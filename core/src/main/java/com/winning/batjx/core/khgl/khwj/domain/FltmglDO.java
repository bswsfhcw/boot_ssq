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
public class FltmglDO implements java.io.Serializable,Cloneable {
    @ApiModelProperty(notes = "主键id")
    private int id;

    @ApiModelProperty(notes = "模板分类id")
    private int mbflid;

    @ApiModelProperty(notes = "题目id")
    private int tmid;

    @ApiModelProperty(notes = "题目顺序")
    private int tmsx;

    @ApiModelProperty(notes = "题目答案来源(0无1默认填写2关联计算)")
    private String tmdaly;

    @ApiModelProperty(notes = "题目答案公式名称")
    private String tmdagsmc;

    @ApiModelProperty(notes = "题目答案公式编码")
    private String tmdagsbm;

    @ApiModelProperty(notes = "填空值是否为得分(0否1是)")
    private String tkzsfwdf;

    @ApiModelProperty(notes = "题目算分方式(1选项得分2选项得分累计3题目分)")
    private String tmsffs;

    @ApiModelProperty(notes = "算分方法分类(1自定义公式2固定分段)")
    private String tmsffffl;

    @ApiModelProperty(notes = "题目算分表达式描述")
    private String tmsfbdsms;

    /******************扩展分类基础属性***********************/
    @ApiModelProperty(notes = "模板分类名称")
    private String mbflmc;

    /******************扩展题目基础属性***********************/
    @ApiModelProperty(notes = "题目编码")
    private String tmbm;

    @ApiModelProperty(notes = "题目名称")
    private String tmmc;

    @ApiModelProperty(notes = "题目类型(1单选2多选3填空)")
    private String tmlx;

    @ApiModelProperty(notes = "操作类型 1新增 2更新 3 删除")
    private String czlx;

    private List<XxfszDO> xxfszDOs  =new ArrayList<>();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMbflid() {
        return mbflid;
    }

    public void setMbflid(int mbflid) {
        this.mbflid = mbflid;
    }

    public int getTmid() {
        return tmid;
    }

    public void setTmid(int tmid) {
        this.tmid = tmid;
    }

    public int getTmsx() {
        return tmsx;
    }

    public void setTmsx(int tmsx) {
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

    public String getMbflmc() {
        return mbflmc;
    }

    public void setMbflmc(String mbflmc) {
        this.mbflmc = mbflmc;
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

    public String getTmlx() {
        return tmlx;
    }

    public void setTmlx(String tmlx) {
        this.tmlx = tmlx;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public List<XxfszDO> getXxfszDOs() {
        return  (xxfszDOs==null||xxfszDOs.size()==0)?null:new ArrayList<>(xxfszDOs);
    }

    public void setXxfszDOs(List<XxfszDO> xxfszDOs) {
        this.xxfszDOs.clear();
        this.xxfszDOs.addAll(xxfszDOs);
    }
    @Override
    public FltmglDO clone() throws CloneNotSupportedException {
        return (FltmglDO) super.clone();
    }
}
