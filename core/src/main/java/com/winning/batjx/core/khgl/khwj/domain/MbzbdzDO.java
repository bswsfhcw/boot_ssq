package com.winning.batjx.core.khgl.khwj.domain;

import java.io.Serializable;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-02 14:28
 **/
public class MbzbdzDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String wjmbbh;

    private String zbbm;

    private String jgbm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWjmbbh() {
        return wjmbbh;
    }

    public void setWjmbbh(String wjmbbh) {
        this.wjmbbh = wjmbbh;
    }

    public String getZbbm() {
        return zbbm;
    }

    public void setZbbm(String zbbm) {
        this.zbbm = zbbm;
    }

    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }
}
