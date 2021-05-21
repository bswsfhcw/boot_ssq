package com.winning.batjx.core.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.shael.commons.BeanUtils;
import com.shael.commons.ObjectUtils;
import com.winning.batjx.core.app.mapper.GrstMapper;
import com.winning.batjx.core.app.mapper.JgstMapper;
import com.winning.batjx.core.app.service.JgstService;
import com.winning.batjx.core.common.constant.QyjxConstants;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzXxVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service("jgstService")
public class JgstServiceImpl implements JgstService {

	private static Log logger = LogFactory.getLog(JgstServiceImpl.class);

	@Resource
	private JgstMapper jgstMapper;

	@Resource
    private GrstMapper grstMapper;

    /**
           * 根据方案id、考核区间查询该方案对应的指标信息(机构)
     */
	@Override
	public List<Map<String,Object>> selectKhhzxx(Map<String, Object> map) throws Exception {
		//获取当前登录用户所在的机构(科室)
		String ryid = map.get("ryid") == null?null:(String)map.get("ryid") ;
		String jgbm = map.get("jgbm") == null?null:(String)map.get("jgbm") ;
		List<Map<String, Object>> ksbm = this.grstMapper.selectKsbmByRyid(ryid);
		if(CollectionUtils.isEmpty(ksbm)){
			return null;
		}
        map.put("ksbm",ksbm.get(0).get("jgbm"));
        map.put("jgbm",jgbm);
        
        String nf = (String) map.get("khnf");
        String khpl = (String) map.get("khpl");
        String khqj = (String) map.get("khqj");
        //环比
        Integer hbnf = 0;
        Integer hbqj = 0;

        if("1".equals(khpl)){
            if("1".equals(khqj)){
                //1月份 ，环比 就是上一年度 12 月，
                hbnf = Integer.parseInt(nf)-1;
                hbqj = QyjxConstants.UNKNOWN;
            }else {
                hbnf = Integer.parseInt(nf);
                if(StringUtils.isNotEmpty(khqj)){
                    hbqj = Integer.parseInt(khqj)-1;
                }
            }
        }
        //季度
        if("2".equals(khpl)){
            if("1".equals(khqj)){
                hbnf = Integer.parseInt(nf)-1;
                //此时不存在环比概念
                hbqj = QyjxConstants.UNKNOWN;
            }else {
                hbnf = Integer.parseInt(nf);
                hbqj = Integer.parseInt(khqj)-1;
            }
        }
        //半年
        if("3".equals(khpl)){
            if("1".equals(khqj)){
                hbnf = Integer.parseInt(nf)-1;
                hbqj = QyjxConstants.UNKNOWN;
            }else {
                hbnf = Integer.parseInt(nf);
                hbqj = Integer.parseInt(khqj)-1;
            }
        }
        //年
        if("4".equals(khpl)){
        	hbqj = QyjxConstants.UNKNOWN;
        }
//        map.put("hbnf",hbnf);
        map.put("hbqj",hbqj);
        map.put("pzbz",QyjxConstants.ZDZB_JGST);
		//本期数据
        List<Map<String,Object>> bqDataList = new ArrayList<Map<String,Object>>();
		//环比上期数据
        List<Map<String,Object>> hbDataList = new ArrayList<Map<String,Object>>();
        //所属科室是空
		List<QyjxKhhzXxVo> khhzInfoList = jgstMapper.selectKhhzxxByFaidAndKhqjForIndividualOrJg(map);
		if(CollectionUtils.isEmpty(khhzInfoList)){
			return null;
		}
		
		//优先去type=1的(考核机构与个人，个人在机构中)
		for(QyjxKhhzXxVo vo : khhzInfoList) {
			if(QyjxConstants.JLZT_VALID.equals(vo.getType())){
				bqDataList.add(BeanUtils.toMap(vo));
			}else {
				hbDataList.add(BeanUtils.toMap(vo));
			}
		}
		
		Collections.sort(bqDataList, new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String,Object> o1, Map<String,Object> o2) {
				return ((String)o1.get("khzbbm")).compareTo((String)o2.get("khzbbm"));
			}
		});
        //环比计算法  和上个月数据进行比较，得出增长率
        if(!CollectionUtils.isEmpty(bqDataList)){
            for(Map<String,Object> bqvo : bqDataList){
                String zbbm = (String)bqvo.get("khzbbm");
                BigDecimal bqVal = (BigDecimal)bqvo.get("zbz");
                boolean flag = false;
                //针对是首月、首季度的情况下，给出默认值
                if(CollectionUtils.isEmpty(hbDataList)) {
                	bqvo.put("hbVal","0");
                	bqvo.put("hbzzlVal","--");
                }
                for(Map<String,Object> hbvo : hbDataList){
                    //按照指标编码匹配
                    if(!flag && zbbm.equals((String)hbvo.get("khzbbm"))){
                        flag = true;
                        BigDecimal hbVal = (BigDecimal)hbvo.get("zbz");
                        if(hbVal.compareTo(BigDecimal.ZERO)==0){
                            // 若环比值 为 0 ，为--
                        	bqvo.put("hbVal","0");
                        	bqvo.put("hbzzlVal","--");
                        }else{
                        	bqvo.put("hbVal",""+hbVal);
                            //计算环比值
                            BigDecimal hbZzlVal = ((bqVal.subtract(hbVal)).divide(hbVal,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100")).setScale(2);
                            bqvo.put("hbzzlVal",""+hbZzlVal+"%");
                        }
                    }
                }
            }
        }
		return bqDataList;
	}

	/**
	 * 指标树
	 */
	@Override
	public List<Map<String, Object>> initZbTree(Map<String, Object> map) throws Exception {
//		String khdxbm = String.valueOf(ShiroUtils.getJgbm());
		//TODO
		String ryid = null;//String.valueOf(ShiroUtils.getLoginUser().getId())
		String jgbm = null;//ShiroUtils.getJgbm()
		//获取当前登录用户所在的机构(科室)
		List<Map<String, Object>> ksbm = this.grstMapper.selectKsbmByRyid(ryid);
		if(CollectionUtils.isEmpty(ksbm)){
			return null;
		}
        map.put("ksbm",ksbm.get(0).get("jgbm"));
        map.put("jgbm",jgbm);
        //查询当前考核对象下面对应的指标
		List<Map<String,Object>> result = jgstMapper.selectZb(map);
		if(ObjectUtils.isBlank(map.get("zbmc")) || CollectionUtils.isEmpty(result)){
			return result;
		}
		//
		Set<Map<String,Object>> set = new HashSet<Map<String,Object>>();
		for(Map<String,Object> data : result) {
			if(data.get("yzjd") != null && "1".equals(data.get("yzjd"))) {
				set.add(data);//增加该叶子节点
				handler(result,(String)data.get("pid"),set);
			}
		}
		
		return new ArrayList<Map<String,Object>>(set);
	}
	/**
	 * 处理搜索过后的父级节点(添加父级)
	 * @param allData
	 * @param id
	 * @param set
	 */
	private void handler(List<Map<String,Object>> allData,String id,Set<Map<String,Object>> set) {
		if(StringUtils.isBlank(id)){
			return;
		}
		for(Map<String,Object> data : allData) {
			if(id.equals(data.get("id"))){//存在 加上
				set.add(data);//添加父级节点
				this.handler(allData, (String)data.get("pid"), set);
			}
		}
	}

	/**
	  * 重点指标保存入库
	 */
	@Override
	public void saveZbImport(Map<String, Object> map) throws Exception {
		//TODO ==========================================
		String ryxm = null;//ShiroUtils.getLoginUser().getRyxm()
		//TODO ===========================================

		map.put("czr", ryxm);
		// 重点指标的数据
		String pzzbbmJh = map.get("pzzbbmJh") == null ? "" : (String) map.get("pzzbbmJh");
		if (StringUtils.isNotBlank(pzzbbmJh)) {
			// 先根据方案ID 删除以前配置的 重点指标
			map.put("pzbz", QyjxConstants.ZDZB_JGST);
			grstMapper.delZdzb(map);
			// 分离重点指标
			if (pzzbbmJh.indexOf("_zdzb_") > 0) {
				List<String> queryZbxx = new ArrayList<>();
				String zdzbArr[] = pzzbbmJh.split("_zdzb_");
				for (String zb : zdzbArr) {
					queryZbxx.add(zb);
				}
				if (queryZbxx.size() > 0) {
					map.put("queryZbxx", queryZbxx);
					map.put("pzbz", QyjxConstants.ZDZB_JGST);
					// 将配置的指标信息插入到表中 -- (#{zbid},#{zbmc},#{pzbz},#{faid},#{czr},NOW())
					grstMapper.saveZdzb(map);
				}
			}
		}
	}
	
	/**
	 * 入参：faid 、
	  * 处理考核区间和分类
	 */
	@Override
	public String getKhzqList(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		//根据方案的ID 获取 方案下的分类类型
		List<Map<String, Object>> fllx = this.grstMapper.queryFafllx(map);
		/*
		//查询已经归档(已经经过评价计算)的那个考核方案对应的区间
		List<Map<String, Object>> listKhzq = jgstMapper.getKhzqList(map);
		List<Map<String, Object>> listKhdx = jgstMapper.getKhdxList(map);
		List<Map<String, Object>> listKhhz;
		for (Map<String, Object> mapKhzq : listKhzq) {
			String khzq = mapKhzq.get("khzq").toString();// 1 2 3 4 5 6
			mapKhzq.put("COLUMNID", khzq);
			if ("1".equals(map.get("khpl").toString())) {
				mapKhzq.put("COLUMNNAME", khzq + "月");
			} else if ("2".equals(map.get("khpl").toString())) {
				mapKhzq.put("COLUMNNAME", khzq + "季度");
			} else if ("3".equals(map.get("khpl").toString())) {
				if ("1".equals(khzq)) {
					mapKhzq.put("COLUMNNAME", "上半年");
				} else {
					mapKhzq.put("COLUMNNAME", "下半年");
				}
			} else {
				mapKhzq.put("COLUMNNAME", "全年");
			}
			mapKhzq.put("zbz", "0");
			map.put("khzq", khzq);
			for (Map<String, Object> mapKhdxbm : listKhdx) {
				map.put("khdxbm", mapKhdxbm.get("khdxbm"));
				// 查每个周期 每个对象的数据,理论上一条数据
				listKhhz = jgstMapper.getKhhzList(map);
				mapKhzq.put((String) mapKhdxbm.get("khdxbm"),
						listKhhz == null || listKhhz.size() == 0 ? 0 : listKhhz.get(0).get("zbdf"));
			}
			list.add(mapKhzq);
		}
		String result = JSON.toJSONString(fllx)+"@@@"+JSON.toJSONString(list);
		*/
		String result = JSON.toJSONString(fllx);

		return result;
	}

	/**
	  * 绩效趋势
	 */
	@Override
	public List<Map<String, Object>> jxqsGraph(Map<String, Object> params) throws Exception {
		String ryid = params.get("ryid")==null?null:(String)params.get("ryid");
		String jgbm = params.get("ryid")==null?null:(String)params.get("jgbm");
		//获取当前登录用户所在的机构(科室)
		List<Map<String, Object>> ksbm = this.grstMapper.selectKsbmByRyid(ryid);
		if(CollectionUtils.isEmpty(ksbm)){
			return null;
		}
		params.put("ksbm",ksbm.get(0).get("jgbm"));
		params.put("jgbm",jgbm);
		
        String khpl = params.get("khpl")==null?"":(String)params.get("khpl");
        //采集频率为4 代表按年，直接返回null
        if(QyjxConstants.YEAR.equals(khpl)){
        	return null;
		}
        List<Map<String, Object>> res = jgstMapper.queryJxqsGraphForIndividualOrJg(params);
        if(!CollectionUtils.isEmpty(res)) {
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal avg =  BigDecimal.ZERO;
            for(Map<String, Object> item : res){
                total = total.add((BigDecimal)item.get("qjval"));
            }
            avg = total.divide(new BigDecimal(res.size()),2,BigDecimal.ROUND_HALF_UP).setScale(2);
            for(Map<String, Object> item : res){
                item.put("avg",avg);
            }
        }
		return res;
	}

	/**
	 * 绩效分析 暂时没用
	 */
	@Override
	public List<Map<String, Object>> jxfxGraph(Map<String, Object> map) throws Exception {
		/*
		map.put("jgbm",ShiroUtils.getJgbm());
		String khdxbm = String.valueOf(ShiroUtils.getId());
		khdxbm = "666";
		map.put("khdxbm",khdxbm);
		map.put("jgbm","34010103012");
		*/
        List<Map<String, Object>> res = grstMapper.queryJxfxGraphForIndividualOrJg(map);
        if(CollectionUtils.isEmpty(res)) {
        	res = grstMapper.queryJxfxGraph(map);
        }
        return res;
	}

	/**
	 * 工作量与工作质量top10视图
	 */
	@Override
	public List<Map<String, Object>> gzlOrgzzlTop10Graph(Map<String, Object> map) throws Exception {
		String ryid = map.get("ryid")==null?null:(String)map.get("ryid");
		String jgbm =  map.get("jgbm")==null?null:(String)map.get("jgbm");
		//获取当前登录用户所在的机构(科室)
		List<Map<String, Object>> ksbm = grstMapper.selectKsbmByRyid(ryid);
		if(CollectionUtils.isEmpty(ksbm)){
			return null;
		}
		if(StringUtils.isEmpty((String)map.get("faid"))){
			return null;
		}
		map.put("ksbm",ksbm.get(0).get("jgbm"));
		map.put("jgbm",jgbm);
		List<Map<String, Object>> res = jgstMapper.queryGzlOrGzzltop10ForIndividualOrJg(map);
        return res;
	}

	/**
	 * 考核结果明细分析
	 */
	@Override
	public List<Map<String, Object>> khjgfxGraph(Map<String, Object> map) throws Exception {
		//入参：ryid 、jgbm 、faid 、khpl 、 khqj
		String ryid = map.get("ryid")==null?null:(String)map.get("ryid");//String.valueOf(ShiroUtils.getLoginUser().getId())
		String jgbm = map.get("jgbm")==null?null:(String)map.get("jgbm");//ShiroUtils.getJgbm()
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();

		//获取当前登录用户所在的机构(科室)
		List<Map<String, Object>> ksbm = grstMapper.selectKsbmByRyid(ryid);
		if(CollectionUtils.isEmpty(ksbm)){
			return null;
		}
		String khdxbm = (String)ksbm.get(0).get("jgbm");
		//根据方案、登录机构等查询汇总表
		 //环比
		String khpl = (String)map.get("khpl");
		String khqj = (String)map.get("khqj");

        Integer hbqj = "1".equals(khqj) ? QyjxConstants.UNKNOWN : Integer.parseInt(khqj)-1;
        //年
        if(QyjxConstants.YEAR.equals(khpl)){
        	hbqj = QyjxConstants.UNKNOWN;
        }
		map.put("hbqj",hbqj);
		List<QyjxKhhzVo> currentData = new ArrayList<QyjxKhhzVo>();
		//环比的数据实际上机构视图用不到
		List<QyjxKhhzVo> hbData = new ArrayList<QyjxKhhzVo>();
		//总得分
		BigDecimal totalZbdf = BigDecimal.ZERO;
		//总工作当量
		BigDecimal totalZbhgzl = BigDecimal.ZERO;
		//总绩效
		BigDecimal totalJJ = BigDecimal.ZERO;
		
		List<QyjxKhhzVo> selectAllByJgbm = grstMapper.selectAllByJgbmForIndividualOrJg(map);
		if(CollectionUtils.isEmpty(selectAllByJgbm)){
			return null;
		}
		
		for(QyjxKhhzVo e : selectAllByJgbm) {
			if(QyjxConstants.JLZT_VALID.equals(e.getType())) {
				currentData.add(e);
			}else {
				hbData.add(e);
			}
		}
		if(CollectionUtils.isEmpty(currentData)){
			return null;
		}
		//根据考核对象类型来判断是使用ksbm还是使用jgbm
		List<String> asList = Arrays.asList(new String[] {"0","1","2","3","8"});
		if(asList.contains(currentData.get(0).getKhdxlx())) {
			khdxbm = jgbm;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		for(int i = 0;i<currentData.size();i++) {
			QyjxKhhzVo vo = currentData.get(i);
			if(khdxbm.equals(vo.getKhdxbm())) {
				param.put("khdxmc", vo.getKhdxmc());
				param.put("myDf", vo.getZbdf());
				param.put("myDfRank", handleRank(currentData,khdxbm,"zbdf"));
				param.put("myBhgzl", vo.getZbhgzl());
				param.put("myBhgzlRank", handleRank(currentData,khdxbm,"bhgzl"));
				param.put("myJj", vo.getJj());
				
	            for(QyjxKhhzVo hbvo : hbData){
	                if(khdxbm.equals(hbvo.getKhdxbm())){
	                    param.put("myDfPre",hbvo.getZbdf());
	                    param.put("myBhgzlPre",hbvo.getZbhgzl());
	                    param.put("myJjPre",hbvo.getJj());
	                    param.put("dfHbZzl",handleHb(vo.getZbdf(),hbvo.getZbdf()));
	                    param.put("ghgzlHbZzl",handleHb(vo.getZbdf(),hbvo.getZbdf()));
	                    param.put("jjHbZzl",handleHb(vo.getZbdf(),hbvo.getZbdf()));
	                    break;
	                }
	            }
	            totalJJ = totalJJ.add(vo.getJj());
			}
			totalZbdf = totalZbdf.add(vo.getZbdf());
			totalZbhgzl = totalZbhgzl.add(vo.getZbhgzl());
//			totalJJ = totalJJ.add(vo.getJj());
		}
		
		BigDecimal avgZbdf = totalZbdf.divide(new BigDecimal(currentData.size()),2, RoundingMode.HALF_UP);
		BigDecimal avgZbhgzl = totalZbhgzl.divide(new BigDecimal(currentData.size()),2, RoundingMode.HALF_UP);
		BigDecimal avgJJ = totalJJ.divide(new BigDecimal(currentData.size()),2, RoundingMode.HALF_UP);
		
		param.put("totalJJ", totalJJ);
		param.put("avgZbdf", avgZbdf);
		param.put("avgZbhgzl", avgZbhgzl);
		param.put("avgJJ", avgJJ);
		res.add(param);
		return res;
	}
	
	/**
	 * 计算增长率
	 * @param one
	 * @param two
	 * @return
	 */
	private Object handleHb(BigDecimal one,BigDecimal two) {
		if(two.compareTo(BigDecimal.ZERO) == 0) {
			return "--";
		}
		return one.subtract(two).divide(two,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2) +"%";
	}
	/**
	 * 获取排名
	 * @param list
	 * @param target
	 * @param sequenceName
	 * @return
	 */
	private Integer handleRank(List<QyjxKhhzVo> list,String target,String sequenceName) {
		List<QyjxKhhzVo> destList=new ArrayList<QyjxKhhzVo>(list.size());
		destList.addAll(list);
		Collections.sort(destList, new Comparator<QyjxKhhzVo>() {
			@Override
			public int compare(QyjxKhhzVo o1, QyjxKhhzVo o2) {
				if(sequenceName.equals("zbdf")) {
					return o2.getZbdf().compareTo(o1.getZbdf());
				}else {
					return o2.getZbhgzl().compareTo(o1.getZbhgzl());
				}
			}
		});
		for (int i = 0; i < destList.size(); i++) {
			if(target.equals(destList.get(i).getKhdxbm())) {
				return ++i;
			}
		}
		return null;
	}

	/**
	 * 查询考核频率
	 */
	@Override
	public List<Map<String, Object>> queryJxkhplList(Map<String, Object> map) throws Exception {

		String ryid = map.get("ryid")==null?null:(String)map.get("ryid");
		String jgbm = map.get("jgbm")==null?null:(String)map.get("jgbm");
		//获取当前登录用户所在的机构(科室)
	  List<Map<String, Object>> ksbm = grstMapper.selectKsbmByRyid(ryid);
      if(CollectionUtils.isEmpty(ksbm)){
      	return null;
	  }
	  map.put("ksbm", ksbm.get(0).get("jgbm"));
	  map.put("jgbm", jgbm);
      
      List<Map<String,Object>> kaplList= grstMapper.queryJxkhplList(map);
      for (Map<String,Object> map1:kaplList){
          switch (map1.get("khpl").toString()){
              case "1":  map1.put("khplname","月度"); break;
              case "2":  map1.put("khplname","季度"); break;
              case "3":  map1.put("khplname","半年"); break;
              case "4":  map1.put("khplname","年度"); break;
              default:
          }
      }
      return kaplList;
	}

	/**
	 * 查询考核方案
	 */
	@Override
	public List<Map<String, Object>> queryJxfaList(Map<String, Object> map) throws Exception {
		String ryid = map.get("ryid")==null?null:(String)map.get("ryid");
		String jgbm = map.get("jgbm")==null?null:(String)map.get("jgbm");
		String fzrbz = map.get("fzrbz")==null?null:(String)map.get("fzrbz");
		String ksbm = map.get("ksbm")==null?null:(String)map.get("ksbm");
	  map.put("fzrbz",fzrbz);
	  if("1".equals(fzrbz)){
          if(org.apache.commons.lang.StringUtils.isNotEmpty(ksbm)) {
              List<String> ksbmList= Arrays.asList(ksbm.split(","));
              map.put("ksbm", org.apache.commons.lang.StringUtils.join(ksbmList, "','"));
          }
      }
	  map.put("jgbm",jgbm);
	  map.put("zclsh",ryid);
      return jgstMapper.queryJxfaList(map);
	}

	/**
	 * 查询重点指标
	 */
	@Override
	public List<Map<String, Object>> queryZdzbTable(Map<String, Object> map) throws Exception {
		map.put("pzbz", QyjxConstants.ZDZB_JGST);
		return grstMapper.queryZdzbByParams(map);
	}
}
