package com.winning.batjx.core.khgl.khwj.domain;

import com.winning.jbase.common.util.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @company:卫宁健康科技集团股份有限公司
 * @team:基层业务研发部-基层产品支持组
 * @program: batjx
 * @description: MDM主数据的对应的字段
 * @author: liuyanchao
 * @create: 2020/6/19 15:24
 * @History: <author>          <time>          <version>          <desc>
 * 姓名             修改时间           版本号              描述
 */
public class UserDTO implements java.io.Serializable{

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATE_FORMATTER_ = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DATE_TIME_FORMATTER_ = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 机构编码
     */
    private String orgCode;
    /**
     * 人员名称
     */
    private String employeeName;
    /**
     * 人员工号
     */
    private String rygh;
    /**
     * 拼音码
     */
    private String py;
    /**
     * 五笔码
     */
    private String wb;
    /**
     * 性别编码
     */
    private String genderCode;
    /**
     * 出生日期
     */
    private String birthDate;
    /**
     * 身份证件类别代码
     */
    private String IDTypeCode;
    /**
     * 身份证件号码
     */
    private String IDNo;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 有效开始时间
     */
    private String validStartDTime;
    /**
     * 有效结束时间
     */
    private String validEndDTime;
    /**
     * 有效标志   0:无效,1:有效
     */
    private String activeSign;

    public static UserDO convertUserDTO(UserDTO userDTO){
        UserDO userDO = new UserDO();
        userDO.setJgbm(userDTO.getOrgCode());
        userDO.setRyxm(userDTO.getEmployeeName());
        userDO.setRygh(userDTO.getRygh());

        userDO.setDldm("");
        userDO.setDlmm("");
        userDO.setXb(userDTO.getGenderCode());

        userDO.setCsrq(userDTO.getBirthDate());
        userDO.setSfzh(userDTO.getIDNo());
        userDO.setDhhm(userDTO.getTelephone());

        userDO.setKsrq(userDTO.getValidStartDTime());
        userDO.setJsrq(userDTO.getValidEndDTime());
        userDO.setJlzt(userDTO.getActiveSign());
        return userDO;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getWb() {
        return wb;
    }

    public void setWb(String wb) {
        this.wb = wb;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getBirthDate() {
        LocalDate localDate = LocalDate.parse(birthDate,DATE_FORMATTER_);
        String localDateStr = localDate.format(DATE_FORMATTER);
        return !StringUtil.isEmpty(birthDate)? localDateStr : birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIDTypeCode() {
        return IDTypeCode;
    }

    public void setIDTypeCode(String IDTypeCode) {
        this.IDTypeCode = IDTypeCode;
    }

    public String getIDNo() {
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getValidStartDTime() {
        LocalDateTime localDateTime = LocalDateTime.parse(validStartDTime,DATE_TIME_FORMATTER_);
        String localDateTimeStr = localDateTime.format(DATE_TIME_FORMATTER);
        return !StringUtil.isEmpty(validStartDTime)? localDateTimeStr : validStartDTime;
    }

    public void setValidStartDTime(String validStartDTime) {
        this.validStartDTime = validStartDTime;
    }

    public String getValidEndDTime() {
        LocalDateTime localDateTime = LocalDateTime.parse(validEndDTime,DATE_TIME_FORMATTER_);
        String localDateTimeStr = localDateTime.format(DATE_TIME_FORMATTER);
        return !StringUtil.isEmpty(validEndDTime)? localDateTimeStr : validEndDTime;
    }

    public void setValidEndDTime(String validEndDTime) {
        this.validEndDTime = validEndDTime;
    }

    public String getActiveSign() {
        return activeSign;
    }

    public void setActiveSign(String activeSign) {
        this.activeSign = activeSign;
    }

    public String getRygh() {
        return rygh;
    }

    public void setRygh(String rygh) {
        this.rygh = rygh;
    }
}
