package com.winning.batjx.core.khgl.khwj.domain;

import java.math.BigDecimal;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/11
 * @time 9:05
 * @description 考核问卷结果 qyjx_khwj_jg
 */

public class WjjgDo {

    private Integer id;

    private String khnf;

    private String khpl;

    private String khqj;

    private Integer faid;

    private String khdxbm;

    private String khdxmc;

    private String zbbm;

    private String zbmc;

    private BigDecimal khdf;

    private String cjr;

    private String gxr;

    private String bz;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getGxr() {
        return gxr;
    }

    public void setGxr(String gxr) {
        this.gxr = gxr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKhnf() {
        return khnf;
    }

    public void setKhnf(String khnf) {
        this.khnf = khnf;
    }

    public String getKhpl() {
        return khpl;
    }

    public void setKhpl(String khpl) {
        this.khpl = khpl;
    }

    public String getKhqj() {
        return khqj;
    }

    public void setKhqj(String khqj) {
        this.khqj = khqj;
    }

    public Integer getFaid() {
        return faid;
    }

    public void setFaid(Integer faid) {
        this.faid = faid;
    }

    public String getKhdxbm() {
        return khdxbm;
    }

    public void setKhdxbm(String khdxbm) {
        this.khdxbm = khdxbm;
    }

    public String getKhdxmc() {
        return khdxmc;
    }

    public void setKhdxmc(String khdxmc) {
        this.khdxmc = khdxmc;
    }

    public String getZbbm() {
        return zbbm;
    }

    public void setZbbm(String zbbm) {
        this.zbbm = zbbm;
    }

    public String getZbmc() {
        return zbmc;
    }

    public void setZbmc(String zbmc) {
        this.zbmc = zbmc;
    }

    public BigDecimal getKhdf() {
        return khdf;
    }

    public void setKhdf(BigDecimal khdf) {
        this.khdf = khdf;
    }
}
