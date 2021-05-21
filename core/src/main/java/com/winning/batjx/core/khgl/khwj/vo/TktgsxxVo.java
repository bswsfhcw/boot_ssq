package com.winning.batjx.core.khgl.khwj.vo;

import java.math.BigDecimal;

/**
 * @author yxf@winning.com.cn
 * @date 2021/4/2
 * @time 11:15
 * @description 填空题的公式信息
 *
 */

public class TktgsxxVo {

    private Integer id;

    private Integer fltmglid;

    private String sfgsmc;

    private String sfgsbm;

    private Integer xh;

    private BigDecimal fdksz;//

    private String fdksjsf;

    private BigDecimal fdjsz;//

    private String fdjsjsf;//

    private BigDecimal dfz;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFltmglid() {
        return fltmglid;
    }

    public void setFltmglid(Integer fltmglid) {
        this.fltmglid = fltmglid;
    }

    public String getSfgsmc() {
        return sfgsmc;
    }

    public void setSfgsmc(String sfgsmc) {
        this.sfgsmc = sfgsmc;
    }

    public String getSfgsbm() {
        return sfgsbm;
    }

    public void setSfgsbm(String sfgsbm) {
        this.sfgsbm = sfgsbm;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public BigDecimal getFdksz() {
        return fdksz;
    }

    public void setFdksz(BigDecimal fdksz) {
        this.fdksz = fdksz;
    }

    public String getFdksjsf() {
        return fdksjsf;
    }

    public void setFdksjsf(String fdksjsf) {
        this.fdksjsf = fdksjsf;
    }

    public BigDecimal getFdjsz() {
        return fdjsz;
    }

    public void setFdjsz(BigDecimal fdjsz) {
        this.fdjsz = fdjsz;
    }

    public String getFdjsjsf() {
        return fdjsjsf;
    }

    public void setFdjsjsf(String fdjsjsf) {
        this.fdjsjsf = fdjsjsf;
    }

    public BigDecimal getDfz() {
        return dfz;
    }

    public void setDfz(BigDecimal dfz) {
        this.dfz = dfz;
    }
}
