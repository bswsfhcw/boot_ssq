package com.winning.batjx.core.app.service.impl;

import com.winning.batjx.core.app.mapper.SpzxMapper;
import com.winning.batjx.core.app.service.SpzxService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/21
 */
@Service
public class SpzxServiceImpl implements SpzxService {
    /**
     * roleMapper object
     */
    private  static  final  Logger loger = LoggerFactory.getLogger(SpzxServiceImpl.class);
    @Resource
    private SpzxMapper spzxMapper;

    @Value("${qyjxUrl.base}")
    private String qyjxUrlBase;
    @Value("${qyjxUrl.actionShtg}")
    private String actionShtg;
    @Value("${qyjxUrl.actionShbtg}")
    private String actionShbtg;
    /**
     * 更加taskId 以及 业务表名称 ywbmc做一下判断
     * 1、是否在act task相关两张表中
     * 2、实际业务表是否有对应流程实例
     * @param params
     * @return
     */
    @Override
    public Map<String,Object> pdspxx(Map<String,Object> params) {
        Map<String,Object>  result= new HashMap<>();
        result.put("lcslid","-1");//流程实例ID
        result.put("zxr","-1");//
        result.put("zxdz","-1");//
        result.put("shlx","-1");// 审核类型 TBSH PJSH JJSH
        result.put("shywid","-1");// 被审核业务表的ID 比如 qyjx_sjtb_lc
        LinkedHashMap<String,Object> spxx  = (LinkedHashMap<String, Object>) params.get("spxx");
        String ywbmc= (String) spxx.get("ywbmc");
        params.put("ywbzjid", spxx.get("ywbzjid"));
        if("act_hi_taskinst".equalsIgnoreCase(ywbmc) || "act_ru_task".equalsIgnoreCase(ywbmc)){
            params.put("tableName", ywbmc);
            params.put("taskid", spxx.get("ywbzjid"));
            Map taskMap = spzxMapper.queryLcslid(params);
            if(taskMap !=null){//流程表 可以找打流程实例
                String lcslid = (String) taskMap.get("lcslid");
                result.put("lcslid",lcslid);
                result.put("zxr",taskMap.get("zxr"));//
                result.put("zxdz",taskMap.get("zxdz"));//
                params.put("lcslid",lcslid);
                List<Map> shlxList = spzxMapper.queryShlx(params);
                loger.info("ShlxList.size:" + shlxList.size());
                if(CollectionUtils.isNotEmpty(shlxList) && shlxList.size()>0){
                    result.put("shlx",shlxList.get(0).get("shlx"));
                    result.put("shywid",shlxList.get(0).get("shywid"));
                }
            }
        }else {
        }
        return result;
    }
    @Override
    public String querySjtbLx(Map<String,Object> params) {
        String result="";
        String shlx= (String) params.get("shlx");
        if("TBSH".equalsIgnoreCase(shlx)){//采集填报
            params.put("shywid", params.get("shywid"));
            result = spzxMapper.querySjtbLx(params);
        }else if("PJSH".equalsIgnoreCase(shlx)){//评价计算

        }else if("JJSH".equalsIgnoreCase(shlx)){//奖金审核 预留

        }else {

        }
        return result;
    }
    @Override
    public List<Map> querySpxxKhdx(Map<String,Object> params) {
        List<Map> result = new ArrayList<>();
        String shlx= (String) params.get("shlx");
        if("TBSH".equalsIgnoreCase(shlx)){//采集填报
            result =  spzxMapper.querySpxxKhdxSjtb(params);
        }else if("PJSH".equalsIgnoreCase(shlx)){//评价计算
            result =  spzxMapper.querySpxxKhdxPjjs(params);
        }else if("JJSH".equalsIgnoreCase(shlx)){//奖金审核 预留

        }else {

        }
        return result;
    }
    @Override
    public Map querySpxx(Map<String,Object> params) {
        Map result = new HashMap();
        List<Map> zblist = new ArrayList<>();
        int bjzbCount = 0;
        String shlx= (String) params.get("shlx");
        LinkedHashMap<String,Object> khdx  = (LinkedHashMap<String, Object>) params.get("khdx");
        params.put("khdxbm",khdx.get("khdxbm"));
        if("TBSH".equalsIgnoreCase(shlx)){//采集填报
            zblist =  spzxMapper.querySpxxSjtb(params);
            bjzbCount = spzxMapper.querybjzbCountSjtb(params);
        }else if("PJSH".equalsIgnoreCase(shlx)){//评价计算
            zblist =  spzxMapper.querySpxxPjjs(params);
        }else if("JJSH".equalsIgnoreCase(shlx)){//奖金审核 预留

        }else {

        }
        result.put("zblist",zblist);
        result.put("bjzbCount",bjzbCount);
        return result;
    }
    @Override
    public List<Map> querySpxxGr(Map<String,Object> params) {
        List<Map> result = new ArrayList<>();
        LinkedHashMap<String,Object> khdxzb  = (LinkedHashMap<String, Object>) params.get("khdxzb");
        String shlx= (String) params.get("shlx");
        params.put("ssksbm", khdxzb.get("khdxbm"));
        params.put("zbbm", khdxzb.get("zbbm"));
        if("TBSH".equalsIgnoreCase(shlx)){//采集填报
            result =  spzxMapper.querySpxxSjtbGr(params);
        }else if("PJSH".equalsIgnoreCase(shlx)){//评价计算
            result =  spzxMapper.querySpxxPjjsGr(params);
        }else if("JJSH".equalsIgnoreCase(shlx)){//奖金审核 预留

        }else {

        }
        return result;
    }

