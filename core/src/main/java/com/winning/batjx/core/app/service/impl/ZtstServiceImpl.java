package com.winning.batjx.core.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.winning.batjx.core.app.mapper.ZtstMapper;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;
import com.winning.batjx.core.app.service.ZtstService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by thinkpad on 2019/6/6.
 */
@Service("ztstService")
public class ZtstServiceImpl implements ZtstService {

    private static Log log = LogFactory.getLog(ZtstServiceImpl.class);

    @Resource
    private ZtstMapper ztstMapper;


    @Override
    public List<QyjxKhhzVo> queryBizstByNd(Map<String,Object> map) throws Exception {
        return ztstMapper.queryBizstByNd(map);
    }

    @Override
    public List<QyjxKhhzVo> queryBizstYgtFaByNd(Map<String,Object> map) throws Exception {
        return ztstMapper.queryBizstYgtFaByNd(map);
    }

    @Override
    public  HashMap<String, Object> queryCurLoginUserJgdjByRygh(Map<String, Object> map) throws Exception{
        return ztstMapper.queryCurLoginUserJgdjByRygh(map);
    }

    @Override
    public List<HashMap<String, Object>> queryCurLoginUserIsYgtByJgbm(Map<String, Object> map) throws Exception{
        return ztstMapper.queryCurLoginUserIsYgtByJgbm(map);
    }

    @Override
    public  List<HashMap<String, Object>> queryBizstFaqjByFaid(Map<String, Object> map) throws Exception{
        return ztstMapper.queryBizstFaqjByFaid(map);
    }

    @Override
    public List<HashMap<String, Object>> queryCurFafllx(Map<String,Object> map) throws Exception {
        return ztstMapper.queryCurFafllx(map);
    }

    /**
     * 指标列表(初始化)
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<HashMap<String, Object>> zblbTree(Map<String, Object> params) throws Exception{
        try{
            List<HashMap<String, Object>> result = ztstMapper.zblbTree(params);
            //过滤指标，只将方案里配置的指标以及其上面的直属分类获取到
            if(null!=result && result.size()>0){
                //获取所有叶子节点
                HashSet<HashMap<String, Object>> leafFlResult=new HashSet<>();
                for(HashMap<String, Object> leaf: result){
                    if("false".equals(leaf.get("open"))){
                        String pid = leaf.get("pId")==null?"":leaf.get("pId").toString();
                        HashSet<HashMap<String, Object>> items = cycleRoot(result,pid,new HashSet<HashMap<String, Object>>());
                        //将叶子节点的父类节点（指标的分类）的数据插入
                        leafFlResult.addAll(items);
                        //叶子节点上级找不到的情况
                        if(items.size()==0){
                            leaf.put("pId",0);
                        }
                        //叶子节点的数据
                        leafFlResult.add(leaf);
                    }
                }
                result = new ArrayList<>(leafFlResult);
            }

            //指标的查询
            if("SP".equals(params.get("queryArea")) && null !=result && result.size()>0){
                HashSet<HashMap<String, Object>> resultSP=new HashSet<>();
                params.put("queryArea","HEAD");
                //分类的数据
                List<HashMap<String, Object>> headResult = ztstMapper.zblbTree(params);
                //叶子节点找父节点
                for(HashMap<String, Object> item : result){
                    String pid = item.get("pId")==null?"":item.get("pId").toString();
                    HashSet<HashMap<String, Object>> items = cycleRoot(headResult,pid,new HashSet<HashMap<String, Object>>());
                    //将叶子节点的父类节点（指标的分类）的数据插入
                    resultSP.addAll(items);
                    //叶子节点上级找不到的情况
                    if(items.size()==0){
                        item.put("pId",0);
                    }
                    //叶子节点的数据
                    resultSP.add(item);
                }
                return new ArrayList<>(resultSP);
            }
            return  result;
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    /**
     * 向上递归单条算法
     * @param headResult
     * @param id
     * @param out
     * @return
     */
    private HashSet<HashMap<String, Object>> cycleRoot(List<HashMap<String, Object>> headResult,String id,HashSet<HashMap<String, Object>> out){
        for(HashMap<String, Object> item : headResult){
            if("0".equals(id)){
                item.put("open","true");
                out.add(item);
                return out;
            }else{
                if(id.equals(item.get("id").toString())){
                    item.put("open","true");
                    out.add(item);
                    cycleRoot(headResult,item.get("pId").toString(),out);
                }
            }
        }
        return out;
    }

