package com.winning.batjx.core.khgl.khwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.winning.batjx.core.khgl.khwj.mapper.XxfszMapper;
import com.winning.batjx.core.khgl.khwj.service.XxfszService;
import com.winning.batjx.core.khgl.khwj.domain.XxfszDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
@Service("xxfszService")
public class XxfszServiceImpl implements XxfszService {

    @Resource
    private XxfszMapper xxfszMapper;

    @Override
    public List<XxfszDO>  getXxfszDO(int fltmglid) throws Exception {
        return xxfszMapper.queryByFltmgl(fltmglid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<XxfszDO> saveXxfszs(int fltmglid,List<XxfszDO> xxfszDOPs) throws Exception {
        xxfszMapper.deleteByFltmgl(fltmglid);
        for (int i = 0; i <xxfszDOPs.size() ; i++){
            XxfszDO xxfszDO = JSONObject.parseObject(JSONObject.toJSONString(xxfszDOPs.get(i)), XxfszDO.class);
            xxfszDO.setFltmglid(fltmglid);
        }
        xxfszMapper.saveList(xxfszDOPs);
        return  xxfszMapper.queryByFltmgl(fltmglid);
    }
}
