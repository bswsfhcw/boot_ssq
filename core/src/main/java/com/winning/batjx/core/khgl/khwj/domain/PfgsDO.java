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
public class PfgsDO implements java.io.Serializable , Cloneable{

    @ApiModelProperty(notes = "主键id")
    private int id;

    @ApiModelProperty(notes = "分类题目关联id")
    private int fltmglid;

    @ApiModelProperty(notes = "算分公式名称")
    private String sfgsmc;

    @ApiModelProperty(notes = "算分公式编码")
    private String sfgsbm;

    @ApiModelProperty(notes = "序号")
    private int xh;

    @ApiModelProperty(notes = "分段开始值")
    private BigDecimal fdksz;

    @ApiModelProperty(example = "GTEQ",notes = "分段开始计算符,GT:大于，GTEQ：大于等于，LT：小于，LTEQ：小于等于，EQ：等于")
    private String fdksjsf;

    @ApiModelProperty(notes = "分段结束值")
    private BigDecimal fdjsz;

    @ApiModelProperty(example = "LTEQ",notes = "分段结束计算符")
    private String fdjsjsf;

    @ApiModelProperty(notes = "得分值")
    private BigDecimal dfz;

    @ApiModelProperty(notes = "操作类型 1新增 2更新 3 删除")
    private String czlx;

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

    public String getSfgsmc() {
        return sfgsmc;
    }

    public void setSfgsmc(String sfgsmc) {
        this.sfgsmc = sfgsmc;
    }

    public String getSfgsbm() {
        return sfgsbm;
    }

    public void setSfgsbm(String sfgsbm) {
        this.sfgsbm = sfgsbm;
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public BigDecimal getFdksz() {
        return fdksz;
    }

    public void setFdksz(BigDecimal fdksz) {
        this.fdksz = fdksz;
    }

    public String getFdksjsf() {
        return fdksjsf;
    }

    public void setFdksjsf(String fdksjsf) {
        this.fdksjsf = fdksjsf;
    }

    public BigDecimal getFdjsz() {
        return fdjsz;
    }

    public void setFdjsz(BigDecimal fdjsz) {
        this.fdjsz = fdjsz;
    }

    public String getFdjsjsf() {
        return fdjsjsf;
    }

    public void setFdjsjsf(String fdjsjsf) {
        this.fdjsjsf = fdjsjsf;
    }

    public BigDecimal getDfz() {
        return dfz;
    }

    public void setDfz(BigDecimal dfz) {
        this.dfz = dfz;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    @Override
    public PfgsDO clone() throws CloneNotSupportedException {
        return (PfgsDO) super.clone();
    }
}
