package com.winning.batjx.core.khgl.khwj.vo;

import java.util.List;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/29
 * @time 13:31
 * @description
 */

public class TkxxVo {

    private Integer xxid;

    private Integer tmsx;

    private String tmdaly; // 题目答案来源(0无1默认填写2关联计算)

    private String tmdagsmc; //

    private String tmdagsbm;//

    private String tkzsfwdf;//填空值是否为得分(0否1是)

    private String tmsffs;// 题目算分方式(1选项得分2选项得分累计3题目分)

    private String tmsffffl;// 算分方法分类(1自定义公式2固定分段)

    private String tmsfbdsms;//题目算分表达式描述

    private String tmda; // 题目答案

    private String tmbz;

    private List<TktgsxxVo> gsxxs;

    public List<TktgsxxVo> getGsxxs() {
        return gsxxs;
    }

    public void setGsxxs(List<TktgsxxVo> gsxxs) {
        this.gsxxs = gsxxs;
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

    public Integer getXxid() {
        return xxid;
    }

    public void setXxid(Integer xxid) {
        this.xxid = xxid;
    }

    public Integer getTmsx() {
        return tmsx;
    }

    public void setTmsx(Integer tmsx) {
        this.tmsx = tmsx;
    }

    public String getTmdaly() {
        return tmdaly;
    }

    public void setTmdaly(String tmdaly) {
        this.tmdaly = tmdaly;
    }

    public String getTmdagsmc() {
        return tmdagsmc;
    }

    public void setTmdagsmc(String tmdagsmc) {
        this.tmdagsmc = tmdagsmc;
    }

    public String getTmdagsbm() {
        return tmdagsbm;
    }

    public void setTmdagsbm(String tmdagsbm) {
        this.tmdagsbm = tmdagsbm;
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

    public String getTmsfbdsms() {
        return tmsfbdsms;
    }

    public void setTmsfbdsms(String tmsfbdsms) {
        this.tmsfbdsms = tmsfbdsms;
    }
}
