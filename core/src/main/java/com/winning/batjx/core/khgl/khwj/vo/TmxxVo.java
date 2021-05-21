package com.winning.batjx.core.khgl.khwj.vo;

import java.math.BigDecimal;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/19
 * @time 10:09
 * @description
 */

public class TmxxVo {

    private Integer xxid;

    private Integer tmid;

    private String xxnr;

    private String sfgx;

    private Integer xxsx;

    private String tmbz;

    private BigDecimal xxfz;

    public BigDecimal getXxfz() {
        return xxfz;
    }

    public void setXxfz(BigDecimal xxfz) {
        this.xxfz = xxfz;
    }

    public String getTmbz() {
        return tmbz;
    }

    public void setTmbz(String tmbz) {
        this.tmbz = tmbz;
    }

    public String getSfgx() {
        return sfgx;
    }

    public void setSfgx(String sfgx) {
        this.sfgx = sfgx;
    }

    public Integer getXxsx() {
        return xxsx;
    }

    public void setXxsx(Integer xxsx) {
        this.xxsx = xxsx;
    }

    public Integer getXxid() {
        return xxid;
    }

    public void setXxid(Integer xxid) {
        this.xxid = xxid;
    }

    public Integer getTmid() {
        return tmid;
    }

    public void setTmid(Integer tmid) {
        this.tmid = tmid;
    }

    public String getXxnr() {
        return xxnr;
    }

    public void setXxnr(String xxnr) {
        this.xxnr = xxnr;
    }


}
