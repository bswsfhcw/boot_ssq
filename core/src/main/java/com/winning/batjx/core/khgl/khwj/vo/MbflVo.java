package com.winning.batjx.core.khgl.khwj.vo;

import java.util.List;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/19
 * @time 10:06
 * @description 问卷分类
 */

public class MbflVo {

    private Integer flid;

    private Integer mbid;

    private String sfyfj; // 是否有附件

    private Integer flsx;

    private String flmc;

    private List<MbtmVo> tms;

    private List<FileVo> files;

    public List<FileVo> getFiles() {
        return files;
    }

    public void setFiles(List<FileVo> files) {
        this.files = files;
    }

    public List<MbtmVo> getTms() {
        return tms;
    }

    public void setTms(List<MbtmVo> tms) {
        this.tms = tms;
    }

    public Integer getFlid() {
        return flid;
    }

    public void setFlid(Integer flid) {
        this.flid = flid;
    }

    public Integer getMbid() {
        return mbid;
    }

    public void setMbid(Integer mbid) {
        this.mbid = mbid;
    }

    public String getSfyfj() {
        return sfyfj;
    }

    public void setSfyfj(String sfyfj) {
        this.sfyfj = sfyfj;
    }

    public Integer getFlsx() {
        return flsx;
    }

    public void setFlsx(Integer flsx) {
        this.flsx = flsx;
    }

    public String getFlmc() {
        return flmc;
    }

    public void setFlmc(String flmc) {
        this.flmc = flmc;
    }
}
