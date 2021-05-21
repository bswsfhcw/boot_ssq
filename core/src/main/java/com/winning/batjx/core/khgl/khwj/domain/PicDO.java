package com.winning.batjx.core.khgl.khwj.domain;

import lombok.Data;

import java.io.Serializable;

/***
 * @Description: 存图片
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/23
 */
@Data
public class PicDO implements Serializable {
    /**
     * 表主键
     */
    private Integer id;

    /**
     * 模块Id
     */
    private Integer mkid;

    /**
     *
     */
    private String mkbm;

    /**
     *
     */
    private String yswjmc;

    /**
     *
     */
    private String bz;

    /**
     *
     */
    private String ccwjmc;

    /**
     *
     */
    private String wjlx;
    private String cclj;
    private String scr;

    private String jgbm;
    private String tpzm;

    /**
     * 创建时间
     */
//    private Timestamp scsj;

    /**
     * 启用状态
     */
    private Integer jlzt;

}
