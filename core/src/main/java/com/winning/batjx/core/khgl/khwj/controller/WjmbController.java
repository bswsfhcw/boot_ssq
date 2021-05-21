package com.winning.batjx.core.khgl.khwj.controller;

import com.alibaba.fastjson.JSONObject;
import com.winning.batjx.core.common.BaseController;
import com.winning.batjx.core.khgl.khwj.domain.*;
import com.winning.batjx.core.khgl.khwj.service.*;

import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
@RestController
@RequestMapping("/wjmb")
@Api(description = "区域绩效问卷模板管理", tags = "001-区域绩效-问卷-模板管理接口")
public class WjmbController extends BaseController {

    @Autowired
    @Qualifier("wjmbService")
    private WjmbService wjmbService;

    @Autowired
    @Qualifier("wjmbflService")
    private WjmbflService wjmbflService;

    @Autowired
    @Qualifier("fltmglService")
    private FltmglService fltmglService;

    @Autowired
    @Qualifier("xxfszService")
    private XxfszService xxfszService;

    @Autowired
    @Qualifier("pfgsService")
    private PfgsService pfgsService;

    /**********************************************问卷模板**********************************************/
    /**
     * @param params
     * @return
     */
    @ApiOperation(position = 1,value = "取问卷模板详细信息供预览",notes = "获取问卷模板详细信息供预览")
    @PostMapping("/getWjmbYl")
    public ResponseEntity<Object> getWjmbYl( @RequestBody Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        int id = (Integer)params.get("id");
        try {
            WjmbDO wjmbDO = wjmbService.getWjmbYl(id);
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbDO);
        } catch (Exception e){
            logger.error("获取问卷模板详细信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @ApiOperation(value = "获取一个自动生成的模板编号",notes = "获取一个自动生成的模板编号")
    @PostMapping("/getWjmbbh")
    public ResponseEntity<Object> getWjmbbh(){
        ResponseMessage message = new ResponseMessage();
        try {
            WjmbDO  wjmbDO = wjmbService.getWjmbbh();
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbDO);
        } catch (Exception e){
            logger.error("获取一个自动生成的模板编号出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**
     * @param params
     * @return
     */
    @ApiOperation(position = 1,value = "复制问卷模板",notes = "复制问卷模板")
    @PostMapping("/fzWjmb")
    public ResponseEntity<Object> fzWjmb( @RequestBody Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
            int idOld =(Integer)(params.get("mbidOld"));
            WjmbDO  wjmbDO = wjmbService.fzWjmb(idOld);
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbDO);
        } catch (Exception e){
            logger.error("复制问卷模板出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**
     * @param params
     * @return
     */
    @ApiOperation(position = 2,value = "分页获取问卷模板信息",notes = "分页")
    @PostMapping("/getWjmbPageList")
    public ResponseMessage getWjmbPageList(@RequestBody Map<String,Object> params){
        WjmbDO wjmbDOP = JSONObject.parseObject(JSONObject.toJSONString(params), WjmbDO.class);

        int pageNum =params.get("pageNum")==null?1: (Integer)params.get("pageNum");
        int pageSize =params.get("pageSize")==null?20:(Integer)params.get("pageSize");
        ResponseMessage message = new ResponseMessage();
        try {
            PageRequest pageRequest=new PageRequest();
            pageRequest.setPageNum(pageNum);
            pageRequest.setPageSize(pageSize);
            PageResult pageResult=wjmbService.getWjmbDO(wjmbDOP,pageRequest);
            message.setCode("T").setMessage("成功");
            message.setData(pageResult);
        } catch (Exception e){
            logger.error("获取问卷模板列表出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }
    /***
     * @Description: 
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @ApiOperation(value = "获取问卷模板信息",notes = "不分页")
    @PostMapping("/getWjmb")
    public ResponseEntity<Object> getWjmb(@RequestBody WjmbDO wjmbDOP) throws IOException {

        ResponseMessage message = new ResponseMessage();
        try {
            List<WjmbDO> wjmbDOs = wjmbService.getWjmbDO(wjmbDOP);
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbDOs);
        } catch (Exception e){
            logger.error("获取问卷模板信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**
     * 新增1,更新2,删除3，启用停用4问卷模板
     * @param wjmbDOP
     * @return
     */
    @ApiOperation(value = "问卷模板新增,更新,删除，启用停用",notes = "注意数据的操作类型czlx")
    @PostMapping("/saveWjmb")
    public ResponseEntity<Object> saveWjmb(@RequestBody WjmbDO wjmbDOP){
        ResponseMessage message = new ResponseMessage();
        try {
            wjmbService.saveWjmb(wjmbDOP);
            message.setCode("T").setMessage("成功").setData(wjmbDOP);
        } catch (Exception e){
            logger.error("问卷模板新增/更新/删除出错，错误信息：",e);
            message.setCode("F").setMessage(""+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**********************************************模板分类**********************************************/
    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @ApiOperation(value = "获取问卷模板分类信息",notes = "不分页")
    @PostMapping("/getWjmbfl")
    public ResponseEntity<Object> getWjmbfl(@RequestBody WjmbDO wjmbDOP) throws IOException {

        ResponseMessage message = new ResponseMessage();
        int mbid = wjmbDOP.getId();
        try {
            List<WjmbflDO> wjmbflDOs = wjmbflService.getWjmbflDO(mbid);
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbflDOs);
        } catch (Exception e){
            logger.error("获取问卷模板分类信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @ApiOperation(value = "获取一个自动生成的模板分类顺序",notes = "获取一个自动生成的模板分类顺序")
    @PostMapping("/getWjmbflsx")
    public ResponseEntity<Object> getWjmbflsx(@RequestBody WjmbDO wjmbDOP){
        ResponseMessage message = new ResponseMessage();
        int mbid = wjmbDOP.getId();
        try {
            WjmbflDO wjmbflDO = wjmbflService.getWjmbflsx(mbid);
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbflDO);
        } catch (Exception e){
            logger.error("获取问卷模板分类信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**
     * 新增1,更新2,删除3 问卷模板分类
     * @param wjmbflDOP
     * @return
     */
    @ApiOperation(value = "单个问卷模板分类新增,更新,删除",notes = "注意数据的操作类型czlx")
    @PostMapping("/saveWjmbfl")
    public ResponseEntity<Object>  saveWjmbfl(@RequestBody WjmbflDO wjmbflDOP){
        ResponseMessage message = new ResponseMessage();
        try {
            wjmbflService.saveWjmbfl(wjmbflDOP);
            message.setCode("T").setMessage("成功").setData(wjmbflDOP);
        } catch (Exception e){
            logger.error("问卷模板分类新增/更新/删除出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**********************************************模板题目关联**********************************************/
    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @ApiOperation(value = "获取分类下题目关联信息",notes = "不分页")
    @PostMapping("/getFltmgl")
    public ResponseEntity<Object> getFltmgl(@RequestBody WjmbflDO wjmbflDOP) throws IOException {
        ResponseMessage message = new ResponseMessage();
        int mbflid = wjmbflDOP.getId();
        try {
            List<FltmglDO> fltmglDOs = fltmglService.getFltmglDO(mbflid);
            message.setCode("T")
                    .setMessage("success")
                    .setData(fltmglDOs);
        } catch (Exception e){
            logger.error("获取分类题目关联信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**
     * 新增1,更新2,删除3 分类题目关联
     * 只有更新和删除 新增有单独的批量
     * @param params
     * @return
     */
    @ApiOperation(value = "单个分类题目关联新增,更新,删除",notes = "注意数据的操作类型czlx")
    @PostMapping("/saveFltmgl")
    public ResponseEntity<Object> saveFltmgl(@RequestBody Map<String,Object> params){
        FltmglDO fltmglDOP = JSONObject.parseObject(JSONObject.toJSONString(params), FltmglDO.class);
        int mbid =params.get("mbid")==null?0: (Integer)params.get("mbid");
        ResponseMessage message = new ResponseMessage();
        try {
            fltmglService.saveFltmgl(mbid,fltmglDOP);
            message.setCode("T").setMessage("成功").setData(fltmglDOP);
        } catch (Exception e){
            logger.error("分类题目关联新增/更新/删除出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /**
     * @param params
     * @return
     */
    @ApiOperation(position = 2,value = "分页获取问卷模板所有题目信息",notes = "分页")
    @PostMapping("/getWjmbtmPageList")
    public ResponseMessage getWjmbtmPageList(@RequestBody Map<String,Object> params){
        int mbid =params.get("mbid")==null?0: (Integer)params.get("mbid");
        String tmmc =params.get("tmmc")==null?"": (String)params.get("tmmc");
        int pageNum =params.get("pageNum")==null?PAGE_NUM: (Integer)params.get("pageNum");
        int pageSize =params.get("pageSize")==null?PAGE_SIZE:(Integer)params.get("pageSize");
        ResponseMessage message = new ResponseMessage();
        try {
            PageRequest pageRequest=new PageRequest();
            pageRequest.setPageNum(pageNum);
            pageRequest.setPageSize(pageSize);
            PageResult pageResult=fltmglService.getFltmglDO(mbid,tmmc,pageRequest);
            message.setCode("T").setMessage("成功");
            message.setData(pageResult);
        } catch (Exception e){
            logger.error("分页获取问卷模板所有题目信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }
    @ApiOperation(value = "查询题目列表")
    @PostMapping("/queryTmPageList")
    public ResponseMessage queryTmPageList(@RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
            PageResult pageResult = fltmglService.queryAllTm(params);
            message.setCode("T").setMessage("成功");
            message.setData(pageResult);
        } catch (Exception e){
            logger.error("查询题目列表出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    /**
     * 批量新增1,更新2,删除3 分类题目关联
     * 只有批量新增 其他预留
     * @param params
     * @return
     */
    @ApiOperation(value = "批量分类题目关联新增,更新,删除",notes = "注意数据的操作类型czlx")
    @PostMapping("/saveFltmgls")
    public ResponseEntity<Object> saveFltmgls(@RequestBody Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        List<FltmglDO> fltmglDOPs = JSONObject.parseObject(JSONObject.toJSONString(params.get("fltmgls")), List.class);
        int mbid =params.get("mbid")==null?0: (Integer)params.get("mbid");
        int mbflid =params.get("mbflid")==null?0: (Integer)params.get("mbflid");
        try {
            fltmglService.saveFltmgls(mbid,mbflid,fltmglDOPs);
            message.setCode("T").setMessage("成功").setData(fltmglDOPs);
        } catch (Exception e){
            logger.error("分类题目关联新增/更新/删除出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    /**********************************************选项分设置**********************************************/
    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    @ApiOperation(value = "获取选项分设置信息",notes = "不分页")
    @PostMapping("/getXxfsz")
    public ResponseEntity<Object> getXxfsz(@RequestBody Map<String, Object> params) throws IOException {
        ResponseMessage message = new ResponseMessage();
        FltmglDO fltmglDO = JSONObject.parseObject(JSONObject.toJSONString(params), FltmglDO.class);
        int fltmglid = fltmglDO.getId();
        try {
            List<XxfszDO> xxfszDOs = xxfszService.getXxfszDO(fltmglid);
            message.setCode("T")
                    .setMessage("success")
                    .setData(xxfszDOs);
        } catch (Exception e){
            logger.error("获取分选项分设置信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分选项分设置先删除后新增",notes = "先删除后新增")
    @PostMapping("/saveXxfsz")
    public  ResponseEntity<Object> saveXxfsz( @RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        List<XxfszDO> xxfszDOPs = JSONObject.parseObject(JSONObject.toJSONString(params.get("xxfszs")), List.class);
        int fltmglid = (Integer)params.get("fltmglid");
        try {
            xxfszService.saveXxfszs(fltmglid,xxfszDOPs);
            message.setCode("T").setMessage("成功").setData(xxfszDOPs);
        } catch (Exception e){
            logger.error("分选项分设置新增/更新/删除出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "获取问卷模板计算因子",notes = "不分页")
    @PostMapping("/getWjmbjsyz")
    public ResponseEntity<Object> getWjmbjsyz(@RequestBody  Map<String,Object> params) throws IOException {
        ResponseMessage message = new ResponseMessage();
        int id = (Integer)params.get("id");
        try {
            List<WjmbflDO> wjmbflDOs = wjmbflService.getWjmbjsyz(id);
            message.setCode("T")
                    .setMessage("success")
                    .setData(wjmbflDOs);
        } catch (Exception e){
            logger.error("获取问卷模板计算因子出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    /*********************************************评分设置**********************************************/
    /***
     * @Description:
     * @Param:params
     * @return:
     * @Author: huchengwei
     * @Date: 2021/4/5
     */
    @ApiOperation(value = "获取题目评分设置信息",notes = "不分页")
    @PostMapping("/getPfgs")
    public ResponseEntity<Object> getPfgs(@RequestBody Map<String, Object> params) throws IOException {
        ResponseMessage message = new ResponseMessage();
        FltmglDO fltmglDO = JSONObject.parseObject(JSONObject.toJSONString(params), FltmglDO.class);
        int fltmglid = fltmglDO.getId();
        try {
            List<PfgsDO> pfgsDOs = pfgsService.getPfgsDO(fltmglid);
            message.setCode("T")
                    .setMessage("success")
                    .setData(pfgsDOs);
        } catch (Exception e){
            logger.error("获取评分设置信息出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * 新增/更新 评分设置  先删除 后新增
     * @param params
     * @return
     */
    @ApiOperation(value = "评分设置先删除后新增",notes = "先删除后新增")
    @PostMapping("/savePfgs")
    public  ResponseEntity<Object> savePfgs( @RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        List<PfgsDO> pfgsDOPs = JSONObject.parseObject(JSONObject.toJSONString(params.get("pfgs")), List.class);
        int fltmglid = (Integer)params.get("fltmglid");
        try {
            pfgsService.savePfgss(fltmglid,pfgsDOPs);
            message.setCode("T").setMessage("成功").setData(pfgsDOPs);
        } catch (Exception e){
            logger.error("评分设置新增/更新/删除出错，错误信息：",e);
            message.setCode("F").setMessage("失败:"+e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
