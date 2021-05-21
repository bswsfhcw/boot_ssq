package com.winning.batjx.core.khgl.khwj.domain;

/**
 * Created by thinkpad on 2021/3/2.
 */
public class TmxxDO implements java.io.Serializable{

    private Integer id;

    /**
     * 题目id(关联题目表id)
     */
    private Integer tmid;

    /**
     * 选项内容
     */
    private String xxnr;

    /**
     * 选项顺序
     */
    private Integer xxsx;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getXxsx() {
        return xxsx;
    }

    public void setXxsx(Integer xxsx) {
        this.xxsx = xxsx;
    }
}
