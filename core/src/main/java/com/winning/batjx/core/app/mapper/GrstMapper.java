package com.winning.batjx.core.app.mapper;

import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;
import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzXxVo;

import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2021/4/6.
 */
public interface GrstMapper {
    /**
     * 根据方案id、考核区间查询指标信息 对于机构，khdxbm=登录人所在机构;   对于个人，khdxbm=登录人id
     * @param map
     * @return
     * @throws Exception
     */
    List<QyjxKhhzXxVo> selectKhhzxxByFaidAndKhqjForIndividualOrJg(Map<String, Object> map) throws Exception;

    /**
     * 因为可能存在既考核到机构(考核到机构下属人员)又考核到个人   实际上可能并不存在
     * @param map
     * @return
     * @throws Exception
     */
    List<QyjxKhhzXxVo> selectKhhzxxByFaidAndKhqj(Map<String, Object> map) throws Exception;

    /**
     * 指标树
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectZb(Map<String,Object> map) throws Exception;

    /**
     * 绩效趋势
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryJxqsGraph(Map<String, Object> params) throws Exception;

    /**
     * 绩效趋势
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryJxqsGraphForIndividualOrJg(Map<String, Object> params) throws Exception;

    /**
     * 根据方案的ID 获取 方案下的分类类型
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryFafllx(Map<String, Object> params) throws Exception;

    /**
     * 绩效分析 考核到个人、机构
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryJxfxGraphForIndividualOrJg(Map<String, Object> params) throws Exception;

    /**
     * 绩效分析
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryJxfxGraph(Map<String, Object> params) throws Exception;

    /**
     * 工作量与工作质量 考核到个人、机构
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryGzlOrGzzltop10ForIndividualOrJg(Map<String, Object> params) throws Exception;

    /**
     * 工作量与工作质量
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryGzlOrGzzltop10(Map<String, Object> params) throws Exception;

    /**
     * 查询机构(科室)下面的所有的医生考核信息
     * @param params
     * @return
     * @throws Exception
     */
    List<QyjxKhhzVo> selectAllByJgbm(Map<String, Object> params) throws Exception;

    /**
     * 查询方案下面的所有的考核信息   机构视图专用
     * @param params
     * @return
     * @throws Exception
     */
    List<QyjxKhhzVo> selectAllByJgbmForIndividualOrJg(Map<String, Object> params) throws Exception;

    /**
     * 查询考核周期(季度、月度、年、半年)
     * @param map
     * @return
     */
    List<Map<String,Object>> queryJxkhplList(Map<String, Object> map);

    /**
     * 查询考核方案
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String,Object>>  queryJxfaList(Map<String, Object> map) throws Exception;

    /**
     * 根据方案ID 删除 该方案配置的指标项
     * @param params
     * @return
     * @throws Exception
     */
    boolean delZdzb(Map<String, Object> params) throws Exception;

    /**
     * 保存重点指标
     * @param params
     * @return
     * @throws Exception
     */
    boolean saveZdzb(Map<String, Object> params) throws Exception;

    /**
     * 根据方案ID 查询指标配置信息
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> queryZdzbByParams(Map<String, Object> params) throws Exception;

    /**
     * 查询当前登录用户的科室编码(对于考核对象是0123这些，科室编码就是jgbm)
     * @param ryid
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectKsbmByRyid(String ryid) throws Exception;

    /**
     * 查询科室下面的所有的医生考核信息 个人视图用(因为存在直接考核到个人而不是考核到科室下面的个人)
     * @param params
     * @return
     * @throws Exception
     */
    List<QyjxKhhzVo> selectAllByJgbmForIndividual(Map<String, Object> params) throws Exception;

}
