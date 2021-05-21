package com.winning.batjx.core.khgl.khwj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winning.batjx.core.khgl.khwj.domain.TmDO;
import com.winning.batjx.core.khgl.khwj.domain.TmxxDO;
import com.winning.batjx.core.khgl.khwj.mapper.TkglMapper;
import com.winning.batjx.core.khgl.khwj.service.TkglService;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.batjx.core.common.util.PageUtils;
import com.winning.jbase.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 题库管理
 * Created by thinkpad on 2021/3/3.
 */
@Service("tkglService")
public class TkglServiceImpl implements TkglService {

    private static Log log = LogFactory.getLog(TkglServiceImpl.class);

    @Resource
    private TkglMapper tkglMapper;

    @Override
    public PageResult getTmDOList(Map<String,Object> params ) {
        int pageNum = (Integer) params.get("pageNum");
        int pageSize = (Integer) params.get("pageSize");
        Map map=new HashMap();
        //题目名称
        map.put("tmmc",StringUtils.isEmpty((String)params.get("tmmc"))?null:(String)params.get("tmmc"));
        //题目类型
        map.put("tmlx",StringUtils.isEmpty((String)params.get("tmlx"))?"9":(String)params.get("tmlx"));
        //TODO 登录信息放入sql查询
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        PageHelper.startPage(pageNum, pageSize);
        //注意分页工具只对PageHelper下的第一个select语句生效
        List<TmDO> list = tkglMapper.queryPageListTmDO(map);
        return PageUtils.getPageResult(new PageInfo<>(list));
    }


