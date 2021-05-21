package com.winning.batjx.core.khgl.khwj.vo;

import java.util.List;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/25
 * @time 9:42
 * @description 问卷模板
 */

public class WjmbVo {

    private Integer mbid;

    private String mbbh;

    private String mbmc;

    private String mbfbt;

    private String mblx;

    private String mbxsfs;

    private String mbfj;

    private Integer cols;

    private List<FileVo> files;

    private List<MbflVo> fls;

    public List<FileVo> getFiles() {
        return files;
    }

    public void setFiles(List<FileVo> files) {
        this.files = files;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public List<MbflVo> getFls() {
        return fls;
    }

    public void setFls(List<MbflVo> fls) {
        this.fls = fls;
    }

    public Integer getMbid() {
        return mbid;
    }

    public void setMbid(Integer mbid) {
        this.mbid = mbid;
    }

    public String getMbbh() {
        return mbbh;
    }

    public void setMbbh(String mbbh) {
        this.mbbh = mbbh;
    }

    public String getMbmc() {
        return mbmc;
    }

    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
    }

    public String getMbfbt() {
        return mbfbt;
    }

    public void setMbfbt(String mbfbt) {
        this.mbfbt = mbfbt;
    }

    public String getMblx() {
        return mblx;
    }

    public void setMblx(String mblx) {
        this.mblx = mblx;
    }

    public String getMbxsfs() {
        return mbxsfs;
    }

    public void setMbxsfs(String mbxsfs) {
        this.mbxsfs = mbxsfs;
    }

    public String getMbfj() {
        return mbfj;
    }

    public void setMbfj(String mbfj) {
        this.mbfj = mbfj;
    }
}
