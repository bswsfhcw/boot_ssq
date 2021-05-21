package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.app.service.SpzxService;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhe
 */
@RestController
@Api(value = "BATHIM API", tags = "D.spzx", description = "现场评分")
@RequestMapping("/spzx")
public class SpzxController {

    @Resource
    private SpzxService spzxService;

    @ApiOperation(value = "审批消息", notes = "判断审批消息合法性")
    @PostMapping("/pdspxx")
    public ResponseEntity<Object> pdspxx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.pdspxx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "审批消息", notes = "查询数据填报类型")
    @PostMapping("/querySjtbLx")
    public ResponseEntity<Object> querySjtbLx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.querySjtbLx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "审批消息", notes = "查询审批对象列表")
    @PostMapping("/querySpxxKhdx")
    public ResponseEntity<Object> querySpxxKhdx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.querySpxxKhdx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "审批消息", notes = "查询审批消息")
    @PostMapping("/querySpxx")
    public ResponseEntity<Object> querySpxx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.querySpxx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "审批消息", notes = "查询审批个人")
    @PostMapping("/querySpxxGr")
    public ResponseEntity<Object> querySpxxGr(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.querySpxxGr(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "审批消息", notes = "审批消息")
    @PostMapping("/spxx")
    public ResponseEntity<Object> spxx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.spxx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "标记指标", notes = "标记指标")
    @PostMapping("/bjzbList")
    public ResponseEntity<Object> bjzbList(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(spzxService.bjzbList(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
