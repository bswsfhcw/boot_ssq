package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.FltmglDO;
import com.winning.batjx.core.khgl.khwj.domain.WjmbflDO;
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
public interface FltmglMapper {

    List<FltmglDO>  queryByMbfl(@Param("mbflid") int mbflid, @Param("tmlx") String tmlx);
    List<FltmglDO>  queryAllTm(Map<String,Object> map);
    WjmbflDO queryMbflByMbAndTmNotfl(@Param("mbid") int mbid, @Param("mbflid") int mbflid, @Param("tmid") int tmid);
    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    List<FltmglDO>  queryByMb(@Param("mbid") int mbid,@Param("tmmc") String tmmc);

    Integer  queryMaxSxByMbflid(@Param("mbflid") int mbflid);

    /**
     * 新增
     * @param fltmglDO
     */
    void saveOne(@Param("bean") FltmglDO fltmglDO);
    /*复制  */
    void copyOne(@Param("bean") FltmglDO fltmglDO);

    /**
     * 批量新增
     * @param list
     */
    void saveList(@Param("list")List<FltmglDO> list);

    /**
     * 更新
     * @param fltmglDO
     */
    void updateOne(@Param("bean") FltmglDO fltmglDO);

    /**
     * 批量更新
     * @param list
     */
    void updateList(@Param("list") List<FltmglDO> list);

    /**
     * 删除
     * @param fltmglDO
     */
    void deleteOne(@Param("bean") FltmglDO fltmglDO);


    /**
     * 批量删除
     * @param list
     */
    void deleteList(@Param("list") List<FltmglDO> list);

    /**
     * 批量删除
     * @param list
     */
    void deleteListNot(@Param("mbflid") int mbflid,@Param("list") List<FltmglDO> list);
}
