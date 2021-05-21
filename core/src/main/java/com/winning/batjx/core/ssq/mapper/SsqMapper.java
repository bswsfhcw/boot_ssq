package com.winning.batjx.core.ssq.mapper;

import com.winning.batjx.core.ssq.vo.SsqVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface SsqMapper {

    /**
     *
     * @param params
     * @return
     */

    List<SsqVo>  queryList(Map<String,Object> params);
    List<Map>  lstj(Map<String,Object> params);
    /**
     * 删除
     * @param ssqVo
     */
    void deleteOne(@Param("bean") SsqVo ssqVo);
    /**
     * 新增
     * @param ssqVo
     */
    void saveOne(@Param("bean") SsqVo ssqVo);


    /**
     * 批量删除
     * @param list
     */
    void deleteList(@Param("list") List<SsqVo> list);


    /**
     * 批量新增
     * @param list
     */
    void saveList(@Param("list") List<SsqVo> list);
}
