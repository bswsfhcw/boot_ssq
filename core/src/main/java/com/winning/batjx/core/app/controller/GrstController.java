package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.app.service.GrstService;
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

@RestController
@RequestMapping("/grst")
@Api(description = "个人视图")
public class GrstController {

    private static Log log = LogFactory.getLog(GrstController.class);

    @Autowired
    @Qualifier("grstService")
    private GrstService grstService;


    @GetMapping(value = "/queryKhplGrst")
    @ApiOperation(value = "个人视图查询考核汇总表的考核频率")
    public ResponseEntity<Object> queryKhplGrst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = grstService.queryJxkhplList(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value = "/queryFaGrst")
    @ApiOperation(value = "个人视图查询考核汇总表的方案")
    public ResponseEntity<Object> queryFaGrst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = grstService.queryJxfaList(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }



    @GetMapping(value = "/khjgfxGraphGrst")
    @ApiOperation(value = "个人视图考核结果明细")
    public ResponseEntity<Object> khjgfxGraph(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = this.grstService.khjgfxGraph(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }




    @GetMapping(value = "/getZbDataGrst")
    @ApiOperation(value = "个人视图指标值卡片")
    public ResponseEntity<Object> getZbDataGrst(@RequestParam Map<String, Object> params) throws Exception{
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = grstService.selectKhhzxx(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value = "/getRightSelListGrst")
    @ApiOperation(value = "子视图右上角选择框")
    public ResponseEntity<Object> getRightSelListGrst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        String res = null;
        try {
            res = grstService.getKhzqList(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;

    }


    @GetMapping(value = "/gzlOrgzzlTop10Grst")
    @ApiOperation(value = "个人视图工作量与工作质量Top10视图")
    public  ResponseEntity<Object> gzlOrgzzlTop10Grst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = this.grstService.gzlOrgzzlTop10Graph(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @GetMapping(value = "/jxqsGraphGrst")
    @ApiOperation(value = "个人视图绩效趋势视图")
    public ResponseEntity<Object> jxqsGraph(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = this.grstService.jxqsGraph(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }






































    @PostMapping(value = "init")
    @ApiOperation(value = "个人视图页面初始化")
    public ModelAndView init(@RequestBody Map<String, Object> params,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        Calendar date = Calendar.getInstance();
        String curYear = String.valueOf(date.get(Calendar.YEAR));
        //TODO==============================
        String jgbm = null;
        params.put("jgbm",jgbm);
        params.put("khnf", curYear);
        //获取最近5年内数据
        List<HashMap> ndList = new ArrayList<>();
        HashMap ndItem = null;
        for(int nd= Integer.parseInt(curYear),ndj=0 ;ndj<5;ndj++){
            ndItem = new HashMap();
            ndItem.put("ndVal",nd-ndj);
            ndItem.put("ndTxt",nd-ndj+"年");
            ndList.add(ndItem);
        }
        params.put("ndList",ndList);
        return new ModelAndView("jsp/grst/grstInit",params);
    }
    








    

    
    
    
    /**
     * 指标设置弹出框
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/zbszWindow")
    @ApiOperation(value = "指标设置弹出框")
    public ModelAndView zbszWindow(@RequestBody Map<String, Object> params,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{
        return new ModelAndView("main/grst/zbszWindow",params);
    }
    
    /**
     * 保存重点指标
     * @param params
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping(value = "/saveZdzbTable")
    @ApiOperation(value = "指标配置的保存")
    public void saveZdzbTable(@RequestBody Map<String, Object> params,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
        	grstService.saveZbImport(params);
        }catch (Exception e){
            log.error(e);
			//MessageStreamResult.msgStreamResult(response,"error");
        }
    }
    
    /**
     * 重点指标展示界面
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/biZstZdzbTableShow")
    @ApiOperation(value = "重点指标配置的表格")
    @ResponseBody
    public List<Map<String,Object>> biZstZdzbTableShow(@RequestBody Map<String, Object> params,
                                                       HttpServletRequest request, HttpServletResponse response) {
        try{
            return this.grstService.queryZdzbTable(params);
        }catch (Exception e){
            log.error(e);
            return null;
        }
    }
    /**
     * 指标设置
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/zbszImport")
    @ApiOperation(value = "重点指标树设置")
    public ModelAndView zbszImport(@RequestBody Map<String, Object> params,
                                   HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("main/grst/zbszImport",params);
    }
    
    /**
     * 指标数初始化
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/initzbTree")
    @ApiOperation(value = "指标树初始化")
    @ResponseBody
    public List<Map<String,Object>> initzbTree(@RequestBody Map<String, Object> params,
                                               HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> res = null;
        try {
            res = this.grstService.initZbTree(params);
        }catch (Exception e){
            log.error(e);
        }
        return res;
    }
    

    
    /**
      * 绩效分析
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/jxfxGraph")
    @ApiOperation(value = "绩效分析视图")
    @ResponseBody
    public List<Map<String,Object>> jxfxGraph(@RequestBody Map<String, Object> params,
                                              HttpServletRequest request,HttpServletResponse response){
    	List<Map<String, Object>> res = null;
    	try {
    		res = this.grstService.jxfxGraph(params);
		} catch (Exception e) {
            log.error(e);
		}
    	return res;
    }
    

    

}
