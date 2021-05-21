package com.winning.batjx.core.khgl.khwj.service;

import java.util.Map;

/**
 * Created with 基础框架项目
 * USER: 项鸿铭
 * DATE: 2017/2/23.
 * TIME: 12:43.
 * winning_frame
 */
public interface CommonService {

    /**
     * 根据参数编码获取获取参数值
     *
     * @param csbh 参数编码
     * @param jgbm 机构编码
     * @return
     * @throws Exception
     */
     String getCssz(String csbh, String jgbm) throws Exception;






    /***
     * @Description: 判断当前登录人员所在机构是指定方案的上级还是下级
     * @Param:  String faid
     * @return: boolean
     * @Author: yyh@winning.com.cn
     * @Date: 2019/4/25 1:19 PM
     */
     boolean isSuperior(Map map)  throws Exception;


}
