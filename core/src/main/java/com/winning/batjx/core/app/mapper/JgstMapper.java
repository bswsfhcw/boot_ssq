package com.winning.batjx.core.app.mapper;

import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzXxVo;

import java.util.List;
import java.util.Map;

/**
 * 机构视图、单元视图
 * Created by thinkpad on 2021/4/6.
 */
public interface JgstMapper {

    List<Map<String, Object>> selectAllByJgbm(Map<String, Object> params) throws Exception;

    /**
     * 指标树
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectZb(Map<String,Object> map) throws Exception;

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<QyjxKhhzXxVo> selectKhhzxxByFaidAndKhqjForIndividualOrJg(Map<String, Object> map) throws Exception;

    /**
     * 绩效趋势
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryJxqsGraphForIndividualOrJg(Map<String, Object> params) throws Exception;

    /**
     * 工作量与工作质量
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryGzlOrGzzltop10ForIndividualOrJg(Map<String, Object> params) throws Exception;

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>>  queryJxfaList(Map<String, Object> map) throws Exception;

    /**
     * 查询已经归档(已经经过评价计算)的那个考核方案对应的区间
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getKhzqList(Map<String, Object> map) throws Exception;

    /**
     * 查询考核对象
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getKhdxList(Map<String, Object> map) throws Exception;

    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getKhhzList(Map<String, Object> map) throws Exception;

}