    @Override
    public String saveBizstZbpz(Map<String, Object> map) throws Exception {
        String res = "success";
        try{
            //TOTO
            //map.put("czr",ShiroUtils.getLoginUser().getRyxm());
            //重点指标的数据
            String pzzbbmJh = map.get("pzzbbmJh")==null?"":(String)map.get("pzzbbmJh");
            //top5 指标的数据
            String top5zbJh = map.get("top5zbJh")==null?"":(String)map.get("top5zbJh");
            if(StringUtil.isNotEmpty(pzzbbmJh)){
                map.put("pzbz","ZDZB");
                ztstMapper.delBizstZbpz(map);
                //分离重点指标
                if(pzzbbmJh.indexOf("_zdzb_")>0){
                    pzzbbmJh = pzzbbmJh.substring(0,pzzbbmJh.length()-6);
                    List<String> queryZbxx = new ArrayList<>();
                    String zdzbArr[] = pzzbbmJh.split("_zdzb_");
                    for(String zb : zdzbArr){
                        queryZbxx.add(zb);
                    }
                    if(queryZbxx.size()>0){
                        map.put("queryZbxx",queryZbxx);
                        map.put("pzbz","ZDZB");
                        //将配置的指标信息插入到表中 -- (#{zbid},#{zbmc},#{pzbz},#{faid},#{czr},NOW())
                        ztstMapper.saveBizstZbpz(map);
                    }
                }
            }else{
                map.put("pzbz","ZDZB");
                ztstMapper.delBizstZbpz(map);
            }
            //分离top5指标
            if(StringUtil.isNotEmpty(top5zbJh)){
                map.put("pzbz","TOP5");
                ztstMapper.delBizstZbpz(map);
                //分离top5指标
                if(top5zbJh.indexOf("_top5_")>0){
                    top5zbJh = top5zbJh.substring(0,top5zbJh.length()-6);
                    List<String> queryZbxx = new ArrayList<>();
                    String zdzbArr[] = top5zbJh.split("_top5_");
                    for(String zb : zdzbArr){
                        queryZbxx.add(zb);
                    }
                    if(queryZbxx.size()>0){
                        map.put("queryZbxx",queryZbxx);
                        map.put("pzbz","TOP5");
                        //将配置的指标信息插入到表中 -- (#{zbid},#{zbmc},#{pzbz},#{faid},#{czr},NOW())
                        ztstMapper.saveBizstZbpz(map);
                    }
                }
            }else{
                map.put("pzbz","TOP5");
                ztstMapper.delBizstZbpz(map);
            }
        }catch (Exception e){
            res = "false";
            log.error(e);
        }
        return res;
    }

    @Override
    public List<Map<String,Object>> queryBizstZdzbTable(Map map) throws Exception{
        return ztstMapper.queryBizstZbpzByParams(map);
    }

