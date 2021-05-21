package com.winning.batjx.core.app.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shael.commons.ObjectUtils;
import com.winning.batjx.core.app.mapper.GrstMapper;
import com.winning.batjx.core.app.mapper.JgstMapper;
import com.winning.batjx.core.common.constant.QyjxConstants;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;
import com.winning.batjx.core.app.service.GrstService;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzXxVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.shael.commons.BeanUtils;

import javax.annotation.Resource;


@Service("grstService")
public class GrstServiceImpl implements GrstService {


	private static Log logger = LogFactory.getLog(GrstServiceImpl.class);

	@Resource
	private JgstMapper jgstMapper;

	@Resource
	private GrstMapper grstMapper;

    /**
           * 根据方案id、考核区间查询该方案对应的指标信息(个人)
     */
	@Override
	public List<Map<String,Object>> selectKhhzxx(Map<String, Object> map) throws Exception {
		// String.valueOf(ShiroUtils.getId());
		String khdxbm = map.get("ryid")==null?null:(String)map.get("ryid");
        map.put("khdxbm",khdxbm);
        //map.put("jgbm",ShiroUtils.getJgbm());
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
                hbqj = Integer.parseInt(khqj)-1;
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
        map.put("pzbz",QyjxConstants.ZDZB_GRST);
        List<Map<String,Object>> bqDataList = new ArrayList<Map<String,Object>>();//本期数据
        List<Map<String,Object>> hbDataList = new ArrayList<Map<String,Object>>();//环比上期数据
        //所属科室是空
		List<QyjxKhhzXxVo> khhzInfoList = grstMapper.selectKhhzxxByFaidAndKhqjForIndividualOrJg(map);
		if(CollectionUtils.isEmpty(khhzInfoList)) {
			//所属科室非空
			khhzInfoList = grstMapper.selectKhhzxxByFaidAndKhqj(map);
		}
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
                            BigDecimal hbZzlVal = (
                            		(bqVal.subtract(hbVal)).divide(hbVal,4,BigDecimal.ROUND_HALF_UP)
							).multiply(new BigDecimal("100")).setScale(2);
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
		//TODO
		String khdxbm = null;//String.valueOf(ShiroUtils.getId())
		//String khdxbm = String.valueOf(ShiroUtils.getId());
        map.put("khdxbm",khdxbm);
        //查询当前考核对象下面对应的指标
		List<Map<String,Object>> result = grstMapper.selectZb(map);
		if(ObjectUtils.isBlank(map.get("zbmc")) || CollectionUtils.isEmpty(result)){
			return result;
		}
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
		//TODO ==================================
		String ryxm = null;
		//TODO ==================================
		map.put("czr", ryxm);
		// 重点指标的数据
		String pzzbbmJh = map.get("pzzbbmJh") == null ? "" : (String) map.get("pzzbbmJh");
		if (StringUtils.isNotBlank(pzzbbmJh)) {
			// 先根据方案ID 删除以前配置的 重点指标
			map.put("pzbz", QyjxConstants.ZDZB_GRST);
			this.grstMapper.delZdzb(map);
			// 分离重点指标
			if (pzzbbmJh.indexOf("_zdzb_") > 0) {
//				pzzbbmJh = pzzbbmJh.substring(0, pzzbbmJh.length() - 6);
				List<String> queryZbxx = new ArrayList<>();
				String zdzbArr[] = pzzbbmJh.split("_zdzb_");
				for (String zb : zdzbArr) {
					queryZbxx.add(zb);
				}
				if (queryZbxx.size() > 0) {
					map.put("queryZbxx", queryZbxx);
					map.put("pzbz", QyjxConstants.ZDZB_GRST);
					// 将配置的指标信息插入到表中 -- (#{zbid},#{zbmc},#{pzbz},#{faid},#{czr},NOW())
					grstMapper.saveZdzb(map);
				}
			}
		}
	}
	
	/**
	  * 处理考核区间和分类
	 */
	@Override
	public String getKhzqList(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> fllx = this.grstMapper.queryFafllx(map);
		String result = JSON.toJSONString(fllx);
		return result;
	}

