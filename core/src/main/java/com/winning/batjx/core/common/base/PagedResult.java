package com.winning.batjx.core.common.base;

import com.github.pagehelper.PageInfo;

import java.util.List;


public class PagedResult<E> {
    private long total;
    private int pages;
    private List<E> list;

    public PagedResult(PageInfo pageInfo) {
        total = pageInfo.getTotal();
        pages = pageInfo.getPages();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
