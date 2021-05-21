package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.TmDO;
import com.winning.batjx.core.common.util.PageResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 题库管理
 * Created by thinkpad on 2021/3/3.
 */
public interface TkglService {

    PageResult getTmDOList(Map<String,Object> params) throws Exception;

    Map<String,Object> createCurTmbh(Map<String,Object> params) throws Exception;

    void saveTmDO(TmDO tmDO, List<Map<String,Object>> tmxx) throws Exception;

    TmDO getTmDetail(Map<String,Object> params ) throws Exception;

    Map<String,Object> saveTmExcel(Map<String, Object> map) throws Exception;

    void delTmByParams(Map<String,Object> params ) throws Exception;

    void saveUploadExclTmDO(Collection<Map> importExcel) throws Exception;

}
