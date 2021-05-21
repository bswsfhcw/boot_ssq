package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.WjmbflDO;

import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface WjmbflService {

    /***
     * @Description: 
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */

    List<WjmbflDO>  getWjmbflDO(int mbid) throws Exception;

    List<WjmbflDO>  getWjmbjsyz(int mbid) throws Exception;

    WjmbflDO   getWjmbflsx(int mbid) throws Exception;

    void saveWjmbfl(WjmbflDO wjmbflDOP) throws Exception;

}
