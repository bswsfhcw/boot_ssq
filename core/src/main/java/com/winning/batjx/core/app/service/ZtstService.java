package com.winning.batjx.core.app.service;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2019/6/6.
 */
public interface ZtstService {

    List<QyjxKhhzVo> queryBizstByNd(Map<String, Object> map) throws Exception;

    List<QyjxKhhzVo> queryBizstYgtFaByNd(Map<String, Object> map) throws Exception;

    HashMap<String, Object> queryCurLoginUserJgdjByRygh(Map<String, Object> map) throws Exception;

    List<HashMap<String, Object>> queryCurLoginUserIsYgtByJgbm(Map<String, Object> map) throws Exception;

    List<HashMap<String, Object>> queryBizstFaqjByFaid(Map<String, Object> map) throws Exception;

    List<HashMap<String, Object>> zblbTree(Map<String, Object> params) throws Exception;

    String saveBizstZbpz(Map<String, Object> map) throws Exception;

    List<Map<String,Object>> queryBizstZdzbTable(Map map) throws Exception;

    List<HashMap<String, Object>> calculBizstZdzbKp(Map<String, Object> params) throws Exception;

    String calculBizstZdzbTop5(Map<String, Object> params) throws Exception;

    String bizstKhjgfxGraph(Map<String, Object> params) throws Exception;

    List<HashMap<String, Object>> queryCurFafllx(Map<String, Object> map) throws Exception;

    String bizstPmtop10(Map<String, Object> params) throws Exception;

    String bizstJxfxGraph(Map<String, Object> params) throws Exception;

    String bizstJxqsGraph(Map<String, Object> params) throws Exception;



}
