package com.winning.batjx.core.khgl.khwj.domain;

import java.math.BigDecimal;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/11
 * @time 9:12
 * @description 答题结果表
 */

public class DtjgDo {

    private Integer id;

    private Integer mbjgid;

    private Integer flid;

    private String flmc;

    private Integer flsx; // 分类顺序

    private String tmlx; // 题目类型

    private String tmbm; //题目编码

    private String tmmc;

    private String tmfbt;

    private Integer tmsx; // 题目顺序

    private String tmbz; //备注

    private String tmda;// 答案

    private BigDecimal tmdf; // 题目得分


    public void setTmsx(Integer tmsx) {
        this.tmsx = tmsx;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMbjgid() {
        return mbjgid;
    }

    public void setMbjgid(Integer mbjgid) {
        this.mbjgid = mbjgid;
    }

    public Integer getFlid() {
        return flid;
    }

    public void setFlid(Integer flid) {
        this.flid = flid;
    }

    public String getFlmc() {
        return flmc;
    }

    public void setFlmc(String flmc) {
        this.flmc = flmc;
    }

    public Integer getFlsx() {
        return flsx;
    }

    public void setFlsx(Integer flsx) {
        this.flsx = flsx;
    }

    public String getTmlx() {
        return tmlx;
    }

    public void setTmlx(String tmlx) {
        this.tmlx = tmlx;
    }

    public String getTmbm() {
        return tmbm;
    }

    public void setTmbm(String tmbm) {
        this.tmbm = tmbm;
    }

    public String getTmmc() {
        return tmmc;
    }

    public void setTmmc(String tmmc) {
        this.tmmc = tmmc;
    }

    public String getTmfbt() {
        return tmfbt;
    }

    public void setTmfbt(String tmfbt) {
        this.tmfbt = tmfbt;
    }

    public Integer getTmsx() {
        return tmsx;
    }

    public void setTmfx(Integer tmsx) {
        this.tmsx = tmsx;
    }

    public String getTmbz() {
        return tmbz;
    }

    public void setTmbz(String tmbz) {
        this.tmbz = tmbz;
    }

    public String getTmda() {
        return tmda;
    }

    public void setTmda(String tmda) {
        this.tmda = tmda;
    }

    public BigDecimal getTmdf() {
        return tmdf;
    }

    public void setTmdf(BigDecimal tmdf) {
        this.tmdf = tmdf;
    }
}
