package com.winning.batjx.core.khgl.khwj.domain;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/11
 * @time 9:18
 * @description 答题结果选项 qyjx_khwj_dajgxx
 */

public class DtjgxxDo {

    private Integer id;

    private Integer dtjgid; // 答题结果id

    private String xxnr; // 选项内容

    private String sfgx; // 是否勾选

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDtjgid() {
        return dtjgid;
    }

    public void setDtjgid(Integer dtjgid) {
        this.dtjgid = dtjgid;
    }

    public String getXxnr() {
        return xxnr;
    }

    public void setXxnr(String xxnr) {
        this.xxnr = xxnr;
    }

    public String getSfgx() {
        return sfgx;
    }

    public void setSfgx(String sfgx) {
        this.sfgx = sfgx;
    }
}
