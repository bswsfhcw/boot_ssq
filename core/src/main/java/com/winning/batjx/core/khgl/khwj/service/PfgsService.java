package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.PfgsDO;

import java.util.List;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface PfgsService {

    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */

    List<PfgsDO>  getPfgsDO(int fltmglid) throws Exception;

    List<PfgsDO>  savePfgss(int fltmglid, List<PfgsDO> pfgsDOPs) throws Exception;

}
