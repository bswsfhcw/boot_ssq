package com.winning.batjx.core.common.util;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-02 16:45
 **/
public class PageRequest implements java.io.Serializable{

    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize==0?20:pageSize);
    }

}
