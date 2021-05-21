package com.winning.batjx.core.khgl.khwj.controller;

import com.mysql.jdbc.StringUtils;
import com.winning.batjx.core.common.BaseController;
import com.winning.batjx.core.khgl.khwj.vo.WjmbVo;
import com.winning.batjx.core.khgl.khwj.service.WjkhService;
import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @program: boot-batjx
 * @description 问卷考核后台
 * @author: yxf
 * @create: 2021-03-02 11:07
 **/

@RestController
@RequestMapping("/wjkh")
@Api(description = "问卷考核", tags = "问卷考核")
public class WjkhController extends BaseController {

    @Autowired
    private WjkhService wjkhService;

    @Value("${file.upload_root_dir}")
    private String fileDir;

    @ApiOperation(value = "获取查询条件方案信息")
    @PostMapping("/khfa")
    public ResponseMessage getKhfas(@RequestParam String khnf,@RequestParam String khpldm){
        ResponseMessage message = new ResponseMessage();
        try {
            message.setCode("T").setMessage("成功");
            message.setData(wjkhService.getKhfas(khnf,khpldm));
        }catch (Exception e){
            logger.error("获取方案信息出错：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @ApiOperation(value = "获取查询条件方案期间信息")
    @PostMapping("/faqj")
    public ResponseMessage getKhfaKhqj(@RequestParam String faid){
        ResponseMessage message = new ResponseMessage();
        try {
            message.setCode("T").setMessage("成功");
            message.setData(wjkhService.getKhfaKhqj(faid));
        }catch (Exception e){
            logger.error("获取方案期间出错：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @ApiOperation(value = "获取查询条件方案被考核对象信息")
    @PostMapping("/khdx")
    public ResponseMessage getKhfaKhdx(@RequestParam String faid,@RequestParam String khqjdm){
        ResponseMessage message = new ResponseMessage();
        try {
            message.setCode("T").setMessage("成功");
            message.setData(wjkhService.getKhfaKhdx(faid,khqjdm));
        }catch (Exception e){
            logger.error("获取方案信息出错：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @ApiOperation(value = "查询列表")
    @PostMapping("/tablelist")
    public ResponseMessage tablelist(@RequestBody Map para){
        ResponseMessage message = new ResponseMessage();
        try {
            Integer pageNum = (Integer) para.get("pageNum");
            Integer pageSize = (Integer)para.get("pageSize");
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNum(pageNum == null ? PAGE_NUM : pageNum);
            pageRequest.setPageSize(pageSize == null ? PAGE_SIZE : pageSize);
            PageResult pageResult = wjkhService.getPageList(pageRequest,para);
            message.setCode("T").setMessage("成功");
            message.setData(pageResult);
        } catch (Exception e){
            logger.error("查询问卷考核列表出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fileupload(@RequestParam("fjfile") MultipartFile fjfile,@RequestParam Map para) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try{
            wjkhService.insertFile(fjfile,para);
            map.put("msg", "YES");
        }catch (Exception e){
            map.put("msg", "NO");
            logger.error(e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/removefile", method = RequestMethod.POST)
    public ResponseMessage removefile(@RequestParam Map para) throws IOException {
        ResponseMessage message = new ResponseMessage();
        try{
            wjkhService.removefile(para);
            message.setCode("T").setMessage("成功");
        }catch (Exception e){
            message.setCode("F").setMessage("失败");
            logger.error(e.getMessage());
        }
        return message;
    }



    @ApiOperation(value = "通过问卷模板编码获取模板具体信息")
    @PostMapping("/quesResult")
    public ResponseMessage getResult(@RequestParam Map para){
        ResponseMessage message = new ResponseMessage();
        try {
            message.setCode("T").setMessage("成功");
            message.setData(wjkhService.getResult(para));
        }catch (Exception e){
            logger.error("获取方案信息出错：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }


    @ApiOperation(value = "保存")
    @PostMapping("/saveQues")
    public ResponseMessage saveQues(@RequestBody WjmbVo wjmbVo, @RequestParam Map para){
        ResponseMessage message = new ResponseMessage();
        try {
            message.setCode("T").setMessage("成功");
            wjkhService.saveAnswer(wjmbVo,para);
            message.setData(null);
        }catch (Exception e){
            logger.error("保存：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam Map para, HttpServletResponse response) throws Exception{
        String filename = (String) para.get("showname");
        String ljname = (String) para.get("ljname");
        response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
        if(StringUtils.isNullOrEmpty(fileDir)){
            fileDir = "d://wjkh";
        }
        InputStream in = new FileInputStream(new File(fileDir+"\\"+ljname));
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer,0,len);//将缓冲区的数据输出到客户端浏览器
        }
        in.close();
        out.flush();
        out.close();
    }

}
