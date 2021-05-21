package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.FltmglDO;
import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;

import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
public interface FltmglService {

    /***
     * @Description: 
     * @Param:
     * @return:
     * @Author: huchengwei
     * @Date: 2021/1/29
     */
    PageResult getFltmglDO(int mbid,String tmmc, PageRequest pageRequest) throws Exception;

    List<FltmglDO>  getFltmglDO(int mbflid) throws Exception;
    PageResult  queryAllTm(Map<String,Object> params) throws Exception;

    void saveFltmgl(int mbid,FltmglDO fltmglDOP) throws Exception;

    void saveFltmgls(int mbid,int mbflid,List<FltmglDO> fltmglDOPs) throws Exception;

}
