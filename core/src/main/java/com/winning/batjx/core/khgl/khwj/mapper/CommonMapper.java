package com.winning.batjx.core.khgl.khwj.mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-04-14 13:04
 **/
public interface CommonMapper {

    /**
     * 根据参数编码获取获取参数值
     *
     * @param map 机构编码和csbh
     * @return
     * @throws Exception
     */
    public List<String> getCssz(Map map) throws Exception;

    List<Map<String, Object>> queryParentJgList(Map map);
}
