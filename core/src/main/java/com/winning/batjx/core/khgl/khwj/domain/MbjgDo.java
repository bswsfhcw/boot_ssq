package com.winning.batjx.core.khgl.khwj.domain;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/11
 * @time 9:09
 * @description qyjx_khwj_mbjg
 */

public class MbjgDo {

    private Integer id;

    private Integer wjjgid; // 结果id

    private Integer wjmbid; // 模板id

    private String wjbt; // 标题

    private String wjfbt; // 副标题

    private String wjxsfs; // 问卷显示方式

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWjjgid() {
        return wjjgid;
    }

    public void setWjjgid(Integer wjjgid) {
        this.wjjgid = wjjgid;
    }

    public Integer getWjmbid() {
        return wjmbid;
    }

    public void setWjmbid(Integer wjmbid) {
        this.wjmbid = wjmbid;
    }

    public String getWjbt() {
        return wjbt;
    }

    public void setWjbt(String wjbt) {
        this.wjbt = wjbt;
    }

    public String getWjfbt() {
        return wjfbt;
    }

    public void setWjfbt(String wjfbt) {
        this.wjfbt = wjfbt;
    }

    public String getWjxsfs() {
        return wjxsfs;
    }

    public void setWjxsfs(String wjxsfs) {
        this.wjxsfs = wjxsfs;
    }
}
