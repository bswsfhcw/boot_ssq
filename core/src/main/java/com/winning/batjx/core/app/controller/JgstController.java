package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.app.service.JgstService;
import com.winning.batjx.core.app.service.ZtstService;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/jgst")
@Api(description = "机构视图")
public class JgstController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JgstController.class);

    @Autowired
    @Qualifier("ztstService")
    private ZtstService ztstService;

    @Autowired
    @Qualifier("jgstService")
    private JgstService jgstService;


    @GetMapping(value = "/queryKhplJgst" )
    @ApiOperation(value = "机构视图根据考核年份查询考核频率")
    public ResponseEntity<Object> queryKhplJgst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = jgstService.queryJxkhplList(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value = "/queryFaJgst")
    @ApiOperation(value = "机构视图根据年份以及考核频率查询考核方案")
    public ResponseEntity<Object> queryFaJgst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = jgstService.queryJxfaList(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;

    }

    @GetMapping(value="/khjgfxGraph")
    @ApiOperation(value = "机构视图单元名片视图")
    public ResponseEntity<Object> khjgfxGraph(@RequestParam Map<String, Object> params){
        //入参：jgbm 、 rygh
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = this.jgstService.khjgfxGraph(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value="/getZbDataJgst")
    @ApiOperation(value = "机构视图指标值卡片")
    public ResponseEntity<Object> getZbDataJgst(@RequestParam Map<String, Object> params) throws Exception{
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = jgstService.selectKhhzxx(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value = "/getFafllxJgst" )
    @ApiOperation(value = "机构视图子视图方案的分类类型")
    public ResponseEntity<Object> getFafllxJgst(@RequestParam Map<String, Object> params) throws Exception {
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        String res = null;
        try {
            res = jgstService.getKhzqList(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value="/gzlOrgzzlTop10Jgst")
    @ApiOperation(value = "工作量与工作质量Top10视图")
    public ResponseEntity<Object> gzlOrgzzlTop10Jgst(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = this.jgstService.gzlOrgzzlTop10Graph(params);
            message.setCode("T").setMessage("success").setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }


    @GetMapping(value="/jxqsGraphJgst")
    @ApiOperation(value = "绩效趋势视图")
    public ResponseEntity<Object> jxqsGraphJgst(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> result = null;
        ResponseMessage message = new ResponseMessage();
        List<Map<String, Object>> res = null;
        try {
            res = jgstService.jxqsGraph(params);
            message.setCode("T").setMessage("success").setData(res);
            result = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }



























    @PostMapping(value = "/init")
    @ApiOperation(value = "初始化页面")
    public ModelAndView init(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response)
            throws Exception{
        Calendar date = Calendar.getInstance();
        String curYear = String.valueOf(date.get(Calendar.YEAR));
        //TODO --------------------------ShiroUtils.getLoginUser().getJgbm()
        String jgbm = null;
        params.put("khnf",curYear);
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
        return new ModelAndView("jsp/jgst/jgstInit",params);
    }
    





    

    
    
    
    /**
     * 指标设置弹出框
     * @param paramsr
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/zbszWindow")
    @ApiOperation(value = "指标设置弹出框")
    public ModelAndView zbszWindow(@RequestBody Map<String, Object> paramsr, HttpServletRequest request, HttpServletResponse response) throws Exception{
        return new ModelAndView("main/jgst/zbszWindow",paramsr);
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
    public void saveZdzbTable(@RequestBody Map<String, Object> params,  HttpServletRequest request,
                              HttpServletResponse response) throws Exception{
        try{
        	jgstService.saveZbImport(params);
        }catch (Exception e){
        	LOGGER.error(e.getMessage());
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
            return this.jgstService.queryZdzbTable(params);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
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
    public ModelAndView zbszImport(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("main/jgst/zbszImport",params);
    }
    
    /**
           * 指标数初始化
     * @param params
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/initzbTree")
    @ResponseBody
    @ApiOperation(value = "指标树初始化")
    public List<Map<String,Object>> initzbTree(@RequestBody Map<String, Object> params,HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Object>> res = null;
        try {
            res = this.jgstService.initZbTree(params);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
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
    @PostMapping(value="/jxfxGraph")
    @ApiOperation(value = "绩效分析视图")
    @ResponseBody
    public List<Map<String,Object>> jxfxGraph(@RequestBody Map<String, Object> params,
                                              HttpServletRequest request,HttpServletResponse response){
    	List<Map<String, Object>> res = null;
    	try {
    		res = this.jgstService.jxfxGraph(params);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
    	return res;
    }
    

    

}
