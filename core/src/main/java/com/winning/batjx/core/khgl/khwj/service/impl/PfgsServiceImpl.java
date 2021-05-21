package com.winning.batjx.core.khgl.khwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.winning.batjx.core.khgl.khwj.domain.PfgsDO;
import com.winning.batjx.core.khgl.khwj.mapper.PfgsMapper;
import com.winning.batjx.core.khgl.khwj.service.PfgsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
@Service("pfgsService")
public class PfgsServiceImpl implements PfgsService {

    @Resource
    private PfgsMapper pfgsMapper;

    @Override
    public List<PfgsDO>  getPfgsDO(int fltmglid) throws Exception {
        return pfgsMapper.queryByFltmgl(fltmglid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<PfgsDO>  savePfgss(int fltmglid,List<PfgsDO> pfgsDOPs) throws Exception {
        pfgsMapper.deleteByFltmgl(fltmglid);
        List<PfgsDO> pfgsDOPsAdd = new ArrayList<>();
        for (int i = 0; i <pfgsDOPs.size() ; i++) {
            PfgsDO pfgsDO = JSONObject.parseObject(JSONObject.toJSONString(pfgsDOPs.get(i)), PfgsDO.class);
            pfgsDO.setFltmglid(fltmglid);
            pfgsDO.setXh(i+1);
            pfgsDOPsAdd.add(pfgsDO);
        }
        if(pfgsDOPsAdd.size() > 0){
            pfgsMapper.saveList(pfgsDOPsAdd);
        }
        return pfgsMapper.queryByFltmgl(fltmglid);
    }
}
