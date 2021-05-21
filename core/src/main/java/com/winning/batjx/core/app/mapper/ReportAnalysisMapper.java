package com.winning.batjx.core.app.mapper;

import com.winning.batjx.core.app.domain.QyjxKhhzVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-29 14:11
 **/
public interface ReportAnalysisMapper {

    List<HashMap> queryKhfaList(Map<String, Object> params);

    QyjxKhhzVo queryKhdf(Map<String, Object> params);

    List<QyjxKhhzVo>  queryKhdxDfmx(Map<String, Object> params);

    List<QyjxKhhzVo>  queryKhhzList(Map<String, Object> params);

    QyjxKhhzVo  queryKhdfYjkh(Map<String, Object> params);

    List<QyjxKhhzVo>  queryKhdxDfmxYjkh(Map<String, Object> params);

    List<QyjxKhhzVo>  queryKhhzListYjkh(Map<String, Object> params);

    Integer getKhdxlx(Map<String, Object> params);

}