    /**
     * 点击新增产生题目编码
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> createCurTmbh(Map<String,Object> params){
        Map<String,Object> tmbhMap = new HashMap<>();
        String tmbh = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String date = df.format(System.currentTimeMillis());
        tmbh = date.substring(0,8);
        String rq = tmbh.substring(0,4)+"-"+tmbh.substring(4,6)+"-"+tmbh.substring(6,tmbh.length());
        //params.put("curDate",tmbh.substring(0,4)+"-"+tmbh.substring(4,6)+"-"+tmbh.substring(6,tmbh.length()));
        params.put("kssj",rq+" 00:00:00");
        params.put("jssj",rq+" 23:59:59");

        tmbhMap =  tkglMapper.queryTodayTmsl(params);
        //查询数据库中，今天的题目数量，在此基础上加1
        if(null!=tmbhMap){
            //获取数据库里最新的一笔题目编码
            String curTmbm = tmbhMap.get("sl").toString();
            if(null!= curTmbm){
                //2021  的 202
                String head_nf = curTmbm.substring(0,3);
                //2021 中的1 和 00X
                String rqbm = curTmbm.substring(3);
                Integer sl = Integer.parseInt(rqbm)+1;
                tmbhMap.put("tmbm",head_nf+String.valueOf(sl));
            }
        }else{
            //如果今天没有新增题目，则自动创建题目编码
            tmbhMap = new HashMap<>();
            tmbhMap.put("tmbm",tmbh+"001");
        }
        return tmbhMap;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTmDO(TmDO tmDO,List<Map<String,Object>> tmxx){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tmbm",tmDO.getTmbm());
        //获取老对象
        TmDO obj=tkglMapper.getTmDOByKey(params);
        if (obj==null){
            //新增主表信息，得到主表的ID
            tkglMapper.insertTmDO(tmDO);
            TmxxDO tmxxDO = null;
            if(null!= tmxx && tmxx.size()>0){
                Integer index = 1;
                for(Map<String,Object> xxnr : tmxx){
                    tmxxDO = new TmxxDO();
                    tmxxDO.setTmid(tmDO.getId());
                    tmxxDO.setXxnr(xxnr.get("xxnr").toString());
                    tmxxDO.setXxsx(index);
                    index++;
                    tkglMapper.insertTmxxDO(tmxxDO);
                }
            }
        }else{
            //修改主表信息
            tkglMapper.updateTmDO(tmDO);
            //删除从表信息
            tkglMapper.delTmxxDO(params);
            //将新的选项写入到子表中
            TmxxDO tmxxDO = null;
            if(null!= tmxx && tmxx.size()>0){
                Integer index = 1;
                for(Map<String,Object> xxnr : tmxx){
                    tmxxDO = new TmxxDO();
                    tmxxDO.setTmid(obj.getId());
                    tmxxDO.setXxnr(xxnr.get("xxnr").toString());
                    tmxxDO.setXxsx(index);
                    index++;
                    tkglMapper.insertTmxxDO(tmxxDO);
                }
            }
        }
    }


    @Override
    public TmDO getTmDetail(Map<String,Object> params ) throws Exception{
        TmDO tmDO = new TmDO();
        try{
            tmDO.setTmbm((String) params.get("tmbm"));
            tmDO = tkglMapper.getTmDOByKey(params);
            if(null!=tkglMapper.getTmxxDOByKey(params)){
                tmDO.setTmxxDOList((List<TmxxDO>)tkglMapper.getTmxxDOByKey(params));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return tmDO;
    }


    /**
     * 题库管理主从表的删除
     * @param params：tmbm : 题目编码
     * @throws Exception
     */
    @Override
    public void delTmByParams(Map<String,Object> params ) throws Exception{
        try{
            tkglMapper.delTmDO(params);
            tkglMapper.delTmxxDO(params);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUploadExclTmDO(Collection<Map> importExcel) throws Exception {
        try{
            String tmbmExcel,tmmcExcel,tmlxmcExcel,tmfbtExcel,
                    xxnrExcel_1Excel,xxnrExcel_2Excel,xxnrExcel_3Excel,xxnrExcel_4Excel,xxnrExcel_5Excel,
                    xxnrExcel_6Excel,xxnrExcel_7Excel,xxnrExcel_8Excel,xxnrExcel_9Excel,xxnrExcel_10Excel;
            Iterator<Map> it = importExcel.iterator() ;
            TmDO tmDO = null;
            Map<String,Object> curTmbhMap = createCurTmbh(new HashMap<>());
            if(null!=curTmbhMap.get("tmbm")){
                String ori_tmbm = String.valueOf(curTmbhMap.get("tmbm"));
                //当前年份
                String ori_tmbm_nf = ori_tmbm.substring(0,3);
                //日期+索引 = 10329001
                String ori_tmbm_head = ori_tmbm.substring(3);
                Integer tmbm  = Integer.parseInt(ori_tmbm_head);
                while (it.hasNext()){
                    tmDO = new TmDO();
                    Map<String,String> mapExcel=it.next();
                    // 题目编码 自动生成:根据今天的日期进行查询，获取递增索引
                    tmDO.setTmbm(ori_tmbm_nf+tmbm.toString());
                    tmbm++;
                    tmmcExcel=mapExcel.get("题目名称");
                    /*
                    if(StringUtil.isEmpty(tmmcExcel)){
                        tmmcExcel="-";
                    }*/
                    tmDO.setTmmc(tmmcExcel);
                    tmlxmcExcel=mapExcel.get("题目类型");
                    if(StringUtil.isEmpty(tmlxmcExcel)){
                        tmlxmcExcel="1";
                    }else{
                        if(tmlxmcExcel.indexOf("单选")!=-1){
                            tmDO.setTmlx("1");
                        }else{
                            if(tmlxmcExcel.indexOf("多选")!=-1){
                                tmDO.setTmlx("2");
                            }else{
                                if(tmlxmcExcel.indexOf("填空")!=-1){
                                    tmDO.setTmlx("3");
                                }else{
                                    //默认为 【单选】
                                    tmDO.setTmlx("1");
                                }
                            }
                        }
                    }

                    tmfbtExcel=mapExcel.get("副标题");
                    /*
                    if(StringUtil.isEmpty(tmfbtExcel)){
                        tmfbtExcel="-";
                    }*/
                    tmDO.setTmfbt(tmfbtExcel);
                    //上传题目的“答题备注”默认为“无”
                    tmDO.setSfdtbz("0");
                    //默认状态为1
                    tmDO.setZt("1");

                    //主表的保存
                    //新增主表信息，得到主表的ID
                    tkglMapper.insertTmDO(tmDO);

                    //从表选项的保存(只保存选项类型为【单选和多选的题目选项，对于填空题类型，选项不做保存操作】)-------------------------------------------------------
                    if(null!=tmDO && (!"3".equals(tmDO.getTmlx()))){
                        TmxxDO tmxxDO = null;
                        List<String> xxList =  new ArrayList<String>();
                        xxnrExcel_1Excel=mapExcel.get("选项1");
                        xxnrExcel_2Excel=mapExcel.get("选项2");
                        xxnrExcel_3Excel=mapExcel.get("选项3");
                        xxnrExcel_4Excel=mapExcel.get("选项4");
                        xxnrExcel_5Excel=mapExcel.get("选项5");
                        xxnrExcel_6Excel=mapExcel.get("选项6");
                        xxnrExcel_7Excel=mapExcel.get("选项7");
                        xxnrExcel_8Excel=mapExcel.get("选项8");
                        xxnrExcel_9Excel=mapExcel.get("选项9");
                        xxnrExcel_10Excel=mapExcel.get("选项10");

                        if(!StringUtil.isEmpty(xxnrExcel_1Excel)){
                            xxList.add(xxnrExcel_1Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_2Excel)){
                            xxList.add(xxnrExcel_2Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_3Excel)){
                            xxList.add(xxnrExcel_3Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_4Excel)){
                            xxList.add(xxnrExcel_4Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_5Excel)){
                            xxList.add(xxnrExcel_5Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_6Excel)){
                            xxList.add(xxnrExcel_6Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_7Excel)){
                            xxList.add(xxnrExcel_7Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_8Excel)){
                            xxList.add(xxnrExcel_8Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_9Excel)){
                            xxList.add(xxnrExcel_9Excel);
                        }
                        if(!StringUtil.isEmpty(xxnrExcel_10Excel)){
                            xxList.add(xxnrExcel_10Excel);
                        }

                        if(null!= xxList && xxList.size()>0){
                            Integer index = 1;
                            for(String xxnr : xxList){
                                tmxxDO = new TmxxDO();
                                tmxxDO.setTmid(tmDO.getId());
                                tmxxDO.setXxnr(xxnr);
                                tmxxDO.setXxsx(index);
                                index++;
                                tkglMapper.insertTmxxDO(tmxxDO);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
























    /**
     * 保存上传的文件
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> saveTmExcel(Map<String, Object> map) throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        //int czr = ShiroUtils.getLoginUser().getId();

        //前台进行拼接，然后写入
        //题目编码
        String tmbmPara = (String) map.get("tmbm");
        //题目名称
        String tmmcPara = (String) map.get("tmmc");
        //题目类型
        String tmlxmcPara = (String) map.get("tmlxmc");
        //副标题
        String tmfbtPara = (String) map.get("tmfbt");
        String xxnrExcel_1Para = (String) map.get("xxnrExcel_1");
        String xxnrExcel_2Para = (String) map.get("xxnrExcel_2");
        String xxnrExcel_3Para = (String) map.get("xxnrExcel_3");
        String xxnrExcel_4Para = (String) map.get("xxnrExcel_4");
        String xxnrExcel_5Para = (String) map.get("xxnrExcel_5");
        String xxnrExcel_6Para = (String) map.get("xxnrExcel_6");
        String xxnrExcel_7Para = (String) map.get("xxnrExcel_7");
        String xxnrExcel_8Para = (String) map.get("xxnrExcel_8");
        String xxnrExcel_9Para = (String) map.get("xxnrExcel_9");
        String xxnrExcel_10Para = (String) map.get("xxnrExcel_10");

        String[] tmbms = tmbmPara.split("##");
        //题目名称
        String[] tmmcs = tmmcPara.split("##");
        String[] tmlxmcs = tmlxmcPara.split("##");
        String[] tmfbts = tmfbtPara.split("##");

        //选项1-10
        String[] xxnrExcel_1s = xxnrExcel_1Para.split("##");
        String[] xxnrExcel_2s = xxnrExcel_2Para.split("##");
        String[] xxnrExcel_3s = xxnrExcel_3Para.split("##");
        String[] xxnrExcel_4s = xxnrExcel_4Para.split("##");
        String[] xxnrExcel_5s = xxnrExcel_5Para.split("##");
        String[] xxnrExcel_6s = xxnrExcel_6Para.split("##");
        String[] xxnrExcel_7s = xxnrExcel_7Para.split("##");
        String[] xxnrExcel_8s = xxnrExcel_8Para.split("##");
        String[] xxnrExcel_9s = xxnrExcel_9Para.split("##");
        String[] xxnrExcel_10s = xxnrExcel_10Para.split("##");

        Date dt=new Date();
        //"yyyyMMdd"这个格式必须区分大小写
        SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
        String date =  matter1.format(dt);
        int count = 0;
        TmDO tmDO = null;
        TmxxDO tmxxDO = null;

        //根据题目进行遍历
        for(int i = 0 ; i < tmmcs.length ; i++) {
            tmDO = new TmDO();
            //如果试题编码不存在做新增处理
            if ("000".equals(tmbms)) {
                count = count + 1;
                //题目编码=================================
                if (count < 10) {
                    tmDO.setTmbm(date + "000" + count);
                }
                if (count < 100 && count > 9) {
                    tmDO.setTmbm(date + "00" + count);
                }
                if (count < 1000 && count > 99) {
                    tmDO.setTmbm(date + "0" + count);
                }
                tmDO.setTmmc(tmmcs[i]);

                //题目类型=================================
                if ("单选".equals(tmlxmcs[i])) {
                    tmDO.setTmlx("1");
                }
                if ("多选".equals(tmlxmcs[i])) {
                    tmDO.setTmlx("2");
                }
                if ("填空".equals(tmlxmcs[i])) {
                    tmDO.setTmlx("3");
                }

                //题目副标题=================================
                if ("--".equals(tmfbts[i])) {
                    tmDO.setTmfbt(tmfbts[i]);
                }

                //是否答题备注(0否1是),TODO 默认是什么
                tmDO.setSfdtbz("1");
                //TODO 操作人
                tmDO.setCjr(null);
                //状态(0无效1有效),默认是有效
                tmDO.setZt("1");
                //保存题库管理主表
                tkglMapper.insertTmDO(tmDO);

                if (!"填空".equals(tmlxmcs[i])) {
                    Integer index = 1;
                    //不是“填空”的话，需要存选项
                    // TODO 得到返回的主表ID
                    tmxxDO.setTmid(1);
                    if (null != xxnrExcel_1s && xxnrExcel_1s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_1s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_2s && xxnrExcel_2s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_2s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_3s && xxnrExcel_3s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_3s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_4s && xxnrExcel_4s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_4s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_5s && xxnrExcel_5s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_1s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_6s && xxnrExcel_6s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_1s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_7s && xxnrExcel_7s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_7s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_8s && xxnrExcel_8s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_8s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_9s && xxnrExcel_9s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_9s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                    if (null != xxnrExcel_10s && xxnrExcel_10s.length > 0) {
                        tmxxDO = new TmxxDO();
                        tmxxDO.setXxnr(xxnrExcel_10s[i]);
                        tmxxDO.setXxsx(index);
                        index++;
                        tkglMapper.insertTmxxDO(tmxxDO);
                    }
                }
            } else {
                //如果存在的话，做修改处理

            }
        }
        resultMap.put("count",count);
        resultMap.put("msg","success");
        return resultMap;
    }
}
