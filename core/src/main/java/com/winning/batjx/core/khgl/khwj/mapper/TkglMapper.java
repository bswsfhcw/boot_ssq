package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.TmxxDO;
import com.winning.batjx.core.khgl.khwj.domain.TmDO;

import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2021/3/2.
 */
public interface TkglMapper {
    /**
     *查询分页题目数据
     */
    List<TmDO> queryPageListTmDO(Map<String,Object> map);


    /**
     * 查询今天已经产生题目的数量
     * @param map
     * @return
     */
    Map<String,Object> queryTodayTmsl(Map<String,Object> map);

    /**
     * 查询题目主表信息
     * @param map
     * **/
    TmDO getTmDOByKey(Map<String,Object> map);

    /**
     * 查询题目选项子表信息
     * @param map
     * @return
     */
    List<TmxxDO> getTmxxDOByKey(Map<String,Object> map);


    /**
     * 新增
     * @param tmDO
     * **/
    Integer insertTmDO(TmDO tmDO);

    /**
     * 修改
     * @param tmDO
     * **/
    void updateTmDO(TmDO tmDO);

    /**
     * 删除主表信息
     * @param params
     * **/
    void delTmDO(Map<String,Object> params);

    /**
     * 删除从表信息
     * @param params
     * **/
    void delTmxxDO(Map<String,Object> params);


    /**
     * 新增选项
     * @param tmxxDO
     * **/
    void insertTmxxDO(TmxxDO tmxxDO);


}
