package com.winning.batjx.core.app.service;

import com.winning.batjx.core.khgl.khwj.domain.PicDTO;

import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/21
 */
public interface XcpfService {
    /**
     * 查询方案列表
     * @return
     */
    List<Map> queryKhfa(Map<String, Object> params);
    List<Map> queryKhdx(Map<String, Object> params);
    List<Map> queryKhzbZtList(Map<String, Object> params);
    List<Map> queryKhzbList(Map<String, Object> params);
    List<Map> queryKhzbPics(Map<String, Object> params);
    boolean uploadPics(PicDTO pic);

}
