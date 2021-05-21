package com.winning.batjx.core.app.service;

import com.winning.batjx.core.app.domain.QyjxKhhzVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-29 13:51
 **/
public interface ReportAnalysisService {


    /**
     * 查询考核方案
     *
     * **/
    List<HashMap> queryKhfa(Map<String, Object> params) ;

    /**
     * 查询汇总得分
     * */

    QyjxKhhzVo getKhdfHz(Map<String, Object> params);


    /**
     * 查询机构得分明细
     *
     * */
    List<QyjxKhhzVo> getKhdxDfmx(Map<String, Object> params);

    /**
     * 机构考核指标得分
     *
     * */
    List<QyjxKhhzVo> getKhdfMxList(Map<String, Object> params);

}
