package com.winning.batjx.core.khgl.khwj.controller;


import com.winning.batjx.core.common.BaseController;
import com.winning.batjx.core.khgl.khwj.domain.TmDO;
import com.winning.batjx.core.khgl.khwj.domain.TmExcel;
import com.winning.batjx.core.khgl.khwj.service.TkglService;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.batjx.core.common.util.excel.EasyexcelUtils;
import com.winning.batjx.core.common.util.excel.ExcelLogs;
import com.winning.batjx.core.common.util.excel.TmscExcelUtil;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by thinkpad on 2021/3/3.
 */
@RestController
@RequestMapping("/tkgl")
@Api(description = "区域绩效题库管理", tags = "009-区域绩效题库管理接口")
public class TkglController extends BaseController {

    @Autowired
    @Qualifier("tkglService")
    private TkglService tkglService;

    @ApiOperation(value = "查询题目列表")
    @PostMapping("/queryTmPageList")
    public ResponseMessage queryTmPageList(@RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
                PageResult pageResult = tkglService.getTmDOList(params);
                message.setCode("T").setMessage("成功");
                message.setData(pageResult);
        } catch (Exception e){
            logger.error("查询题目列表出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @ApiOperation(value = "获取当天最新产生的题目代码")
    @PostMapping("/getCurTmbh")
    public ResponseEntity<Object> getCurTmbh(@RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        Map<String, Object> res = new HashMap<>();
        try {
            if(null==params){
                params = new HashMap<>();
            }
            res = tkglService.createCurTmbh(params);
            message.setCode("T")
                    .setMessage("success")
                    .setData(res);
        } catch (Exception e){
            logger.error("获取当天最新产生的题目代码，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存题目")
    @RequestMapping("/saveTmDO")
    public ResponseMessage saveTmDO(@RequestBody Map<String, Object> params) {
        ResponseMessage message = new ResponseMessage();
        try {
            if(null != params){
                String tmbm = (String) params.get("tmbm");
                String tmlx = (String) params.get("tmlx");
                boolean sfdtbz = (boolean) params.get("sfdtbz");
                String tmmc = (String) params.get("tmmc");
                String tmfbt = (String) params.get("tmfbt");
                List<Map<String,Object>> tmxx = params.get("tmxx")==null?null:(List<Map<String,Object>>) params.get("tmxx");

                TmDO tmDO = new TmDO();
                tmDO.setTmbm(tmbm);
                tmDO.setTmlx(tmlx);
                tmDO.setSfdtbz(sfdtbz?"1":"0");
                tmDO.setTmmc(tmmc);
                tmDO.setTmfbt(tmfbt);

                //保存题目主表信息
                tkglService.saveTmDO(tmDO,tmxx);
            }
            message.setCode("T").setMessage("成功");
        }catch (Exception e){
            logger.error("保存题目对照出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }


    @ApiOperation(value = "查询题目详情")
    @PostMapping("/queryTmDetail")
    public ResponseMessage queryTmDetail(@RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
            TmDO tmDO = tkglService.getTmDetail(params);
            message.setCode("T").setMessage("成功");
            message.setData(tmDO);
        } catch (Exception e){
            logger.error("查询题目详情出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }


    @ApiOperation(value = "删除题目")
    @PostMapping("/delTmByParams")
    public ResponseMessage delTmByParams(@RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
            tkglService.delTmByParams(params);
            message.setCode("T").setMessage("成功");
        } catch (Exception e){
            logger.error("删除题目出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @ApiOperation(value = "下载题目导入模板")
    @RequestMapping(value ="/downloadTmglTemplate")
    public void downloadTmglTemplate(
            HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        try{
            String userAgent  = request.getHeader("USER-AGENT");
            String filename = "题目导入模板.xlsx";
            if(StringUtils.contains(userAgent, "MSIE")) {
                // IE浏览器
                filename = URLEncoder.encode(filename, "UTF-8");
            }else if (StringUtils.contains(userAgent, "Mozilla")) {
                // google,火狐浏览器
                filename = new String(filename.getBytes(), "ISO8859-1");
            } else {
                filename = URLEncoder.encode(filename, "UTF8");
                // 其他浏览器
            }
            response.setHeader("content-disposition", "attachment;filename="+ filename);
            InputStream in = this.getClass().getResourceAsStream(
                    "/excelTemplate/tkglTemplate.xlsx");
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                out.write(buffer,0,len);
            }
            in.close();
            out.flush();
            out.close();
        }catch (Exception e){
            logger.error("下载题目导入模板：",e);
        }
    }






    @RequestMapping(value = "/exclInsertTkmb", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> exclInsertTkmb(@RequestParam("excelFile") MultipartFile excelFile) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String tkmbUploadFlag = "Y";
        try{
            //https://blog.csdn.net/zcyzsy/article/details/84572920?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.control&dist_request_id=1328690.19778.16166375935090633&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.control
            Collection<Map> importExcel = null;
            ExcelLogs logs = new ExcelLogs();
            String fileShowName=excelFile.getOriginalFilename();
            importExcel = TmscExcelUtil.importMyExcel(fileShowName,
                    Map.class,
                    excelFile.getInputStream(),
                    "yyyy-MM-dd",
                    logs,
                    0);
            tkglService.saveUploadExclTmDO(importExcel);
            map.put("tkmbUploadFlag", tkmbUploadFlag);
        }catch (Exception e){
            tkmbUploadFlag="N";
            map.put("tkmbUploadFlag", tkmbUploadFlag);
            logger.error(e.getMessage());
        }
        return map;
    }














    /**
     * 返回前台供预览
     * @param map
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "上传题目预览")
    @PostMapping(value = "/tmUploadShow")
    @ResponseBody
    public List<TmExcel> tmUploadShow(@PathVariable(required = false) Map<String,Object> map,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        try {
            List<TmExcel> excels = EasyexcelUtils.readExcel(multipartRequest.getFile("userExcelFileName").getInputStream(),TmExcel.class);
            return excels;
        } catch (Exception e) {
            logger.error("上传题目预览错误",e.getMessage());
            //MessageStreamResult.msgStreamResult(response,"上传题目预览错误！");
        }
        return null;
    }

    @ApiOperation(value = "导入题目数据保存")
    @PostMapping(value = "/saveTmExcel" )
    @ResponseBody
    public Map<String,Object> saveTmExcel(@PathVariable(required = false) Map<String,Object> map,
                                          HttpServletResponse response){
        try {
            return tkglService.saveTmExcel(map);
        }catch (Exception e){
            logger.error("导入题目数据保存错误",e.getMessage());
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("msg","error");
        return resultMap;
    }






}
