package com.winning.batjx.core.khgl.khwj.controller;

import com.winning.batjx.core.common.BaseController;
import com.winning.batjx.core.common.constant.AssessmentConstant;
import com.winning.batjx.core.khgl.khwj.domain.MbzbdzDO;
import com.winning.batjx.core.khgl.khwj.domain.WjmbDO;
import com.winning.batjx.core.khgl.khwj.service.MbdzService;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: boot-batjx
 * @description 模板对照后台
 * @author: yyh
 * @create: 2021-03-02 11:07
 **/

@RestController
@RequestMapping("/mbdz")
@Api(description = "区域绩效机构管理", tags = "003-问卷考核-模板对照")
public class MbdzController extends BaseController {

    @Autowired
    private MbdzService mbdzService;


    @ApiOperation(value = "查询指标对照列表")
    @PostMapping("/queryPageList")
    public ResponseMessage queryPageList(@RequestBody Map<String, Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
            PageResult pageResult=mbdzService.getZbdzList(params);
            message.setCode("T").setMessage("成功");
            message.setData(pageResult);
        } catch (Exception e){
            logger.error("查询指标对照列表出错，错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }


    /**
     * 保存
     */
    @ApiOperation(value = "保存指标对应模板编号")
    @PostMapping("/save")
    public ResponseMessage save(@RequestBody MbzbdzDO mbzbdz){

        ResponseMessage message = new ResponseMessage();
        try {
            mbdzService.saveMbdz(mbzbdz);
            message.setCode("T").setMessage("保存模板对照成功！");
        }catch (Exception e){
            logger.error("保存模板对照出错，错误信息：",e);
            message.setCode("F").setMessage("保存模板对照失败！");
        }
        return message;
    }



    @ApiOperation(value = "问卷预览")
    @PostMapping("/showDetail")
    public ResponseMessage showDetail(@RequestBody MbzbdzDO mbzbdz){

        ResponseMessage message = new ResponseMessage();
        try {
            WjmbDO wjmbDO=mbdzService.queryWjmbBymbbh(mbzbdz.getWjmbbh());
            if(wjmbDO==null){
                message.setCode("F").setMessage("预览失败，当前模板不存在!");
                return message;
            }
            if(!String.valueOf(AssessmentConstant.TemplateStatusEnum.START.getCode()).equals(wjmbDO.getZt())){
                message.setCode("F").setMessage("预览失败，当前模板已禁用!");
                return message;
            }
            message.setCode("T").setMessage("成功").setData(wjmbDO);
        }catch (Exception e){
            logger.error("系统异常，错误信息：",e);
            message.setCode("F").setMessage("预览失败!");
        }
        return message;
    }

}
