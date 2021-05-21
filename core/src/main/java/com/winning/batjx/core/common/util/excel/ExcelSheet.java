package com.winning.batjx.core.common.util.excel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用于汇出多个sheet的Vo The <code>ExcelSheet</code>
 */
public class ExcelSheet<T> {
    private String sheetName;
    private String[] headers =new String[]{};
    private Collection<T> dataset = new ArrayList<>();

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Excel页签名称
     * 
     * @param sheetName
     *            the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getHeaders() {
        return headers == null ? null : headers.clone();
    }

    public void setHeaders(String[] headers) {
        this.headers = headers == null ? null : headers.clone();
    }

    /**
     * Excel数据集合
     * 
     * @return the dataset
     */
    public Collection<T> getDataset() {
        return new ArrayList<>(dataset);
    }

    /**
     * @param dataset
     *            the dataset to set
     */
    public void setDataset(Collection<T> dataset) {
        this.dataset.clear();
        this.dataset.addAll(dataset);
    }

}
