package com.winning.batjx.core.khgl.khwj.domain;

import java.math.BigDecimal;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/11
 * @time 17:11
 * @description 问卷题目和答案具体信息 页面展示
 */

public class WjxxPageDo {

    private Integer dtjgid;

    private String mbbm;

    private String mbmc;

    private Integer flid;

    private String flmc;

    private Integer flsx;

    private String tmbm;

    private String tmmc;

    private String tmfbt;

    private Integer tmsx;

    private String tmlx;

    private String tmda;

    private String tmbz;

    private String xxnr;// 选项内容

    private Integer xxsx;// 选项顺序

    private String tmdaly;// 题目答案来源

    private String tmdagsbm;// 题目答案公式编码

    private String tmdagsmc; // 题目答案公式名称

    private String tkzsfwdf;// 填空值是否为得分

    private String tmsffs;// 题目算分方式(1选项得分2选项得分累计3题目分)

    private String tmsffffl;//算分方法分类(1自定义公式2固定分段)

    private String sfgx;// 是否勾选

    private BigDecimal xxfz; // 选项分值

    public Integer getDtjgid() {
        return dtjgid;
    }

    public void setDtjgid(Integer dtjgid) {
        this.dtjgid = dtjgid;
    }

    public String getTmdaly() {
        return tmdaly;
    }

    public void setTmdaly(String tmdaly) {
        this.tmdaly = tmdaly;
    }

    public String getTmdagsbm() {
        return tmdagsbm;
    }

    public void setTmdagsbm(String tmdagsbm) {
        this.tmdagsbm = tmdagsbm;
    }

    public String getTmdagsmc() {
        return tmdagsmc;
    }

    public void setTmdagsmc(String tmdagsmc) {
        this.tmdagsmc = tmdagsmc;
    }

    public String getTkzsfwdf() {
        return tkzsfwdf;
    }

    public void setTkzsfwdf(String tkzsfwdf) {
        this.tkzsfwdf = tkzsfwdf;
    }

    public String getTmsffs() {
        return tmsffs;
    }

    public void setTmsffs(String tmsffs) {
        this.tmsffs = tmsffs;
    }

    public String getTmsffffl() {
        return tmsffffl;
    }

    public void setTmsffffl(String tmsffffl) {
        this.tmsffffl = tmsffffl;
    }

    public String getSfgx() {
        return sfgx;
    }

    public void setSfgx(String sfgx) {
        this.sfgx = sfgx;
    }

    public BigDecimal getXxfz() {
        return xxfz;
    }

    public void setXxfz(BigDecimal xxfz) {
        this.xxfz = xxfz;
    }

    public String getMbbm() {
        return mbbm;
    }

    public void setMbbm(String mbbm) {
        this.mbbm = mbbm;
    }

    public String getMbmc() {
        return mbmc;
    }

    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
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

    public void setTmsx(Integer tmsx) {
        this.tmsx = tmsx;
    }

    public String getTmlx() {
        return tmlx;
    }

    public void setTmlx(String tmlx) {
        this.tmlx = tmlx;
    }

    public String getTmda() {
        return tmda;
    }

    public void setTmda(String tmda) {
        this.tmda = tmda;
    }

    public String getTmbz() {
        return tmbz;
    }

    public void setTmbz(String tmbz) {
        this.tmbz = tmbz;
    }

    public String getXxnr() {
        return xxnr;
    }

    public void setXxnr(String xxnr) {
        this.xxnr = xxnr;
    }

    public Integer getXxsx() {
        return xxsx;
    }

    public void setXxsx(Integer xxsx) {
        this.xxsx = xxsx;
    }
}
