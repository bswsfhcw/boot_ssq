package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.khgl.khwj.domain.PicDTO;
import com.winning.batjx.core.app.service.XcpfService;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhe
 */
@RestController
@Api(value = "BATHIM API", tags = "D.xcpf", description = "现场评分")
@RequestMapping("/xcpf")
public class XcpfController {

    @Resource
    private XcpfService xcpfService;


    @ApiOperation(value = "方案列表", notes = "查询方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zclsh", value = "zclsh", defaultValue = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jgbm", value = "jgbm", defaultValue = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "khnf", value = "考核年份", defaultValue = "2020", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "khpl", value = "考核频率", defaultValue = "1", dataType = "string", paramType = "query")
    })
    @GetMapping("/queryKhfa")
    public ResponseEntity<Object> getPermissionList(
            @RequestParam(required = true) String zclsh,
            @RequestParam(required = true) String jgbm,
            @RequestParam(required = true) String khnf,
            @RequestParam(required = true) String khpl) {

        Map<String,Object> params = new HashMap<>();
        params.put("zclsh",zclsh);
        params.put("jgbm",jgbm);
        params.put("khnf",khnf);
        params.put("khpl",khpl);
        ResponseMessage message = new ResponseMessage();
        message.setCode("T")
                .setMessage("success")
                .setData(xcpfService.queryKhfa(params));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "考核对象列表", notes = "查询考核对象列表")
    @GetMapping("/queryKhdx")
    public ResponseEntity<Object> queryKhdx(@RequestParam Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xcpfService.queryKhdx(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "考核指标状态列表", notes = "查询考核指标状态列表")
    @GetMapping("/queryKhzbZtList")
    public ResponseEntity<Object> queryKhzbZtList(@RequestParam Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xcpfService.queryKhzbZtList(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "考核指标列表", notes = "查询考核指标列表")
    @GetMapping("/queryKhzbList")
    public ResponseEntity<Object> queryKhzbList(@RequestParam Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xcpfService.queryKhzbList(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "指标图片列表", notes = "查询考核指标图片列表")
    @GetMapping("/queryKhzbPics")
    public ResponseEntity<Object> queryKhzbPics(@RequestParam Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xcpfService.queryKhzbPics(params));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/uploadPics")
    public ResponseEntity<Object> uploadPics(@RequestBody PicDTO pic)
    {
        ResponseMessage message = new ResponseMessage();;
        message.setCode("T")
                .setMessage("success")
                .setData(xcpfService.uploadPics(pic));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
