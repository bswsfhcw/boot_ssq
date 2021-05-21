package com.winning.batjx.core.common.domain;

/**
 * @company:卫宁健康科技集团股份有限公司
 * @team:基层业务研发部-基层产品支持组
 * @program: batjx
 * @description: 日志DTO
 * @author: liuyanchao
 * @create: 2020/7/3 9:49
 * @History: <author>          <time>          <version>          <desc>
 * 姓名             修改时间           版本号              描述
 */
public class LogDTO implements java.io.Serializable{

    /**
     * 序号
     */
    private String xh;
    /**
     * 日志分类 1为信息 2为警告 3为错误  对应数据字典分类9903
     */
    private String rzfl;
    /**
     * 人员编码
     */
    private String rybm;
    /**
     * 机构编码
     */
    private String jgbm;
    /**
     * ip地址对照
     */
    private String ipdz;
    /**
     * 操作类型， 1.登录 2.菜单 9.注销 对应数据字典分类9905
     */
    private String rzlx;
    /**
     * 窗口名称
     */
    private String ckmc;
    /**
     * 日志内容
     */
    private String rznr;
    /**
     * 记录时间
     */
    private String jlsj;

    private String szbm;

    private String gjz;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getRzfl() {
        return rzfl;
    }

    public void setRzfl(String rzfl) {
        this.rzfl = rzfl;
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

    public String getIpdz() {
        return ipdz;
    }

    public void setIpdz(String ipdz) {
        this.ipdz = ipdz;
    }

    public String getRzlx() {
        return rzlx;
    }

    public void setRzlx(String rzlx) {
        this.rzlx = rzlx;
    }

    public String getCkmc() {
        return ckmc;
    }

    public void setCkmc(String ckmc) {
        this.ckmc = ckmc;
    }

    public String getRznr() {
        return rznr;
    }

    public void setRznr(String rznr) {
        this.rznr = rznr;
    }

    public String getJlsj() {
        return jlsj;
    }

    public void setJlsj(String jlsj) {
        this.jlsj = jlsj;
    }

    public String getSzbm() {
        return szbm;
    }

    public void setSzbm(String szbm) {
        this.szbm = szbm;
    }

    public String getGjz() {
        return gjz;
    }

    public void setGjz(String gjz) {
        this.gjz = gjz;
    }
}
