package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.WjmbDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface WjmbMapper {

    WjmbDO queryByid(@Param("id")int id);
    /**
     *
     * @param wjmbDOP
     * @return
     */

    List<WjmbDO>  queryList(@Param("bean")WjmbDO wjmbDOP);
    /**
     * 新增
     * @param wjmbDOP
     */
    void saveOne(@Param("bean")WjmbDO wjmbDOP);
    /*
         复制
         */
    void copyOne(@Param("bean") WjmbDO wjmbDOP);
    /**
     * 更新
     * @param wjmbDOP
     */
    void updateOne(@Param("bean")WjmbDO wjmbDOP);

    /**
     * 更新
     * @param wjmbDOP
     */
    void statuOne(@Param("bean")WjmbDO wjmbDOP);
    /**
     * 删除
     * @param wjmbDOP
     */
    void deleteOne(@Param("bean")WjmbDO wjmbDOP);

    Integer queryMbsl();
    Integer queryMbslByMbbh(@Param("bean")WjmbDO wjmbDOP);
}
