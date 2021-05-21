package com.winning.batjx.core.khgl.khwj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/3/2
 */
public class WjmbDO implements java.io.Serializable, Cloneable {

    private int id;
    /**
     * 模板编号
     */
    @ApiModelProperty(example = "mbyyyymmdd001")
    private String mbbh;
    /**
     * 模板名称
     */
    @ApiModelProperty(example = "XX模板")
    private String mbmc;
    /**
     * 模板副标题
     */
    @ApiModelProperty(example = "XX副标题")
    private String mbfbt;

    /**
     * 模板显示方式(1单行显示2一行两列)
     */
    @ApiModelProperty(notes = "模板显示方式(1单行显示2一行两列)")
    private String mbxsfs;
    /**
     * 模板类型(1机构2个人)
     */
    @ApiModelProperty(notes="模板类型(1机构2个人)")
    private String mblx;
    /**
     * 模板附件(0无1有)
     */
    @ApiModelProperty(notes="模板附件(0无1有)")
    private String mbfj;
    /**
     * 创建人
     */
    @ApiModelProperty(example = "")
    private String cjr;
    /**
     * 创建时间
     */
    @ApiModelProperty(required = true,example = "2018-10-01 12:18:48")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date cjsj;
    /**
     * 更新人
     */
    private String gxr;
    /**
     * 更新时间
     */
    @ApiModelProperty(required = true,example = "2018-10-01 12:18:48")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date gxsj;
    /**
     * 状态(0停用1启用）
     */
    @ApiModelProperty(notes="状态(0停用1启用）")
    private String zt;
    /**
    * 操作类型 1新增 2更新 3 删除 4启用禁用
    */
    @ApiModelProperty(notes="操作类型 1新增 2更新 3 删除 4启用禁用")
    private String czlx;


    private List<WjmbflDO> wjmbflDOs = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMbxsfs() {
        return mbxsfs;
    }

    public void setMbxsfs(String mbxsfs) {
        this.mbxsfs = mbxsfs;
    }

    public String getMblx() {
        return mblx;
    }

    public void setMblx(String mblx) {
        this.mblx = mblx;
    }

    public String getMbfj() {
        return mbfj;
    }

    public void setMbfj(String mbfj) {
        this.mbfj = mbfj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj == null ? null : (Date) cjsj.clone();
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj == null ? null : (Date) cjsj.clone();
    }

    public String getGxr() {
        return gxr;
    }

    public void setGxr(String gxr) {
        this.gxr = gxr;
    }

    public Date getGxsj() {
        return gxsj == null ? null : (Date) gxsj.clone();
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj == null ? null : (Date) gxsj.clone();
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public List<WjmbflDO> getWjmbflDOs() {
        return new ArrayList<>(wjmbflDOs);
    }

    public void setWjmbflDOs(List<WjmbflDO> wjmbflDOs) {
        this.wjmbflDOs.clear();
        this.wjmbflDOs.addAll(wjmbflDOs);
    }
    @Override
    public WjmbDO clone() throws CloneNotSupportedException {
        return (WjmbDO) super.clone();
    }
}
