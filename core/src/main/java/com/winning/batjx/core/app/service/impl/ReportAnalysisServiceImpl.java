package com.winning.batjx.core.app.service.impl;

import com.winning.batjx.core.app.mapper.ReportAnalysisMapper;
import com.winning.batjx.core.app.service.ReportAnalysisService;
import com.winning.batjx.core.app.domain.QyjxKhhzVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-29 14:08
 **/

@Service
public class ReportAnalysisServiceImpl implements ReportAnalysisService {

    @Resource
    private ReportAnalysisMapper gzzlAppMapper;


    @Override
    public List<HashMap> queryKhfa(Map<String, Object> params) {
        return gzzlAppMapper.queryKhfaList(params);
    }

    @Override
    public QyjxKhhzVo getKhdfHz(Map<String, Object> params) {
        //查询考核类型
        int countkhdxlx=gzzlAppMapper.getKhdxlx(params);
        /**
         * 区级考核机构
         * **/
        QyjxKhhzVo qyjxKhhzVo;
        if(countkhdxlx>0){
            qyjxKhhzVo= gzzlAppMapper.queryKhdf(params);
        }else{
            qyjxKhhzVo=gzzlAppMapper.queryKhdfYjkh(params);
        }
        return qyjxKhhzVo;
    }


    @Override
    public List<QyjxKhhzVo> getKhdxDfmx(Map<String, Object> params) {

        //查询考核类型
        int countkhdxlx=gzzlAppMapper.getKhdxlx(params);

        List<QyjxKhhzVo> resultList;
        if(countkhdxlx>0){
            resultList=gzzlAppMapper.queryKhdxDfmx(params);
        }else{
            resultList=gzzlAppMapper.queryKhdxDfmxYjkh(params);
        }
        return resultList;
    }

    @Override
    public List<QyjxKhhzVo> getKhdfMxList(Map<String, Object> params) {

        //查询考核类型
        int countkhdxlx=gzzlAppMapper.getKhdxlx(params);
        List<QyjxKhhzVo> resultList;
        if(countkhdxlx>0){
            resultList=gzzlAppMapper.queryKhhzList(params);
        }else{
            resultList=gzzlAppMapper.queryKhhzListYjkh(params);
        }
        return resultList;
    }



}
