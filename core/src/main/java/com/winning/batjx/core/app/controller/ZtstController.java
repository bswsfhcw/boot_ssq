package com.winning.batjx.core.app.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;
import com.winning.batjx.core.app.service.ZtstService;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/** 总体视图BI
 *  cnadmin 院内
 * Created by thinkpad on 2019/6/6.
 */
@RestController
@RequestMapping("/ztst")
@Api(description = "总体视图")
public class ZtstController  {
    private static Log log = LogFactory.getLog(ZtstController.class);

    @Autowired
    @Qualifier("ztstService")
    private ZtstService ztstService;


    @GetMapping(value="/getKhplOrFaxxByKhnf")
    @ApiOperation(value = "根据考核年份得出考核频率或者方案信息")
    public ResponseEntity<Object> getKhplOrFaxxByKhnf(@RequestParam Map<String, Object> params){
        //入参：jgbm 、 rygh
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<QyjxKhhzVo> res = new ArrayList<>();
        try{
            String jgbm = null;
            //判断当前登录人所属机构层级 ： 县级机构负责人 、 医共体领头单位负责人 、 机构负责人
            HashMap<String, Object> curLoginUserJgdj = ztstService.queryCurLoginUserJgdjByRygh(params);
            String rlrSzjgdj = curLoginUserJgdj.get("val")==null?null:curLoginUserJgdj.get("val").toString();
            if("2".equals(rlrSzjgdj)){
                //params.put("jgbm",jgbm);
                //区县级 ： 可以看见归档的方案 自己以及下级医共体创建的方案
                //根据系统当前年度找方案
                List<QyjxKhhzVo> fazzList_QX = ztstService.queryBizstByNd(params);
                // 医共体的方案
                List<QyjxKhhzVo> fazzList_YGT = ztstService.queryBizstYgtFaByNd(params);
                res.addAll(fazzList_QX);
                res.addAll(fazzList_YGT);

            }else{
                if("7".equals(rlrSzjgdj)){
                    //【医共体】：判断当前登录人是否是医共体下的管理人员，如果是则取医共体的直属上级的机构编码
                    //params.put("jgbm",jgbm);
                    List<QyjxKhhzVo> resSelf = ztstService.queryBizstByNd(params);
                    List<HashMap<String, Object>> ygtList = ztstService.queryCurLoginUserIsYgtByJgbm(params);
                    if(null !=ygtList && ygtList.size()>0){
                        //取医共体的上级机构
                        params.put("jgbm",ygtList.get(0).get("jgbm"));
                    }
                    List<QyjxKhhzVo> resSjfaxx = ztstService.queryBizstByNd(params);
                    res.addAll(resSjfaxx);
                    res.addAll(resSelf);
                }else{
                    //社区中心：根据系统当前年度找方案
                    //params.put("jgbm",jgbm);
                    res = ztstService.queryBizstByNd(params);
                }
            }
            //根据选择的年份，得到该年份下所有方案不重复的考核频率------------------------------------------------------
            if("PLXX".equals(params.get("changeF"))){
                HashMap plItem = null;
                List<HashMap> plRes = null;
                Set<String> plSet = null;
                if(null != res && res.size()>0){
                    plSet = new HashSet<>();
                    for(QyjxKhhzVo item : res){
                        plSet.add(item.getKhpl());
                    }
                    if(plSet.size()>0){
                        plRes=new ArrayList<>();
                        for(int pl=1;pl<5;pl++){
                            boolean existFlag = false;
                            for(String expl : plSet){
                                if(!existFlag && String.valueOf(pl).equals(expl)){
                                    plItem = new HashMap();
                                    if("1".equals(expl)){
                                        plItem.put("plmc","月度");
                                    }
                                    if("2".equals(expl)){
                                        plItem.put("plmc","季度");
                                    }
                                    if("3".equals(expl)){
                                        plItem.put("plmc","半年");
                                    }
                                    if("4".equals(expl)){
                                        plItem.put("plmc","年度");
                                    }
                                    plItem.put("plVal",expl);
                                    plRes.add(plItem);
                                    existFlag = true;
                                }
                            }
                        }
                        //plRes 已经得到不重复的考核频率
                    }
                }
                if(null == plRes){
                    return null;
                }else{
                    String plResTxt=JSON.toJSONString(plRes);
                    message.setCode("T")
                            .setMessage("success")
                            .setData(plResTxt);
                    result = new ResponseEntity<>(message, HttpStatus.OK);
                }
            }

            //根据选择的年份、考核频率 得到方案信息--------------------------------------------------------------
            if("FAXX".equals(params.get("changeF"))){
                if(null == res){
                    return null;
                }else{
                    String faxxResTxt=JSON.toJSONString(res);
                    message.setCode("T")
                            .setMessage("success")
                            .setData(faxxResTxt);
                    result = new ResponseEntity<>(message, HttpStatus.OK);
                }
            }
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }




    /**
     * 查询该年度、该考核频率下，当前登录人所能看见的考核方案
     * @param params
     * @return
     */
    @GetMapping("/queryKhfa_ztst")
    @ApiOperation(value = "查询条件下考核方案下拉框")
    public ResponseEntity<Object> getPermissionList(@RequestParam Map<String,Object> params) {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        Map<String,Object> res = new HashMap<>();
        List<HashMap> ndList = new ArrayList<>();
        HashMap ndItem = null;
        List<QyjxKhhzVo> faxxList = new ArrayList<>();
        try{
            Calendar date = Calendar.getInstance();
            String curYear = String.valueOf(date.get(Calendar.YEAR));
            //仅仅查询最近5年
            /*
            for(int nd= Integer.parseInt(curYear),ndj=0 ;ndj<5;ndj++){
                ndItem = new HashMap();
                ndItem.put("ndVal",nd-ndj);
                ndItem.put("ndTxt",nd-ndj+"年");
                ndList.add(ndItem);
            }
            res.put("ndList",ndList);
            //当前年份
            params.put("khnf",curYear);
            */
            //是否允许设置重点指标--------------------------------------------------------------------------------------
            //判断 - 医共体信息 （入参：当前登录人的 jgbm ）
            //params.put("jgbm","001");
            List<HashMap<String, Object>> ygtList = ztstService.queryCurLoginUserIsYgtByJgbm(params);
            if(null !=ygtList && ygtList.size()>0){
                //重点指标医共体设置标识
                res.put("zdzbYgtSzBz","N");
            }else{
                res.put("zdzbYgtSzBz","Y");
            }

            //判断当前登录人所属机构层级 ： 县级机构负责人 、 医共体领头单位负责人 、 机构负责人（入参：人员工号）
            HashMap<String, Object> curLoginUserJgdj = ztstService.queryCurLoginUserJgdjByRygh(params);
            String rlrSzjgdj = curLoginUserJgdj.get("val")==null?null:curLoginUserJgdj.get("val").toString();
            if("2".equals(rlrSzjgdj)){
                //区县级 ： 可以看见归档的方案 自己以及下级医共体创建的方案
                //根据系统当前年度找方案（入参：jgbm、khnf、khpl）
                List<QyjxKhhzVo> fazzList_QX = ztstService.queryBizstByNd(params);
                // 医共体的方案
                List<QyjxKhhzVo> fazzList_YGT = ztstService.queryBizstYgtFaByNd(params);
                //区县方案
                faxxList.addAll(fazzList_QX);
                //医共体方案
                faxxList.addAll(fazzList_YGT);
            }else{
                if("3".equals(rlrSzjgdj)){
                    //区县级 ： 可以看见归档的方案 自己以及下级医共体创建的方案
                    //根据系统当前年度找方案
                    List<QyjxKhhzVo> fazzList_QX = ztstService.queryBizstByNd(params);
                    // 医共体的方案
                    List<QyjxKhhzVo> fazzList_YGT = ztstService.queryBizstYgtFaByNd(params);
                    faxxList.addAll(fazzList_QX);
                    faxxList.addAll(fazzList_YGT);
                }else{
                    //根据系统当前年度找方案
                    faxxList = ztstService.queryBizstByNd(params);
                }
            }
            //params.put("fazzList",faxxList);
            message.setCode("T")
                    .setMessage("success")
                    .setData(faxxList);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
           log.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value="/changeFaxxGetFafl")
    @ApiOperation(value = "总体视图方案的切换得到方案分类")
    public ResponseEntity<Object> changeFaxxGetFafl(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        try{
            // 工作量分类:1 ; 工作质量分类:2 ; 质控分类:3
            List<HashMap<String, Object>> fllx = ztstService.queryCurFafllx(params);
            String fllxTxt=JSON.toJSONString(fllx);
            message.setCode("T")
                    .setMessage("success")
                    .setData(fllxTxt);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }

    @GetMapping(value="/getBizstKpDatas")
    @ApiOperation(value = "重点指标分析卡片部分")
    public ResponseEntity<Object> getBizstKpDatas(
            @RequestParam Map<String,Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        try{
            //入参 ： jgbm 、
            List<HashMap<String, Object>> kpxxList = ztstService.calculBizstZdzbKp(params);
            if(null == kpxxList || kpxxList.size()< 1){
                return result;
            }else{
                message.setCode("T")
                        .setMessage("success")
                        .setData(kpxxList);
                result = new ResponseEntity<>(message, HttpStatus.OK);
            }
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }

    @GetMapping(value="/bizstJxfxGraph")
    @ApiOperation(value = "总当量、得分总体分析")
    public ResponseEntity<Object>  bizstJxfxGraph(@RequestParam Map<String,Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        try{
            if(null!=params.get("bizstJxfxSel") && StringUtil.isNotEmpty(params.get("bizstJxfxSel").toString())
                    && !"undefined".equals(params.get("bizstJxfxSel").toString())){
                String res  = ztstService.bizstJxfxGraph(params);
                message.setCode("T")
                        .setMessage("success")
                        .setData(res);
                result = new ResponseEntity<>(message, HttpStatus.OK);
            }
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }


    @GetMapping(value="/bizstJxqsGraph")
    @ApiOperation(value = "总当量、得分趋势分析")
    public ResponseEntity<Object> bizstJxqsGraph(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        try{
            if(null!=params.get("bizstJxqsSel") && StringUtil.isNotEmpty(params.get("bizstJxqsSel").toString())
                    && !"undefined".equals(params.get("bizstJxqsSel").toString())
                    && null!=params.get("khpl") && StringUtil.isNotEmpty(params.get("khpl").toString())
                    && !"undefined".equals(params.get("khpl").toString())){
                String res =ztstService.bizstJxqsGraph(params);
                message.setCode("T")
                        .setMessage("success")
                        .setData(res);
                result = new ResponseEntity<>(message, HttpStatus.OK);
            }
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }


    @GetMapping(value="/bizstKhjgfxGraph")
    @ApiOperation(value = "占比分析视图")
    public ResponseEntity<Object> bizstKhjgfxGraph(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        try{
            if(null!=params.get("jgfxSel") && StringUtil.isNotEmpty(params.get("jgfxSel").toString())
                    && !"undefined".equals(params.get("jgfxSel").toString())){
                String res =ztstService.bizstKhjgfxGraph(params);
                message.setCode("T")
                        .setMessage("success")
                        .setData(res);
                result = new ResponseEntity<>(message, HttpStatus.OK);
            }
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }


    @GetMapping(value="/bizstPmtop10")
    @ApiOperation(value = "Top10视图")
    public ResponseEntity<Object> bizstPmtop10(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        try{
            if(null!=params.get("top10Sel") && StringUtil.isNotEmpty(params.get("top10Sel").toString())
                    && !"undefined".equals(params.get("top10Sel").toString())){
                String res =ztstService.bizstPmtop10(params);
                message.setCode("T")
                        .setMessage("success")
                        .setData(res);
                result = new ResponseEntity<>(message, HttpStatus.OK);
            }
        }catch (Exception e){
            log.error(e);
        }
        return result;
    }


























    @PostMapping(value = "init")
    @ApiOperation(value = "初始化页面")
    public ModelAndView init(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        HashMap plItem = null;
        List<HashMap> plRes = new ArrayList<>();
        Set<String> plSet = null;
        //TODO==============================
        String jgbm = null;
        String rygh = null;
        //TODO==============================
        Calendar date = Calendar.getInstance();
        try{
            List<QyjxKhhzVo> fazzList = new ArrayList<>();
            //获取最近5年内数据
            String curYear = String.valueOf(date.get(Calendar.YEAR));
            params.put("khnf",curYear);
            List<HashMap> ndList = new ArrayList<>();
            HashMap ndItem = null;
            for(int nd= Integer.parseInt(curYear),ndj=0 ;ndj<5;ndj++){
                ndItem = new HashMap();
                ndItem.put("ndVal",nd-ndj);
                ndItem.put("ndTxt",nd-ndj+"年");
                ndList.add(ndItem);
            }
            params.put("ndList",ndList);

            //是否允许设置重点指标--------------------------------------------------------------------------------------
            //医共体
            params.put("jgbm", jgbm);
            //如果是医共体内的人登录，是不允许进行重点指标的配置，他只能看见上级配置的指标（自己的）
            //医共体的上级机构编码-----------------------------------------------------------------
            List<HashMap<String, Object>> ygtList = ztstService.queryCurLoginUserIsYgtByJgbm(params);
            if(null !=ygtList && ygtList.size()>0){
                //重点指标医共体设置标识
                params.put("zdzbYgtSzBz","N");
            }else{
                params.put("zdzbYgtSzBz","Y");
            }

            params.put("rygh",rygh);
            //判断当前登录人所属机构层级 ： 县级机构负责人 、 医共体领头单位负责人 、 机构负责人
            HashMap<String, Object> curLoginUserJgdj = ztstService.queryCurLoginUserJgdjByRygh(params);
            String rlrSzjgdj = curLoginUserJgdj.get("val")==null?null:curLoginUserJgdj.get("val").toString();
            if("2".equals(rlrSzjgdj)){
                //区县级 ： 可以看见归档的方案 自己以及下级医共体创建的方案
                //根据系统当前年度找方案
                List<QyjxKhhzVo> fazzList_QX = ztstService.queryBizstByNd(params);
                // 医共体的方案
                List<QyjxKhhzVo> fazzList_YGT = ztstService.queryBizstYgtFaByNd(params);
                fazzList.addAll(fazzList_QX);
                fazzList.addAll(fazzList_YGT);

            }else{
                //根据系统当前年度找方案
                fazzList = ztstService.queryBizstByNd(params);
            }


            if(null != fazzList && fazzList.size()>0){
                //该年份下的考核频率列表-------------------------------------------------------------
                plSet = new HashSet<>();
                for(QyjxKhhzVo item : fazzList){
                    plSet.add(item.getKhpl());
                }

                if(plSet.size()>0){
                    for(int pl=1;pl<5;pl++){
                        boolean existFlag = false;
                        for(String expl : plSet){
                            if(!existFlag && String.valueOf(pl).equals(expl)){
                                plItem = new HashMap();
                                if("1".equals(expl)){
                                    plItem.put("plmc","月度");
                                }
                                if("2".equals(expl)){
                                    plItem.put("plmc","季度");
                                }
                                if("3".equals(expl)){
                                    plItem.put("plmc","半年");
                                }
                                if("4".equals(expl)){
                                    plItem.put("plmc","年度");
                                }
                                plItem.put("plVal",expl);
                                plRes.add(plItem);
                                existFlag = true;
                            }
                        }
                    }
                }
                params.put("plRes",plRes);

                //根据年份 + 考核频率得到 其下的方案信息
                params.put("khpl",plRes.get(0)==null?"1":plRes.get(0).get("plVal"));
                List<QyjxKhhzVo> faxxList = new ArrayList<>();
                if("3".equals(rlrSzjgdj)){
                    //区县级 ： 可以看见归档的方案 自己以及下级医共体创建的方案
                    //根据系统当前年度找方案
                    List<QyjxKhhzVo> fazzList_QX = ztstService.queryBizstByNd(params);
                    // 医共体的方案
                    List<QyjxKhhzVo> fazzList_YGT = ztstService.queryBizstYgtFaByNd(params);
                    faxxList.addAll(fazzList_QX);
                    faxxList.addAll(fazzList_YGT);

                }else{
                    //根据系统当前年度找方案
                    faxxList = ztstService.queryBizstByNd(params);
                }
                params.put("fazzList",faxxList);

                //采集周期列表生成--------------------------------------------------------------------------------------
                String top1_khpl = (String) params.get("khpl");
                //产生月度列表
                List<HashMap<String,Object>> cjqjList = new ArrayList<>();
                if(null!=faxxList && faxxList.size()>0){
                    Integer curFaid = faxxList.get(0).getFaid();
                    params.put("faid",curFaid);
                    List<HashMap<String, Object>> faqjList = ztstService.queryBizstFaqjByFaid(params);
                    if(null != faqjList && faqjList.size()>0){
                        //月度
                        if("1".equals(top1_khpl)){
                            HashMap<String,Object> yd = null;
                            for(int i=0;i<faqjList.size();i++){
                                yd = new HashMap<>();
                                yd.put("cjqjval",faqjList.get(i).get("khzq"));
                                yd.put("cjqjTxt",curYear+"年"+faqjList.get(i).get("khzq")+"月");
                                cjqjList.add(yd);
                            }
                        }
                        //季度
                        if("2".equals(top1_khpl)){
                            HashMap<String,Object> jd = null;
                            for(int i=1;i<faqjList.size();i++){
                                jd = new HashMap<>();
                                jd.put("cjqjval",i);
                                jd.put("cjqjTxt",curYear+"年"+i+"季度");
                                cjqjList.add(jd);
                            }
                        }
                        //半年
                        if("3".equals(top1_khpl)){
                            HashMap<String,Object> bn = null;
                            for(int i=1;i<faqjList.size();i++){
                                bn = new HashMap<>();
                                bn.put("cjqjval",i);
                                if(i==1){
                                    bn.put("cjqjTxt",curYear+"年上半年");
                                }else{
                                    bn.put("cjqjTxt",curYear+"年下半年");
                                }
                                cjqjList.add(bn);
                            }
                        }
                        if("4".equals(top1_khpl)){
                            params.put("curkhplmc","年度");
                            HashMap<String,Object> nd = new HashMap<>();
                            nd.put("cjqjval",curYear);
                            nd.put("cjqjTxt",curYear+"年");
                            cjqjList.add(nd);
                        }
                    }
                }
                params.put("cjqjList",cjqjList);
            }else{
                params.put("fazzList",new ArrayList<QyjxKhhzVo>());
            }
        }catch (Exception e){
            log.error(e);
        }
        return new ModelAndView("jsp/ztstbi/ztstbiInit",params);
    }

















    /**
     * top5 柱状图部分
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="/getBizstZdzbTop5")
    @ApiOperation(value = "TOP5柱状图")
    public ModelAndView getBizstZdzbTop5(@RequestBody Map<String, Object> params,HttpServletRequest request,HttpServletResponse response){
        try{
            if("4".equals(params.get("khpl"))){
                params.put("khqj","1");
            }
            String res = ztstService.calculBizstZdzbTop5(params);
            if(null == res ){
                return null;
            }else{
                //TODO
                //MessageStreamResult.msgStreamResult(response,res);
            }
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }









    @PostMapping(value = "/initZdzbTree")
    @ApiOperation(value = "指标树数据")
    public List<HashMap<String,Object>> initZdzbTree(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
        List<HashMap<String,Object>> res = null;
        try {
            if(null==params.get("queryArea") || "".equals(params.get("zbmc"))){
                params.put("queryArea","ALL");
            }
            res = ztstService.zblbTree(params);
            //查询已有的配置项，将指标的勾选框勾选
            List<Map<String,Object>> selectedZdzbList = ztstService.queryBizstZdzbTable(params);
            if(null!=res && res.size()>0){
                for(HashMap<String, Object> item : res){
                    String zbbm = String.valueOf(item.get("id"));
                    for(Map<String,Object> pzzb : selectedZdzbList){
                        if(zbbm.equals(pzzb.get("zbbm"))){
                            item.put("checked","true");
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error(e);
        }
        return res;
    }

    @PostMapping(value = "/saveZdzbAndTop5Table")
    @ApiOperation(value = "指标配置的保存")
    public void saveZdzbAndTop5Table(@RequestBody Map<String, Object> params,  HttpServletRequest request, HttpServletResponse response){
        try{
            responsePrint(response,ztstService.saveBizstZbpz(params));
        }catch (Exception e){
            responsePrint(response,"error");
        }

    }

    //异常分开处理
    public void responsePrint(HttpServletResponse response,Object resultObj){
        try{
            if(resultObj instanceof String){
                // TODO
                //MessageStreamResult.msgStreamResult(response, resultObj.toString());
            }else{
                // TODO
                Map resultMap = (Map)resultObj ;
                //MessageStreamResult.msgStreamResult(response, JSONArray.toJSONString(resultMap));
            }
        }catch (Exception e){
            log.error(e);
        }
    }

    @PostMapping(value = "/biZstZdzbTableShow")
    @ApiOperation(value = "重点指标配置的表格")
    @ResponseBody
    public List<Map<String,Object>> biZstZdzbTableShow(@RequestBody Map<String, Object> params,  HttpServletRequest request, HttpServletResponse response) {
        try{
            return ztstService.queryBizstZdzbTable(params);
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }



}
