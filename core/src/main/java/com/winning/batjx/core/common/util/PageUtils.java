package com.winning.batjx.core.common.util;

import com.github.pagehelper.PageInfo;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-02 16:43
 **/
public class PageUtils {

    /**
     * 将分页信息封装到统一的接口
     * @param pageInfo
     * @return
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setRecords(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setRows(pageInfo.getList());
        return pageResult;
    }
}
