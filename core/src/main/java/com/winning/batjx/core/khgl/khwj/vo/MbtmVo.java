package com.winning.batjx.core.khgl.khwj.vo;

import java.util.List;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/19
 * @time 10:07
 * @description 模板题目
 */

public class MbtmVo {


    private Integer tmid;

    private String tmbm;

    private String tmbt;

    private String tmlx;

    private String sfdtbz; // 是否答题备注

    private String tmfbt;

    private String tmbz;

    private String tmda; //

    private String tmdaly;

    private Integer tmsx; // 题目顺序

    private String tktsfwdf;// 填空题是否为得分

    private List<TmxxVo> tmxxs; // 题目选项

    private List<TkxxVo> tkxxs; // 填空题

    private Integer selectId;

    private Integer[] selectIds;

    private Integer dtjgid; // 答题结果id

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

    public String getTktsfwdf() {
        return tktsfwdf;
    }

    public void setTktsfwdf(String tktsfwdf) {
        this.tktsfwdf = tktsfwdf;
    }

    public String getTmda() {
        return tmda;
    }

    public void setTmda(String tmda) {
        this.tmda = tmda;
    }

    public List<TkxxVo> getTkxxs() {
        return tkxxs;
    }

    public void setTkxxs(List<TkxxVo> tkxxs) {
        this.tkxxs = tkxxs;
    }

    public String getTmbz() {
        return tmbz;
    }

    public void setTmbz(String tmbz) {
        this.tmbz = tmbz;
    }

    public Integer getTmsx() {
        return tmsx;
    }

    public void setTmsx(Integer tmsx) {
        this.tmsx = tmsx;
    }

    public String getTmfbt() {
        return tmfbt;
    }

    public void setTmfbt(String tmfbt) {
        this.tmfbt = tmfbt;
    }

    public Integer[] getSelectIds() {
        return selectIds;
    }

    public void setSelectIds(Integer[] selectIds) {
        this.selectIds = selectIds;
    }

    public Integer getSelectId() {
        return selectId;
    }

    public void setSelectId(Integer selectId) {
        this.selectId = selectId;
    }

    public String getTmbm() {
        return tmbm;
    }

    public void setTmbm(String tmbm) {
        this.tmbm = tmbm;
    }

    public String getSfdtbz() {
        return sfdtbz;
    }

    public void setSfdtbz(String sfdtbz) {
        this.sfdtbz = sfdtbz;
    }

    public Integer getTmid() {
        return tmid;
    }

    public void setTmid(Integer tmid) {
        this.tmid = tmid;
    }

    public String getTmbt() {
        return tmbt;
    }

    public void setTmbt(String tmbt) {
        this.tmbt = tmbt;
    }

    public String getTmlx() {
        return tmlx;
    }

    public void setTmlx(String tmlx) {
        this.tmlx = tmlx;
    }

    public List<TmxxVo> getTmxxs() {
        return tmxxs;
    }

    public void setTmxxs(List<TmxxVo> tmxxs) {
        this.tmxxs = tmxxs;
    }
}
