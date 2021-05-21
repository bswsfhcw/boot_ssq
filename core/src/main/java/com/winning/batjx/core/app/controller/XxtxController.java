package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.app.service.XxtxService;
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
@Api(value = "BATHIM API", tags = "D.xxtx", description = "现场评分")
@RequestMapping("/xxtx")
public class XxtxController {

    @Resource
    private XxtxService xxtxService;

    @ApiOperation(value = "消息提醒", notes = "查询消息列表")
    @PostMapping("/queryXxtxList")
    public ResponseEntity<Object> queryXxtxList(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xxtxService.queryXxtxList(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "消息提醒", notes = "阅读消息")
    @PostMapping("/ydxx")
    public ResponseEntity<Object> ydxx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xxtxService.ydxx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "消息提醒", notes = "删除消息")
    @PostMapping("/scxx")
    public ResponseEntity<Object> scxx(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xxtxService.scxx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
