package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.app.service.QyjxService;
import com.winning.batjx.core.app.service.ReportAnalysisService;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description: 报表分析查询（工作量、工作质量、奖金 app）
 * @author: yyh
 * @create: 2021-03-29 13:40
 **/

@RestController
@Api(value = "BATHIM API", tags = "D.xcpf", description = "工作质量统计")
@RequestMapping("/bbcx")
public class ReportAnalysisController {

    @Autowired
    private ReportAnalysisService reportAnalysisService;

    @Resource
    private QyjxService qyjxService;

    @ApiOperation(value = "方案列表", notes = "查询方案列表")
    @GetMapping("/queryKhfa")
    public ResponseMessage getKhfaList(@RequestParam Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        try{
            message.setCode("T").setData(reportAnalysisService.queryKhfa(params));
        }catch (Exception e){
            message.setCode("F").setMessage("系统异常！");
        }
        return  message;
    }

    @ApiOperation(value = "查询汇总信息", notes = "查询汇总信息")
    @GetMapping("/getKhdfHz")
    public ResponseMessage getKhdfHz(@RequestParam Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        try{
            message.setCode("T").setMessage("success").setData(reportAnalysisService.getKhdfHz(params));
        }catch (Exception e){
            e.printStackTrace();
            message.setCode("F").setMessage("系统异常！");
        }
        return message;
    }

    @ApiOperation(value = "查询被考核机构汇总", notes = "查询被考核机构汇总")
    @PostMapping("/getKhjgdf")
    public ResponseMessage getKhjgdf(@RequestParam Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        try{
            message.setCode("T").setMessage("success").setData(reportAnalysisService.getKhdxDfmx(params));
        }catch (Exception e){
            e.printStackTrace();
            message.setCode("F").setMessage("系统异常！");
        }
        return message;
    }

    @ApiOperation(value = "查询弹出页明细数据", notes = "查询弹出页明细数据")
    @PostMapping("/getJgZbdf")
    public ResponseMessage getJgZbdf(@RequestParam Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        try{
            message.setCode("T").setMessage("success").setData(reportAnalysisService.getKhdfMxList(params));
        }catch (Exception e){
            message.setCode("F").setMessage("系统异常！");
        }
        return message;
    }

}
