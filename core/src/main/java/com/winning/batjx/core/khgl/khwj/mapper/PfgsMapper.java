package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.PfgsDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface PfgsMapper {

    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    List<PfgsDO>  queryByFltmgl(@Param("fltmglid") int fltmglid);
    /**
     * 新增
     * @param PfgsDOP
     */
    void saveOne(@Param("bean") PfgsDO PfgsDOP);
    /*复制  */
    void copyOne(@Param("bean") PfgsDO PfgsDOP);
    /**
     * 批量新增
     * @param list
     */
    void saveList(@Param("list")List<PfgsDO> list);


    /**
     * 更新
     * @param PfgsDOP
     */
    void updateOne(@Param("bean") PfgsDO PfgsDOP);

    /**
     * 批量更新
     * @param list
     */
    void updateList(@Param("list") List<PfgsDO> list);

    /**
     * 删除
     * @param PfgsDOP
     */
    void deleteOne(@Param("bean") PfgsDO PfgsDOP);

    /**
     * 根据分类题目管理删除评分
     * @param fltmglid
     */
    void deleteByFltmgl(@Param("fltmglid") int fltmglid);
}
