package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.WjmbDO;
import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;

import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface WjmbService {

    WjmbDO getWjmbbh() throws Exception;
    WjmbDO  getWjmbYl(int id) throws Exception;
    WjmbDO  fzWjmb(int idOld) throws Exception;

    /***
     * @Description:
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    PageResult getWjmbDO(WjmbDO wjmbDOP, PageRequest pageRequest) throws Exception;
    List<WjmbDO>  getWjmbDO(WjmbDO wjmbDOP) throws Exception;

    void saveWjmb(WjmbDO wjmbDOP) throws Exception;

}