    @Override
    public List<HashMap<String, Object>> calculBizstZdzbKp(Map<String, Object> params) throws Exception{
        //本期全部数据(包含 上期值，同比值)
        List<HashMap<String, Object>> bqDataList = new ArrayList<>();
        String loginJgbm = params.get("jgbm")==null?null:(String)params.get("jgbm");
        //医共体的上级机构编码-----------------------------------------------------------------
        List<HashMap<String, Object>> ygtList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
        //方案 是谁建立的，这个人所在的机构
        HashMap<String, Object> curJgbm = ztstMapper.queryCurFaxx(params);
        String fajgbm = curJgbm.get("fajgbm")==null?"-1":curJgbm.get("fajgbm").toString();
        if(ygtList !=null && ygtList.size()>0){
            //为医共体 人员登录
            if(loginJgbm.equals(fajgbm)){
                //医共体 看自己建立的方案 ，可以看见自己下面的中心， 浦江二院 自己看见自己
                params.put("jgbm",loginJgbm);
                List<HashMap<String, Object>> ygtSelfList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
                if(null !=ygtSelfList && ygtSelfList.size()>0){
                    if(ygtSelfList.size()==1){
                        //浦江二院的特殊情况 医共体西面没有社区中心的
                        params.put("jgbmYgt_pjey",fajgbm);
                        params.put("jgbm",fajgbm);
                    }else{
                        //医共体：医共体下有社区中心的
                        //当前登录人的机构编码 为医共体的机构编码
                        params.put("jgbmYgt",fajgbm);
                        //取医共体的上级机构 为机构编码
                        params.put("jgbm",fajgbm);
                    }
                }
            }else{
                //医共体 看 区级建立的方案
                params.put("jgbm",loginJgbm);
                List<HashMap<String, Object>> ygtSxjjgList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
                if(null !=ygtSxjjgList && ygtSxjjgList.size()>0){
                    if(ygtSxjjgList.size()==1){
                        //浦江二院的特殊情况 医共体西面没有社区中心的
                        params.put("jgbmYgt_pjey",loginJgbm);
                        //医共体的上级机构
                        params.put("jgbm",ygtList.get(0).get("jgbm"));
                    }else{
                        //医共体：医共体下有社区中心的
                        //当前登录人的机构编码 为医共体的机构编码
                        params.put("jgbmYgt",loginJgbm);
                        //取医共体的上级机构 为机构编码
                        params.put("jgbm",ygtList.get(0).get("jgbm"));
                    }
                }
            }
        }else{
            //为 非医共体 的人 登录 可能是区级
            if(loginJgbm.equals(fajgbm)){
                //1.区级 看自己 建立的方案
                params.put("jgbm",loginJgbm);
            }else{
                //2.区级 看医共体的方案
                //不是医共体人员登录进来，看看这个方案是谁建立的，取这个人创建方案的机构编码
                //机构编码 为 方案的机构编码
                params.put("jgbm",fajgbm);
                List<HashMap<String, Object>> ygtListCheck = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
                if(null !=ygtListCheck && ygtListCheck.size()>0){
                    if(ygtListCheck.size()==1){
                        //浦江二院的特殊情况 医共体西面没有社区中心的
                        params.put("jgbmYgt_pjey",fajgbm);
                        params.put("jgbm",fajgbm);
                    }else{
                        //医共体：医共体下有社区中心的
                        //当前登录人的机构编码 为医共体的机构编码
                        params.put("jgbmYgt",fajgbm);
                        //取医共体的上级机构 为机构编码
                        params.put("jgbm",fajgbm);
                    }
                }
            }
        }
        //--------------------------------------------------------------------------------------

        String nf = (String) params.get("khnf");
        String khpl = (String) params.get("khpl");
        String khqj = (String) params.get("khqj");
        //环比
        Integer hbnf = 0;
        Integer hbqj = 0;

        //同比期间
        Integer tbnf = Integer.parseInt(nf)-1;
        params.put("tbnf",tbnf);
        params.put("tbqj",khqj);

        //月度
        if("1".equals(khpl)){
            if("1".equals(khqj)){
                //1月份 ，环比 就是上一年度 12 月，
                hbnf = Integer.parseInt(nf)-1;
                hbqj = 12;
            }else {
                hbnf = Integer.parseInt(nf);
                hbqj = Integer.parseInt(khqj)-1;
            }
        }
        //季度
        if("2".equals(khpl)){
            if("1".equals(khqj)){
                hbnf = Integer.parseInt(nf)-1;
                hbqj = 4;
            }else {
                hbnf = Integer.parseInt(nf);
                hbqj = Integer.parseInt(khqj)-1;
            }
        }
        //半年
        if("3".equals(khpl)){
            if("1".equals(khqj)){
                hbnf = Integer.parseInt(nf)-1;
                hbqj = 2;
            }else {
                hbnf = Integer.parseInt(nf);
                hbqj = Integer.parseInt(khqj)-1;
            }
        }
        //年
        if("4".equals(khpl)){
            params.put("khqj","1");
        }
        params.put("hbnf",hbnf);
        params.put("hbqj",hbqj);

        //查询本期 、 环比 、 同比 的指标值
        List<HashMap<String, Object>> kpDatas = ztstMapper.queryBizstZdzbKp(params);
        //环比全部数据(包含上期数值)
        List<HashMap> hbDataList = new ArrayList<>();
        //同比全部数据
        List<HashMap> tbDataList = new ArrayList<>();

        if(null != kpDatas && kpDatas.size()>0){
            for(HashMap item : kpDatas){
                if("1".equals(item.get("type"))){
                    bqDataList.add(item);
                }
                if("2".equals(item.get("type"))){
                    hbDataList.add(item);
                }
                if("3".equals(item.get("type"))){
                    tbDataList.add(item);
                }
            }
        }

        //环比计算法  和上个月数据进行比较，得出增长率
        if(bqDataList.size()>0){
            for(HashMap bq : bqDataList){
                String zbdm = bq.get("zbdm")==null?null:(String)bq.get("zbdm");
                BigDecimal bqVal = new BigDecimal(bq.get("zbzz").toString());
                boolean flag = false;
                for(HashMap hb : hbDataList){
                    //按照指标编码匹配
                    if(!flag && null!=zbdm &&zbdm.equals(hb.get("zbdm"))){
                        flag = true;
                        BigDecimal hbVal = new BigDecimal(hb.get("zbzz").toString());
                        if(hbVal.compareTo(BigDecimal.ZERO)==0){
                            // 若环比值 为 0 ，为--
                            bq.put("hbVal","0");
                            bq.put("hbZzlVal","--");
                        }else{
                            bq.put("hbVal",hbVal);
                            //计算环比值
                            BigDecimal hbZzlVal = ((bqVal.subtract(hbVal)).divide(hbVal,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100")).setScale(2);
                            bq.put("hbZzlVal",hbZzlVal+"%");
                        }
                    }
                }
            }
        }

        //同比计算法  和上年数据进行比较，得出增长率
        if(bqDataList.size()>0){
            for(HashMap bq : bqDataList){
                String zbdm = bq.get("zbdm")==null?null:(String)bq.get("zbdm");
                BigDecimal bqVal = new BigDecimal(bq.get("zbzz").toString());
                boolean flag = false;
                for(HashMap tb : tbDataList){
                    //按照指标编码匹配
                    if(!flag && null!= zbdm &&zbdm.equals(tb.get("zbdm"))){
                        flag = true;
                        BigDecimal tbVal = new BigDecimal(tb.get("zbzz").toString());
                        if(tbVal.compareTo(BigDecimal.ZERO)==0){
                            // 若环比值 为 0 ，为--
                            bq.put("tbVal","0");
                            bq.put("tbZzlVal","--");
                        }else{
                            bq.put("tbVal",tbVal);
                            //计算环比值
                            BigDecimal tbZzlVal = ((bqVal.subtract(tbVal)).divide(tbVal,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100")).setScale(2);
                            bq.put("tbZzlVal",tbZzlVal+"%");
                        }
                    }
                }
            }
        }
        return bqDataList;
    }


    /**
     * top 5 柱状图的计算
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public String calculBizstZdzbTop5(Map<String, Object> params) throws Exception{
        List<HashMap<String, Object>> res0 = null;
        List<HashMap<String, Object>> res1 = null;
        List<HashMap<String, Object>> res2 = null;
        List<HashMap<String, Object>> res3 = null;
        List<HashMap<String, Object>> res4 = null;

        //TODO
        String loginJgbm = null;//ShiroUtils.getJgbm()
        params.put("jgbm",loginJgbm);
        //医共体的上级机构编码-----------------------------------------------------------------
        List<HashMap<String, Object>> ygtList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
        //方案 是谁建立的，这个人所在的机构
        HashMap<String, Object> curJgbm = ztstMapper.queryCurFaxx(params);
        String fajgbm = curJgbm.get("fajgbm")==null?"-1":curJgbm.get("fajgbm").toString();
        if(ygtList !=null && ygtList.size()>0){
            //为医共体 人员登录
            if(loginJgbm.equals(fajgbm)){
                //医共体 看自己建立的方案 ，可以看见自己下面的中心， 浦江二院 自己看见自己
                params.put("jgbm",loginJgbm);
                List<HashMap<String, Object>> ygtSelfList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
                if(null !=ygtSelfList && ygtSelfList.size()>0){
                    if(ygtSelfList.size()==1){
                        //浦江二院的特殊情况 医共体西面没有社区中心的
                        params.put("jgbmYgt_pjey",fajgbm);
                        params.put("jgbm",fajgbm);
                    }else{
                        //医共体：医共体下有社区中心的
                        //当前登录人的机构编码 为医共体的机构编码
                        params.put("jgbmYgt",fajgbm);
                        //取医共体的上级机构 为机构编码
                        params.put("jgbm",fajgbm);
                    }
                }
            }else{
                //医共体 看 区级建立的方案
                params.put("jgbm",loginJgbm);
                List<HashMap<String, Object>> ygtSxjjgList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
                if(null !=ygtSxjjgList && ygtSxjjgList.size()>0){
                    if(ygtSxjjgList.size()==1){
                        //浦江二院的特殊情况 医共体西面没有社区中心的
                        params.put("jgbmYgt_pjey",loginJgbm);
                        //医共体的上级机构
                        params.put("jgbm",ygtList.get(0).get("jgbm"));
                    }else{
                        //医共体：医共体下有社区中心的
                        //当前登录人的机构编码 为医共体的机构编码
                        params.put("jgbmYgt",loginJgbm);
                        //取医共体的上级机构 为机构编码
                        params.put("jgbm",ygtList.get(0).get("jgbm"));
                    }
                }
            }
        }else{
            //为 非医共体 的人 登录 可能是区级
            if(loginJgbm.equals(fajgbm)){
                //1.区级 看自己 建立的方案
                params.put("jgbm",loginJgbm);
            }else{
                //2.区级 看医共体的方案
                //不是医共体人员登录进来，看看这个方案是谁建立的，取这个人创建方案的机构编码
                //机构编码 为 方案的机构编码
                params.put("jgbm",fajgbm);
                List<HashMap<String, Object>> ygtListCheck = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
                if(null !=ygtListCheck && ygtListCheck.size()>0){
                    if(ygtListCheck.size()==1){
                        //浦江二院的特殊情况 医共体西面没有社区中心的
                        params.put("jgbmYgt_pjey",fajgbm);
                        params.put("jgbm",fajgbm);
                    }else{
                        //医共体：医共体下有社区中心的
                        //当前登录人的机构编码 为医共体的机构编码
                        params.put("jgbmYgt",fajgbm);
                        //取医共体的上级机构 为机构编码
                        params.put("jgbm",fajgbm);
                    }
                }
            }
        }
        //--------------------------------------------------------------------------------------
        //获取TOP5 配置的指标信息
        List<HashMap<String, Object>> bgsjList = ztstMapper.queryBizstZdzbTop5Nr(params);
        if(null != bgsjList && bgsjList.size()>0){
            int i=0;
            for(HashMap<String, Object> item : bgsjList){
                String zbsxLb = item.get("zbsxmc")==null?"":(String)item.get("zbsxmc");
                if("数量".equals(zbsxLb.trim())){
                    params.put("zbsxLb","SL");
                    params.put("zbbm",item.get("zbid"));
                }
                if("质量".equals(zbsxLb.trim())){
                    params.put("zbsxLb","ZL");
                    params.put("zbbm",item.get("zbid"));
                }
                if(i==0){
                    res0 = ztstMapper.queryBizstZdzbTop5(params);
                }
                if(i==1){
                    res1 = ztstMapper.queryBizstZdzbTop5(params);
                }
                if(i==2){
                    res2 = ztstMapper.queryBizstZdzbTop5(params);
                }
                if(i==3){
                    res3 = ztstMapper.queryBizstZdzbTop5(params);
                }
                if(i==4){
                    res4 = ztstMapper.queryBizstZdzbTop5(params);
                }
                i++;
            }
        }

        String listRes = null;
        if(null != res0){
            listRes = JSON.toJSONString(res0);
        }else {
            listRes = JSON.toJSONString(new ArrayList<>());
        }
        if(null != res1){
            listRes = listRes+"@@@"+JSON.toJSONString(res1);
        }else {
            listRes = listRes+"@@@"+JSON.toJSONString(new ArrayList<>());
        }
        if(null != res2){
            listRes = listRes+"@@@"+JSON.toJSONString(res2);
        }else {
            listRes = listRes+"@@@"+JSON.toJSONString(new ArrayList<>());
        }
        if(null != res3){
            listRes = listRes+"@@@"+JSON.toJSONString(res3);
        }else {
            listRes = listRes+"@@@"+JSON.toJSONString(new ArrayList<>());
        }
        if(null != res4){
            listRes = listRes+"@@@"+JSON.toJSONString(res4);
        }else {
            listRes = listRes+"@@@"+JSON.toJSONString(new ArrayList<>());
        }

        listRes = listRes+"@@@"+JSON.toJSONString(bgsjList);

        return listRes;
    }


    /**
     * 考核结果分析视图
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public String bizstKhjgfxGraph(Map<String, Object> params) throws Exception{
        try{
            String loginJgbm = params.get("jgbm")==null?null:(String)params.get("jgbm");
            //医共体的上级机构编码-----------------------------------------------------------------
            List<HashMap<String, Object>> ygtList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
            if(null !=ygtList && ygtList.size()>0){
                if(ygtList.size()==1){
                    //浦江二院的特殊情况 医共体西面没有社区中心的
                    params.put("jgbmYgt_pjey",loginJgbm);
                    params.put("jgbm",ygtList.get(0).get("jgbm"));
                }else{
                    //医共体：医共体下有社区中心的
                    //当前登录人的机构编码 为医共体的机构编码
                    params.put("jgbmYgt",loginJgbm);
                    //取医共体的上级机构 为机构编码
                    params.put("jgbm",ygtList.get(0).get("jgbm"));
                }
            }
            //-----------------------------------------------------------------
            if("4".equals(params.get("khpl"))){
                params.put("khqj","1");
            }
            String listRes=null;
            List<HashMap<String, Object>> outPut = new ArrayList<>();
            //依据方案ID来寻找
            List<HashMap<String, Object>> res = ztstMapper.queryBizstKhjgfxGraph(params);
            if(null != res){
                //平均分占比计算
                if("1".equals(params.get("jgfxSel"))){
                    List<HashMap<String, Object>> jg = new ArrayList<>();
                    List<HashMap<String, Object>> zdf = new ArrayList<>();
                    for(HashMap<String, Object> item : res){
                        if("jgs".equals(item.get("type"))){
                            jg.add(item);
                        }else{
                            zdf.add(item);
                        }
                    }

                    if(jg.size()!=0 && zdf.size()!=0){
                        BigDecimal jgs = jg.get(0).get("jgs")==null?BigDecimal.ZERO:new BigDecimal(jg.get(0).get("jgs").toString());
                        if(jgs.compareTo(BigDecimal.ZERO)!=0){
                            for(HashMap<String, Object> one : zdf){
                                // 平均分占是所有机构总分除以机构数
                                BigDecimal yjfldfAvg = (new BigDecimal(one.get("yjzbdf").toString())).divide(jgs,2,BigDecimal.ROUND_HALF_UP).setScale(2);
                                one.put("yjfldfAvg",yjfldfAvg);
                            }
                        }
                    }
                    if(zdf.size()>0){
                        listRes = JSON.toJSONString(zdf);
                    }
                }
                //总当量占比
                if("2".equals(params.get("jgfxSel"))){
                    if(res.size()>0){
                        listRes = JSON.toJSONString(res);
                    }
                }
            }
            return listRes;
        }catch (Exception e){
            log.error(e);
        }
       return null;
    }

    /**
     * TOP10
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public String bizstPmtop10(Map<String, Object> params) throws Exception{
        String loginJgbm = params.get("jgbm")==null?null:(String)params.get("jgbm");
        //医共体的上级机构编码-----------------------------------------------------------------
        List<HashMap<String, Object>> ygtList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
        if(null !=ygtList && ygtList.size()>0){
            if(ygtList.size()==1){
                //浦江二院的特殊情况 医共体西面没有社区中心的
                params.put("jgbmYgt_pjey",loginJgbm);
                params.put("jgbm",ygtList.get(0).get("jgbm"));
            }else{
                //医共体：医共体下有社区中心的
                //当前登录人的机构编码 为医共体的机构编码
                params.put("jgbmYgt",loginJgbm);
                //取医共体的上级机构 为机构编码
                params.put("jgbm",ygtList.get(0).get("jgbm"));
            }
        }
        //-----------------------------------------------------------------
        if("4".equals(params.get("khpl"))){
            params.put("khqj","1");
        }
        String listRes=null;
        List<HashMap<String, Object>> res = ztstMapper.queryBizstPmtop10(params);
        if(null != res){
            if(res.size()>0){
                listRes = JSON.toJSONString(res);
            }
        }
        return listRes;
    }

    /**
     * 绩效分析视图
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public String bizstJxfxGraph(Map<String, Object> params) throws Exception{
        String loginJgbm = params.get("jgbm")==null?null:(String)params.get("jgbm");
        //医共体的上级机构编码-----------------------------------------------------------------
        List<HashMap<String, Object>> ygtList = ztstMapper.queryCurLoginUserIsYgtByJgbm(params);
        if(null !=ygtList && ygtList.size()>0){
            if(ygtList.size()==1){
                //浦江二院的特殊情况 医共体西面没有社区中心的
                params.put("jgbmYgt_pjey",loginJgbm);
                params.put("jgbm",ygtList.get(0).get("jgbm"));
            }else{
                //医共体：医共体下有社区中心的
                //当前登录人的机构编码 为医共体的机构编码
                params.put("jgbmYgt",loginJgbm);
                //取医共体的上级机构 为机构编码
                params.put("jgbm",ygtList.get(0).get("jgbm"));
            }
        }
        //-----------------------------------------------------------------
        if("4".equals(params.get("khpl"))){
            params.put("khqj","1");
        }
        String listRes=null;
        List<HashMap<String, Object>> res = ztstMapper.queryBizstJxfxGraph(params);
        if(null != res){
            if(res.size()>0){
                listRes = JSON.toJSONString(res);
            }
        }
        return listRes;
    }

    /**
     * 绩效趋势视图
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public String bizstJxqsGraph(Map<String, Object> params) throws Exception{
        String listRes=null;
        String khpl = params.get("khpl")==null?"":(String)params.get("khpl");
        //采集频率为4 代表按年 ，取近三年数据 ，不跟方案走
        if("4".equals(khpl)){
            Integer curYear = Integer.parseInt(params.get("khnf")==null?"0":(String)params.get("khnf"));
            params.put("fyear",curYear-2);
            params.put("syear",curYear-1);
            params.put("khqj","1");
        }
        List<HashMap<String, Object>> res = ztstMapper.queryBizstJxqsGraph(params);
        if(null != res){
            if(res.size()>0){
                BigDecimal total = BigDecimal.ZERO;
                BigDecimal avg =  BigDecimal.ZERO;
                //月份数值不为0 的记录
                int recordNotZero = 0;
                for(HashMap<String, Object> item : res){
                    if((new BigDecimal(item.get("qjval").toString())).compareTo(BigDecimal.ZERO)>0){
                        recordNotZero++;
                    }
                    total=total.add(new BigDecimal(item.get("qjval").toString()));
                }
                avg = total.divide(new BigDecimal(recordNotZero),2,BigDecimal.ROUND_HALF_UP).setScale(2);
                for(HashMap<String, Object> item : res){
                    item.put("avg",avg);
                }
                listRes = JSON.toJSONString(res);
            }
        }
        return listRes;
    }

}
