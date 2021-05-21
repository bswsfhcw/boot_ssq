package com.winning.batjx.core.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 *
 */
public class LoginUser implements Serializable {

    private Integer id;

    private String rysfz;

    private String ryxm;

    private String rygh;

    private Integer xb;

    private String sjhm;

    private String dzyx;

    private String xzdz;

    private String ryzc;

    private String ryzw;

    private String whcd;

    private String dlmm;

    private String jgbm;

    private String jgmc;

    private String jlzt;

    private String czr;

    private Date czsj;

    private String jglb;

    private String jgjb;

    private List<Role> roles = new ArrayList<>();

    /**
     * 0:省 1:市  2:区县  3:社区  4:站室 5：科室 6：团队 7:医共体
     *
     * */
    private String jglx;

    private Integer jgid;

    private Integer sjjgid;

    private String sffzr;

    /**
     * 一个人属于多个科室，ksbm 以逗号分隔
     * */
    private String ksbm;

    //是否在编
    private String sfzb;

    //岗位系数
    private BigDecimal gwxs;

    /**--机构外码  浙江数据上传--**/
    private String  jgwm;

    public String getKsbm() {
        return ksbm;
    }

    public void setKsbm(String ksbm) {
        this.ksbm = ksbm;
    }



    public String getJglx() {
        return jglx;
    }

    public void setJglx(String jglx) {
        this.jglx = jglx;
    }

    public String getJglb() {
        return jglb;
    }

    public void setJglb(String jglb) {
        this.jglb = jglb;
    }

    public String getJgjb() {
        return jgjb;
    }

    public void setJgjb(String jgjb) {
        this.jgjb = jgjb;
    }

    public Integer getJgid() {
        return jgid;
    }

    public void setJgid(Integer jgid) {
        this.jgid = jgid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRysfz() {
        return rysfz;
    }

    public void setRysfz(String rysfz) {
        this.rysfz = rysfz;
    }

    public String getRyxm() {
        return ryxm;
    }

    public String getSffzr() {
        return sffzr;
    }

    public void setSffzr(String sffzr) {
        this.sffzr = sffzr;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getRygh() {
        return rygh;
    }

    public void setRygh(String rygh) {
        this.rygh = rygh;
    }

    public Integer getXb() {
        return xb;
    }

    public void setXb(Integer xb) {
        this.xb = xb;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getDzyx() {
        return dzyx;
    }

    public void setDzyx(String dzyx) {
        this.dzyx = dzyx;
    }
    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getXzdz() {
        return xzdz;
    }

    public void setXzdz(String xzdz) {
        this.xzdz = xzdz;
    }

    public String getRyzc() {
        return ryzc;
    }

    public void setRyzc(String ryzc) {
        this.ryzc = ryzc;
    }

    public String getRyzw() {
        return ryzw;
    }

    public void setRyzw(String ryzw) {
        this.ryzw = ryzw;
    }

    public String getWhcd() {
        return whcd;
    }

    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    public String getDlmm() {
        return dlmm;
    }

    public void setDlmm(String dlmm) {
        this.dlmm = dlmm;
    }

    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
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
        this.czsj = czsj==null?null:(Date)czsj.clone();
    }

    public List<Role> getRoles() {
        return new ArrayList<>(roles);
    }

    public void setRoles(List<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

    public Integer getSjjgid() {
        return sjjgid;
    }

    public void setSjjgid(Integer sjjgid) {
        this.sjjgid = sjjgid;
    }


    public String getSfzb() {
        return sfzb;
    }

    public void setSfzb(String sfzb) {
        this.sfzb = sfzb;
    }

    public BigDecimal getGwxs() {
        return gwxs;
    }

    public void setGwxs(BigDecimal gwxs) {
        this.gwxs = gwxs;
    }

    public String getJgwm() {
        return jgwm;
    }

    public void setJgwm(String jgwm) {
        this.jgwm = jgwm;
    }
}
