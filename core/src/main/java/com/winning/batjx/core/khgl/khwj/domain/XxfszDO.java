package com.winning.batjx.core.khgl.khwj.domain;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/3/2
 */
public class XxfszDO implements java.io.Serializable, Cloneable {
    @ApiModelProperty(notes = "主键id")
    private int id;

    @ApiModelProperty(notes = "分类题目关联id")
    private int fltmglid;

    @ApiModelProperty(notes = "题目选项id")
    private int tmxxid;

    @ApiModelProperty(notes = "选项分值")
    private BigDecimal xxfz;

    /******************扩展题目基础属性***********************/

    @ApiModelProperty(notes = "选项内容")
    private String xxnr;

    @ApiModelProperty(notes = "选项顺序")
    private int xxsx;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFltmglid() {
        return fltmglid;
    }

    public void setFltmglid(int fltmglid) {
        this.fltmglid = fltmglid;
    }

    public int getTmxxid() {
        return tmxxid;
    }

    public void setTmxxid(int tmxxid) {
        this.tmxxid = tmxxid;
    }

    public BigDecimal getXxfz() {
        return xxfz;
    }

    public void setXxfz(BigDecimal xxfz) {
        this.xxfz = xxfz;
    }

    public String getXxnr() {
        return xxnr;
    }

    public void setXxnr(String xxnr) {
        this.xxnr = xxnr;
    }

    public int getXxsx() {
        return xxsx;
    }

    public void setXxsx(int xxsx) {
        this.xxsx = xxsx;
    }

    @Override
    public XxfszDO clone() throws CloneNotSupportedException {
        return (XxfszDO) super.clone();
    }
}
