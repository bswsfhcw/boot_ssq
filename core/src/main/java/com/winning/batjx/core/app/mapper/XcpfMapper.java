package com.winning.batjx.core.app.mapper;


import com.winning.batjx.core.khgl.khwj.domain.PicDO;

import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 、
 */

public interface XcpfMapper {
    /**
     * 查询方案列表
     *
     * @return
     */
    List<Map> queryKhfa(Map<String, Object> params);
    List<Map> queryKhdx(Map<String, Object> params);
    List<Map> queryKhzbZtList(Map<String, Object> params);
    List<Map> queryKhzbList(Map<String, Object> params);
    List<Map> queryKhzbPics(Map<String, Object> params);
    void updateSjtb(Map<String, Object> params);
    void updateWjgl(String tbid);
    void insertWjgl(PicDO wjgl);
}