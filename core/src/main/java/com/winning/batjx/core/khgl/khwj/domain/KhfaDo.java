package com.winning.batjx.core.khgl.khwj.domain;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/9
 * @time 16:07
 * @description 考核方案信息
 */

public class KhfaDo {

    private String faid;

    private String khnf;

    private String khpl;

    private String khplmc;

    private String famc;

    private String qyzt; // 0未启用 1启用

    public String getFaid() {
        return faid;
    }

    public void setFaid(String faid) {
        this.faid = faid;
    }

    public String getKhnf() {
        return khnf;
    }

    public void setKhnf(String khnf) {
        this.khnf = khnf;
    }

    public String getKhpl() {
        return khpl;
    }

    public void setKhpl(String khpl) {
        this.khpl = khpl;
    }

    public String getKhplmc() {
        return khplmc;
    }

    public void setKhplmc(String khplmc) {
        this.khplmc = khplmc;
    }

    public String getFamc() {
        return famc;
    }

    public void setFamc(String famc) {
        this.famc = famc;
    }

    public String getQyzt() {
        return qyzt;
    }

    public void setQyzt(String qyzt) {
        this.qyzt = qyzt;
    }


}

