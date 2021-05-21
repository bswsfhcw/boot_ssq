package com.winning.batjx.core.app.service;

import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/21
 */
public interface SpzxService {
    /**
     * 查询方案列表
     *
     * @param jgbm 机构代码
     * @param khnf 考核年份
     * @return
     */
    Map<String, Object> pdspxx(Map<String, Object> params);
    String querySjtbLx(Map<String, Object> params);
    List<Map> querySpxxKhdx(Map<String, Object> params);
    Map querySpxx(Map<String, Object> params);
    List<Map> querySpxxGr(Map<String, Object> params);
    boolean bjzbList(Map<String, Object> params);
    boolean spxx(Map<String, Object> params);
}
