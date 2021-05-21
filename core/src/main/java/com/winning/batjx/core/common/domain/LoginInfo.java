package com.winning.batjx.core.common.domain;

public class LoginInfo implements java.io.Serializable {

    private String username;
    private String password;

    private String randomcode;

    /**
     * 人员流水号
     */
    private String zclsh;
    /**
     * 人员编码
     */
    private String rybm;
    /**
     * 机构编码
     */
    private String jgbm;
    /**
     * 人员工号
     */
    private String rygh;
    /**
     * 人员姓名
     */
    private String ryxm;


    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZclsh() {
        return zclsh;
    }

    public void setZclsh(String zclsh) {
        this.zclsh = zclsh;
    }

    public String getRybm() {
        return rybm;
    }

    public void setRybm(String rybm) {
        this.rybm = rybm;
    }

    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }

    public String getRygh() {
        return rygh;
    }

    public void setRygh(String rygh) {
        this.rygh = rygh;
    }


    public String getRandomcode() {
        return randomcode;
    }

    public void setRandomcode(String randomcode) {
        this.randomcode = randomcode;
    }
}
