package com.winning.batjx.core.khgl.khwj.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * 预览Excel 的 题目虚拟实体
 * Created by thinkpad on 2021/3/9.
 */
@ColumnWidth(25)
public class TmExcel extends BaseRowModel {

    @ExcelProperty("题目编码")
    private String tmbm;

    @ExcelProperty("题目名称")
    private String tmmc;

    @ExcelProperty("题目类型")
    private String tmlxmc;

    @ExcelProperty("副标题")
    private String tmfbt;

    @ExcelProperty("选项1")
    private String xxnrExcel_1;

    @ExcelProperty("选项2")
    private String xxnrExcel_2;

    @ExcelProperty("选项3")
    private String xxnrExcel_3;

    @ExcelProperty("选项4")
    private String xxnrExcel_4;

    @ExcelProperty("选项5")
    private String xxnrExcel_5;

    @ExcelProperty("选项6")
    private String xxnrExcel_6;

    @ExcelProperty("选项7")
    private String xxnrExcel_7;

    @ExcelProperty("选项8")
    private String xxnrExcel_8;

    @ExcelProperty("选项9")
    private String xxnrExcel_9;

    @ExcelProperty("选项10")
    private String xxnrExcel_10;

    public String getTmbm() {
        return tmbm;
    }

    public void setTmbm(String tmbm) {
        this.tmbm = tmbm;
    }

    public String getTmmc() {
        return tmmc;
    }

    public void setTmmc(String tmmc) {
        this.tmmc = tmmc;
    }

    public String getTmlxmc() {
        return tmlxmc;
    }

    public void setTmlxmc(String tmlxmc) {
        this.tmlxmc = tmlxmc;
    }

    public String getTmfbt() {
        return tmfbt;
    }

    public void setTmfbt(String tmfbt) {
        this.tmfbt = tmfbt;
    }

    public String getXxnrExcel_1() {
        return xxnrExcel_1;
    }

    public void setXxnrExcel_1(String xxnrExcel_1) {
        this.xxnrExcel_1 = xxnrExcel_1;
    }

    public String getXxnrExcel_2() {
        return xxnrExcel_2;
    }

    public void setXxnrExcel_2(String xxnrExcel_2) {
        this.xxnrExcel_2 = xxnrExcel_2;
    }

    public String getXxnrExcel_3() {
        return xxnrExcel_3;
    }

    public void setXxnrExcel_3(String xxnrExcel_3) {
        this.xxnrExcel_3 = xxnrExcel_3;
    }

    public String getXxnrExcel_4() {
        return xxnrExcel_4;
    }

    public void setXxnrExcel_4(String xxnrExcel_4) {
        this.xxnrExcel_4 = xxnrExcel_4;
    }

    public String getXxnrExcel_5() {
        return xxnrExcel_5;
    }

    public void setXxnrExcel_5(String xxnrExcel_5) {
        this.xxnrExcel_5 = xxnrExcel_5;
    }

    public String getXxnrExcel_6() {
        return xxnrExcel_6;
    }

    public void setXxnrExcel_6(String xxnrExcel_6) {
        this.xxnrExcel_6 = xxnrExcel_6;
    }

    public String getXxnrExcel_7() {
        return xxnrExcel_7;
    }

    public void setXxnrExcel_7(String xxnrExcel_7) {
        this.xxnrExcel_7 = xxnrExcel_7;
    }

    public String getXxnrExcel_8() {
        return xxnrExcel_8;
    }

    public void setXxnrExcel_8(String xxnrExcel_8) {
        this.xxnrExcel_8 = xxnrExcel_8;
    }

    public String getXxnrExcel_9() {
        return xxnrExcel_9;
    }

    public void setXxnrExcel_9(String xxnrExcel_9) {
        this.xxnrExcel_9 = xxnrExcel_9;
    }

    public String getXxnrExcel_10() {
        return xxnrExcel_10;
    }

    public void setXxnrExcel_10(String xxnrExcel_10) {
        this.xxnrExcel_10 = xxnrExcel_10;
    }
}
