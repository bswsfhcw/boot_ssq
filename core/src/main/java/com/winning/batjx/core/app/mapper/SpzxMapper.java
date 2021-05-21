package com.winning.batjx.core.app.mapper;

import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 、
 */

public interface SpzxMapper {
    /**
     * 查询方案列表
     *
     * @param jgbm 机构代码
     * @param khnf 考核年份
     * @return
     */
    Map queryLcslid(Map<String, Object> params);
    List<Map> queryShlx(Map<String, Object> params);
    String querySjtbLx(Map<String, Object> params);
    List<Map> querySpxxKhdxSjtb(Map<String, Object> params);
    List<Map> querySpxxKhdxPjjs(Map<String, Object> params);
    List<Map> querySpxxSjtb(Map<String, Object> params);
    int querybjzbCountSjtb(Map<String, Object> params);
    List<Map> querySpxxPjjs(Map<String, Object> params);
    List<Map> querySpxxSjtbGr(Map<String, Object> params);
    List<Map> querySpxxPjjsGr(Map<String, Object> params);
    void bjzbListSjtb(Map<String, Object> params);
    void bjzbListPjjs(Map<String, Object> params);
}