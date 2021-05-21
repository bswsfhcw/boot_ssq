package com.winning.batjx.core.app.mapper;

import com.winning.batjx.core.khgl.khwj.vo.QyjxKhhzVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2021/4/6.
 */
public interface ZtstMapper {

    /**
     * 根据年份查询方案（常规方案）
      * @param map
     * @return
     */
    List<QyjxKhhzVo> queryBizstByNd(Map<String,Object> map) throws Exception;

    /**
     * 根据年份查询方案（区县下医共体建立的医共体方案）
     * @param map
     * @return
     * @throws Exception
     */
    List<QyjxKhhzVo> queryBizstYgtFaByNd(Map<String, Object> map) throws Exception;

    /**
     * 判断当前登录人 是 县级机构负责人 、 医共体领头单位负责人 、 机构负责人
     * @param map
     * @return
     * @throws Exception
     */
    HashMap<String, Object> queryCurLoginUserJgdjByRygh(Map<String, Object> map) throws Exception;

    /**
     * 判断当前登录人所在的机构是否为【医共体】，有记录就是医共体 , 浦江二院 是医共体 但是下边没有挂社区中心
     * @param map
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryCurLoginUserIsYgtByJgbm(Map<String, Object> map) throws Exception;

    /**
     * 根据方案id 查询方案的考核期间
     * @param map
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstFaqjByFaid(Map<String, Object> map) throws Exception;


    /**
     * 重点指标设置弹出框 - 右侧指标树
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> zblbTree(Map<String, Object> params) throws Exception;


    /**
     * 保存重点指标 以及  TOP5 配置的指标
     * @param params
     * @return
     * @throws Exception
     */
    boolean saveBizstZbpz(Map<String, Object> params) throws Exception;

    /**
     * 根据方案ID 删除 该方案配置的指标项
     * @param params
     * @return
     * @throws Exception
     */
    boolean delBizstZbpz(Map<String, Object> params) throws Exception;


    /**
     * 根据方案ID 查询指标配置信息
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstZbpzByParams(Map<String, Object> params) throws Exception;

    /**
     * 重点指标卡片的展示
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstZdzbKp(Map<String, Object> params) throws Exception;

    /**
     * 重点指标TOP5 部分
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstZdzbTop5Nr(Map<String, Object> params) throws Exception;

    /**
     * 重点指标TOP5 部分
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstZdzbTop5(Map<String, Object> params) throws Exception;


    /**
     * 根据方案的ID 获取 方案下的分类类型
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryCurFafllx(Map<String, Object> params) throws Exception;

    /**
     * 机构分析子视图
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstKhjgfxGraph(Map<String, Object> params) throws Exception;


    /**
     * TOP10 柱状图部分
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstPmtop10(Map<String, Object> params) throws Exception;

    /**
     * 绩效分析 子视图
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstJxfxGraph(Map<String, Object> params) throws Exception;

    /**
     * 绩效趋势 子视图
     * @param params
     * @return
     * @throws Exception
     */
    List<HashMap<String, Object>> queryBizstJxqsGraph(Map<String, Object> params) throws Exception;


    /**
     * 查询方案 信息
     * @param map
     * @return
     * @throws Exception
     */
    HashMap<String, Object> queryCurFaxx(Map<String, Object> map) throws Exception;

}
