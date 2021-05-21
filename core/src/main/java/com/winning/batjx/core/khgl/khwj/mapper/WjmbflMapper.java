package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.WjmbflDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface WjmbflMapper {

    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    List<WjmbflDO>  queryByMbid(@Param("mbid") int mbid);

    Integer  queryMaxSxByMbid(@Param("mbid") int mbid);
    /**
     * 新增
     * @param wjmbflDOP
     */
    void saveOne(@Param("bean") WjmbflDO wjmbflDOP);

    /*
      复制
      */
    void copyOne(@Param("bean") WjmbflDO wjmbflDOP);

    /**
     * 更新
     * @param wjmbflDOP
     */
    void updateOne(@Param("bean") WjmbflDO wjmbflDOP);

    /**
     * 删除
     * @param wjmbflDOP
     */
    void deleteOne(@Param("bean") WjmbflDO wjmbflDOP);
}
