package com.winning.batjx.core.khgl.khwj.vo;

import java.math.BigDecimal;

/** 目标对应表
 * Created by thinkpad on 2019/6/11.
 */
public class QyjxKhhzXxVo {

    //主键
    private  Integer khhzXxSubId;
    // QYJX_KHHZ 表 id
    private  Integer fid;
    //考核类型
    private  Integer khlx;
    //类型名称
    private String lxmc;
    //分类等级
    private Integer fldj;
    //一级分类编码
    private String yjflbm;
    //一级分类名称
    private String yjflmc;
    //二级分类编码
    private String ejflbm;
    //二级分类名称
    private String ejflmc;
    //三级分类编码
    private String sjflbm;
    //三级分类名称
    private String sjflmc;
    //考核指标名称
    private String khzbmc;
    //考核指标编码
    private String khzbbm;
    //采集方式编码
    private String cjfsbm;
    //采集方式名称
    private String cjfsmc;
    //正负向标志
    private Integer zfxbz;
    //指标值
    private BigDecimal zbz;
    //目标得分
    private BigDecimal mbdf;
    //指标得分
    private BigDecimal zbdf;
    //标化工作量
    private BigDecimal bhgzl;
    //指标奖金
    private BigDecimal zbjj;
    //操作人
    private String czr;
    //机构编码
    private String jgbm;
    //类型
    private String type;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getKhhzXxSubId() {
        return khhzXxSubId;
    }

    public void setKhhzXxSubId(Integer khhzXxSubId) {
        this.khhzXxSubId = khhzXxSubId;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getKhlx() {
        return khlx;
    }

    public void setKhlx(Integer khlx) {
        this.khlx = khlx;
    }

    public String getLxmc() {
        return lxmc;
    }

    public void setLxmc(String lxmc) {
        this.lxmc = lxmc;
    }

    public Integer getFldj() {
        return fldj;
    }

    public void setFldj(Integer fldj) {
        this.fldj = fldj;
    }

    public String getYjflbm() {
        return yjflbm;
    }

    public void setYjflbm(String yjflbm) {
        this.yjflbm = yjflbm;
    }

    public String getYjflmc() {
        return yjflmc;
    }

    public void setYjflmc(String yjflmc) {
        this.yjflmc = yjflmc;
    }

    public String getEjflbm() {
        return ejflbm;
    }

    public void setEjflbm(String ejflbm) {
        this.ejflbm = ejflbm;
    }

    public String getEjflmc() {
        return ejflmc;
    }

    public void setEjflmc(String ejflmc) {
        this.ejflmc = ejflmc;
    }

    public String getSjflbm() {
        return sjflbm;
    }

    public void setSjflbm(String sjflbm) {
        this.sjflbm = sjflbm;
    }

    public String getSjflmc() {
        return sjflmc;
    }

    public void setSjflmc(String sjflmc) {
        this.sjflmc = sjflmc;
    }

    public String getKhzbmc() {
        return khzbmc;
    }

    public void setKhzbmc(String khzbmc) {
        this.khzbmc = khzbmc;
    }

    public String getKhzbbm() {
        return khzbbm;
    }

    public void setKhzbbm(String khzbbm) {
        this.khzbbm = khzbbm;
    }

    public String getCjfsbm() {
        return cjfsbm;
    }

    public void setCjfsbm(String cjfsbm) {
        this.cjfsbm = cjfsbm;
    }

    public String getCjfsmc() {
        return cjfsmc;
    }

    public void setCjfsmc(String cjfsmc) {
        this.cjfsmc = cjfsmc;
    }

    public Integer getZfxbz() {
        return zfxbz;
    }

    public void setZfxbz(Integer zfxbz) {
        this.zfxbz = zfxbz;
    }

    public BigDecimal getZbz() {
        return zbz;
    }

    public void setZbz(BigDecimal zbz) {
        this.zbz = zbz;
    }

    public BigDecimal getMbdf() {
        return mbdf;
    }

    public void setMbdf(BigDecimal mbdf) {
        this.mbdf = mbdf;
    }

    public BigDecimal getZbdf() {
        return zbdf;
    }

    public void setZbdf(BigDecimal zbdf) {
        this.zbdf = zbdf;
    }

    public BigDecimal getBhgzl() {
        return bhgzl;
    }

    public void setBhgzl(BigDecimal bhgzl) {
        this.bhgzl = bhgzl;
    }

    public BigDecimal getZbjj() {
        return zbjj;
    }

    public void setZbjj(BigDecimal zbjj) {
        this.zbjj = zbjj;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }
}
