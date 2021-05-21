package com.winning.batjx.core.khgl.khwj.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/3/2
 */
public class WjmbflDO implements java.io.Serializable , Cloneable {

    @ApiModelProperty(notes = "主键id")
    private int id;

    @ApiModelProperty(notes = "模板id")
    private int mbid;

    @ApiModelProperty(notes = "模板分类名称")
    private String mbflmc;

    @ApiModelProperty(notes = "模板分类顺序")
    private int mbflsx;

    @ApiModelProperty(notes = "是否有附件(0无1有)")
    private String sfyfj;

    @ApiModelProperty(notes = "操作类型 1新增 2更新 3 删除")
    private String czlx;

    private List<FltmglDO> fltmglDOs = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMbid() {
        return mbid;
    }

    public void setMbid(int mbid) {
        this.mbid = mbid;
    }

    public String getMbflmc() {
        return mbflmc;
    }

    public void setMbflmc(String mbflmc) {
        this.mbflmc = mbflmc;
    }

    public int getMbflsx() {
        return mbflsx;
    }

    public void setMbflsx(int mbflsx) {
        this.mbflsx = mbflsx;
    }

    public String getSfyfj() {
        return sfyfj;
    }

    public void setSfyfj(String sfyfj) {
        this.sfyfj = sfyfj;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public List<FltmglDO> getFltmglDOs() {
        return new ArrayList<>(fltmglDOs);
    }

    public void setFltmglDOs(List<FltmglDO> fltmglDOs) {
        this.fltmglDOs.clear();
        this.fltmglDOs.addAll(fltmglDOs);
    }
    @Override
    public WjmbflDO clone() throws CloneNotSupportedException {
        return (WjmbflDO) super.clone();
    }
}
