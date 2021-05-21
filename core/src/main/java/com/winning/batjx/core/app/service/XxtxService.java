package com.winning.batjx.core.app.service;

import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/21
 */
public interface XxtxService {
    /**
     * @return
     */
    Map<String,Object> queryXxtxList(Map<String, Object> params);
    boolean ydxx(Map<String, Object> params);
    boolean scxx(Map<String, Object> params);

}
