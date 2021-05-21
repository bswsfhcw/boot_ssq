package com.winning.batjx.core.khgl.khwj.domain;

import java.util.List;

/**
 * Created by thinkpad on 2021/3/2.
 */
public class TmDO implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 题目编码
     */
    private String tmbm;

    /**
     * 题目名称
     */
    private String tmmc;

    /**
     * 题目类型(1单选2多选3填空)
     */
    private String tmlx;

    /**
     * 题目类型名称(1单选2多选3填空)
     */
    private String tmlxmc;


    /**
     * 题目副标题
     */
    private String tmfbt;

    /**
     * 是否答题备注(0否1是)
     */
    private String sfdtbz;

    /**
     * 是否答题备注汉字(0否1是)
     */
    private String sfdtbzmc;


    /**
     * 创建人
     */
    private String cjr;

    /**
     * 状态(0无效1有效)
     */
    private String zt;

    private List<TmxxDO> tmxxDOList;





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

    public String getTmlx() {
        return tmlx;
    }

    public void setTmlx(String tmlx) {
        this.tmlx = tmlx;
    }

    public String getTmfbt() {
        return tmfbt;
    }

    public void setTmfbt(String tmfbt) {
        this.tmfbt = tmfbt;
    }

    public String getSfdtbz() {
        return sfdtbz;
    }

    public void setSfdtbz(String sfdtbz) {
        this.sfdtbz = sfdtbz;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTmlxmc() {
        return tmlxmc;
    }

    public void setTmlxmc(String tmlxmc) {
        this.tmlxmc = tmlxmc;
    }

    public String getSfdtbzmc() {
        return sfdtbzmc;
    }

    public void setSfdtbzmc(String sfdtbzmc) {
        this.sfdtbzmc = sfdtbzmc;
    }

    public List<TmxxDO> getTmxxDOList() {
        return tmxxDOList;
    }

    public void setTmxxDOList(List<TmxxDO> tmxxDOList) {
        this.tmxxDOList = tmxxDOList;
    }
}