    @Override
    public boolean bjzbList(Map<String,Object> params) {
        String shlx= (String) params.get("shlx");
        ArrayList <Integer>  checkVal = (ArrayList<Integer>) params.get("checkVal");
        String dhyy = (String)params.get("dhyy");
        if("TBSH".equalsIgnoreCase(shlx)){//采集填报
            spzxMapper.bjzbListSjtb(params);
        }else if("PJSH".equalsIgnoreCase(shlx)){//评价计算暂无

        }else if("JJSH".equalsIgnoreCase(shlx)){//奖金审核 预留

        }else {

        }
        return true;
    }

    @Override
    public boolean spxx(Map<String,Object> params) {
        String actionShtg = this.qyjxUrlBase+"/"+this.actionShtg;
        LinkedHashMap<String,Object> spxx  = (LinkedHashMap<String, Object>) params.get("spxx");
        String action = (String) params.get("action");
        if(!"1".equalsIgnoreCase(action)){//不通过
            actionShtg = this.qyjxUrlBase+"/"+this.actionShbtg;
        }
        String shlx= (String) params.get("shlx");
        String tblx= (String)params.get("tblx");
        if("TBSH".equalsIgnoreCase(shlx)){//采集填报
            if("1".equalsIgnoreCase(tblx)){
                tblx="TBSH_SL";
            }else  if("2".equalsIgnoreCase(tblx)){
                tblx="TBSH_ZL";
            }
        }else if("PJSH".equalsIgnoreCase(shlx)){//评价计算
            tblx="PJSH";
        }else if("JJSH".equalsIgnoreCase(shlx)){//奖金审核 预留
            tblx="JJSH";
        }else {

        }
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("jgbm","");
        map.add("zrcy","");
        map.add("tblx",tblx);// TBSH_SL TBSH_ZL PJSH JJSH
        map.add("taskid", String.valueOf((Integer) spxx.get("ywbzjid")));//
        map.add("lcslid", (String) params.get("lcslid"));//
//      actionShtg = this.qyjxUrlBase+"/"+"rsa/getRSAKey";
        RestTemplate restTemplate = new RestTemplate();
        String url = actionShtg;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());
        return true;
    }
}
