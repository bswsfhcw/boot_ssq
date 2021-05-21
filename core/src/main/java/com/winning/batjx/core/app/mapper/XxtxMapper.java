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

public interface XxtxMapper {
    /**
     * 查询消息列表
     *
     * @return
     */
    List<Map> queryXxtxList(Map<String, Object> params);

    /**
     * 设置消息为已读
     * @param params
     */
    void ydxx(Map<String, Object> params);

    /**
     * 删除消息
     * @param params
     */
    void scxx(Map<String, Object> params);

}