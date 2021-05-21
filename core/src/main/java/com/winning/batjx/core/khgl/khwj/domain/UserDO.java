package com.winning.batjx.core.khgl.khwj.domain;

import com.winning.jbase.common.util.StringUtil;

import java.util.UUID;

/**
 * @company:卫宁健康科技集团股份有限公司
 * @team:基层业务研发部-基层产品支持组
 * @program: batjx
 * @description: 操作人员DO
 * @author: liuyanchao
 * @create: 2020/7/7 13:53
 * @History: <author>          <time>          <version>          <desc>
 * 姓名             修改时间           版本号              描述
 */
public class UserDO implements java.io.Serializable {
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
    /**
     * 登录代码
     */
    private String dldm;
    /**
     * 登录密码
     */
    private String dlmm;
    /**
     * 性别
     */
    private String xb;
    /**
     * 出生日期
     */
    private String csrq;
    /**
     * 身份证号码
     */
    private String sfzh;
    /**
     * 电话号码
     */
    private String dhhm;
    /**
     * 人员类型
     */
    private String rylx;
    /**
     * 开始日期
     */
    private String ksrq;
    /**
     * 结束日期
     */
    private String jsrq;
    /**
     * 记录状态
     */
    private String jlzt;
    /**
     * 操作类型
     */
    private String czlx;

    /**
     * 人员编码
     */
    private String rybm;

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

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getDldm() {
        return dldm;
    }

    public void setDldm(String dldm) {
        this.dldm = dldm;
    }

    public String getDlmm() {
        return dlmm;
    }

    public void setDlmm(String dlmm) {
        this.dlmm = dlmm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getDhhm() {
        return dhhm;
    }

    public void setDhhm(String dhhm) {
        this.dhhm = dhhm;
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx;
    }

    public String getKsrq() {
        return ksrq;
    }

    public void setKsrq(String ksrq) {
        this.ksrq = ksrq;
    }

    public String getJsrq() {
        return jsrq;
    }

    public void setJsrq(String jsrq) {
        this.jsrq = jsrq;
    }

    public String getJlzt() {
        return jlzt;
    }

    public void setJlzt(String jlzt) {
        this.jlzt = jlzt;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getRybm() {
        return StringUtil.isEmpty(rybm)? UUID.randomUUID().toString():rybm;
    }

    public void setRybm(String rybm) {
        this.rybm = rybm;
    }
}
