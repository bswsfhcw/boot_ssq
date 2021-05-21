package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.XxfszDO;

import java.util.List;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface XxfszService {

    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */

    List<XxfszDO>  getXxfszDO(int  fltmglid) throws Exception;

    List<XxfszDO>  saveXxfszs(int fltmglid,List<XxfszDO> xxfszDOPs) throws Exception;

}
