package com.winning.batjx.core.khgl.khwj.service.impl;

import com.winning.batjx.core.khgl.khwj.mapper.CommonMapper;
import com.winning.batjx.core.khgl.khwj.service.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-04-14 13:03
 **/
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonMapper commonMapper;

    /**
     * 根据参数编码获取获取参数值
     *
     * @param csbh 参数编码
     * @param jgbm 机构编码
     * @return
     * @throws Exception
     */
    @Override
    public String getCssz(String csbh, String jgbm) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("csbh", csbh);
        params.put("jgbm", jgbm);
        List<String> getCssz =commonMapper.getCssz(params);
        if (CollectionUtils.isEmpty(getCssz)) {
            return "";
        } else {
            return getCssz.get(0);
        }
    }

    /***
     * @Description: 判断当前登录人员所在机构是指定方案的上级还是下级
     * @Param: String faid
     * @return: boolean
     * @Author: yyh@winning.com.cn
     * @Date: 2019/4/25 1:19 PM
     * @param map (faid  ,jgbm )
     */
    @Override
    public boolean isSuperior(Map map) throws Exception {

        boolean isSuperior=false;
        List<Map<String,Object>> list=commonMapper.queryParentJgList(map);
        if(list!=null&&list.size()>0){
            isSuperior=true;
        }
        return isSuperior;
    }
}