	/**
	  * 绩效趋势
	 */
	@Override
	public List<Map<String, Object>> jxqsGraph(Map<String, Object> params) throws Exception {
		String khdxbm = params.get("ryid")==null?null:(String)params.get("ryid");//String.valueOf(ShiroUtils.getId());
		params.put("khdxbm",khdxbm);
        String listRes=null;
        String khpl = params.get("khpl")==null?"":(String)params.get("khpl");
        //采集频率为4 代表按年，直接返回null
        if(QyjxConstants.YEAR.equals(khpl)){
            return null;
        }
        logger.info(String.format("根据方案：%s、考核频率：%s、考核对象编码：%s、所属机构：%s查询", params.get("faid"),params.get("khpl"),params.get("khdxbm"),params.get("jgbm")));
        List<Map<String, Object>> res = this.grstMapper.queryJxqsGraphForIndividualOrJg(params);
        if(CollectionUtils.isEmpty(res)) {
        	res = this.grstMapper.queryJxqsGraph(params);
        }
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
	 * 绩效分析
	 */
	@Override
	public List<Map<String, Object>> jxfxGraph(Map<String, Object> map) throws Exception {
		//TODO
		String khdxbm = null;//String.valueOf(ShiroUtils.getId());
		map.put("khdxbm",khdxbm);
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
		String khdxbm = map.get("ryid")==null?null:(String)map.get("ryid");//String.valueOf(ShiroUtils.getId());
		map.put("khdxbm",khdxbm);
		List<Map<String, Object>> res = grstMapper.queryGzlOrGzzltop10ForIndividualOrJg(map);
        if(CollectionUtils.isEmpty(res)) {
        	res = grstMapper.queryGzlOrGzzltop10(map);
        }
        return res;
	}

	/**
	 * 考核结果明细分析
	 */
	@Override
	public List<Map<String, Object>> khjgfxGraph(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		//String.valueOf(ShiroUtils.getLoginUser().getId())
		String ryxm = map.get("ryid")==null?null:(String)map.get("ryid");
		//ShiroUtils.getId()
		String id = map.get("ryid")==null?null:(String)map.get("ryid");
		//获取当前登录用户所在的机构(科室)
		List<Map<String, Object>> ksbm = this.grstMapper.selectKsbmByRyid(ryxm);
		if(CollectionUtils.isEmpty(ksbm)){
			return null;
		}
		map.put("jgbm",ksbm.get(0).get("jgbm"));
		String khdxbm = String.valueOf(id);
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
		List<QyjxKhhzVo> hbData = new ArrayList<QyjxKhhzVo>();
		BigDecimal totalZbdf = BigDecimal.ZERO;//总得分
		BigDecimal totalZbhgzl = BigDecimal.ZERO;//总工作当量
		BigDecimal totalJJ = BigDecimal.ZERO;//总绩效
		
		List<QyjxKhhzVo> selectAllByJgbm = this.grstMapper.selectAllByJgbmForIndividual(map);
		if(CollectionUtils.isEmpty(selectAllByJgbm)) {
			selectAllByJgbm = this.grstMapper.selectAllByJgbm(map);
		}
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		for(int i = 0;i<currentData.size();i++) {
			QyjxKhhzVo vo = currentData.get(i);
			if(khdxbm.equals(vo.getKhdxbm())) {
				param.put("myDf", vo.getZbdf()==null?BigDecimal.ZERO:vo.getZbdf());
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
		
		param.put("totalJJ", totalJJ.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:totalJJ);
		param.put("avgZbdf", avgZbdf.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:avgZbdf);
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
		return one.subtract(two).divide(two,4,BigDecimal.ROUND_HALF_UP).multiply(
				new BigDecimal("100")).setScale(2) +"%";
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
        map.put("khdxbm", String.valueOf(ryid));
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
		//当前登录人的id
        map.put("khdxbm", String.valueOf(ryid));
        //修改：区级对中心进行考核，即使填报了个人的数据，也不让他看见该方案
		List<Map<String, Object>> res= grstMapper.queryJxfaList(map);
        return res;
	}

	/**
	 * 查询重点指标
	 */
	@Override
	public List<Map<String, Object>> queryZdzbTable(Map<String, Object> map) throws Exception {
		map.put("pzbz", QyjxConstants.ZDZB_GRST);
		return grstMapper.queryZdzbByParams(map);
	}
}
