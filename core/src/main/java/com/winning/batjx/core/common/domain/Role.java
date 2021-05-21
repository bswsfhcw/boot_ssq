package com.winning.batjx.core.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class Role implements Serializable {
    //角色ID
    private Integer id;
    //角色名称
    private String jsmc;
    //角色描述
    private String jsms;
    //记录状态
    private String jlzt;
    //操作人ID
    private String czr;
    //操作时间
    private Date czsj;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public String getJsms() {
        return jsms;
    }

    public void setJsms(String jsms) {
        this.jsms = jsms;
    }

    public String getJlzt() {
        return jlzt;
    }

    public void setJlzt(String jlzt) {
        this.jlzt = jlzt;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getCzsj() {
        return czsj==null?null:(Date)czsj.clone();
    }

    public void setCzsj(Date czsj) {
        this.czsj = (Date)czsj.clone();
    }
}
