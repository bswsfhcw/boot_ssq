package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.XxfszDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface XxfszMapper {

    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    List<XxfszDO>  queryByFltmgl(@Param("fltmglid") int fltmglid);
    /**
     * 新增
     * @param XxfszDOP
     */
    void saveOne(@Param("bean") XxfszDO XxfszDOP);
    /*复制  */
    void copyOne(@Param("bean") XxfszDO XxfszDOP);
    /**
        * 批量新增
     * @param list
     */
    void saveList(@Param("list")List<XxfszDO> list);

    /**
     * 更新
     * @param XxfszDOP
     */
    void updateOne(@Param("bean") XxfszDO XxfszDOP);
    /**
     * 批量更新
     * @param list
     */
    void updateList(@Param("list") List<XxfszDO> list);

    /**
     * 删除
     * @param XxfszDOP
     */
    void deleteOne(@Param("bean") XxfszDO XxfszDOP);

    /**
     * 根据分类题目管理删除选项分设置
     * @param fltmglid
     */
    void deleteByFltmgl(@Param("fltmglid") int fltmglid);
}
