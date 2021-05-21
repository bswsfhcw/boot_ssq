package com.winning.batjx.core.khgl.khwj.vo;

import java.math.BigDecimal;

/** 目标表【 QYJX_KHHZ 】
 * Created by thinkpad on 2019/6/11.
 */
public class QyjxKhhzVo implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    //主键ID
    private Integer khhzMainId;
    //方案ID
    private Integer faid;
    //方案名称
    private String famc;
    //考核频率
    private String khpl;
    //考核年份
    private Integer khnf;
    //考核周期
    private Integer khzq;
    //考核对象类型
    private String khdxlx;
    //考核对象编码
    private String khdxbm;
    //考核对象名称
    private String khdxmc;
    //总目标得分
    private BigDecimal mbdf;
    //总指标得分
    private BigDecimal zbdf;
    //总标化工作量
    private BigDecimal zbhgzl;
    //奖金
    private BigDecimal jj;
    //机构编码
    private String jgbm;
    //操作人
    private String czr;
    private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getKhhzMainId() {
        return khhzMainId;
    }

    public void setKhhzMainId(Integer khhzMainId) {
        this.khhzMainId = khhzMainId;
    }

    public Integer getFaid() {
        return faid;
    }

    public void setFaid(Integer faid) {
        this.faid = faid;
    }

    public String getFamc() {
        return famc;
    }

    public void setFamc(String famc) {
        this.famc = famc;
    }

    public String getKhpl() {
        return khpl;
    }

    public void setKhpl(String khpl) {
        this.khpl = khpl;
    }

    public Integer getKhnf() {
        return khnf;
    }

    public void setKhnf(Integer khnf) {
        this.khnf = khnf;
    }

    public Integer getKhzq() {
        return khzq;
    }

    public void setKhzq(Integer khzq) {
        this.khzq = khzq;
    }

    public String getKhdxlx() {
        return khdxlx;
    }

    public void setKhdxlx(String khdxlx) {
        this.khdxlx = khdxlx;
    }

    public String getKhdxbm() {
        return khdxbm;
    }

    public void setKhdxbm(String khdxbm) {
        this.khdxbm = khdxbm;
    }

    public String getKhdxmc() {
        return khdxmc;
    }

    public void setKhdxmc(String khdxmc) {
        this.khdxmc = khdxmc;
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

    public BigDecimal getZbhgzl() {
        return zbhgzl;
    }

    public void setZbhgzl(BigDecimal zbhgzl) {
        this.zbhgzl = zbhgzl;
    }

    public BigDecimal getJj() {
        return jj;
    }

    public void setJj(BigDecimal jj) {
        this.jj = jj;
    }

    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }
}
