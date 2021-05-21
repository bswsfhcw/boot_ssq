package com.winning.batjx.core.khgl.khwj.service.impl;

import com.winning.batjx.core.common.constant.ActionConst;
import com.winning.batjx.core.khgl.khwj.domain.FltmglDO;
import com.winning.batjx.core.khgl.khwj.domain.WjmbflDO;
import com.winning.batjx.core.khgl.khwj.mapper.WjmbflMapper;
import com.winning.batjx.core.khgl.khwj.service.WjmbflService;
import com.winning.batjx.core.khgl.khwj.mapper.FltmglMapper;
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
@Service("wjmbflService")
public class WjmbflServiceImpl implements WjmbflService {

    @Resource
    private WjmbflMapper wjmbflMapper;

    @Resource
    private FltmglMapper fltmglMapper;

    @Override
    public List<WjmbflDO>  getWjmbflDO(int mbid) throws Exception {
        return wjmbflMapper.queryByMbid(mbid);
    }

    @Override
    public List<WjmbflDO>  getWjmbjsyz(int id) throws Exception {
        List<WjmbflDO>  wjmbflDOs = wjmbflMapper.queryByMbid(id);//分类
        for (WjmbflDO wjmbflDO:wjmbflDOs){
            List<FltmglDO>  fltmglDOs = fltmglMapper.queryByMbfl(wjmbflDO.getId(),"3");//分类下的题目(填空题)
            wjmbflDO.setFltmglDOs(fltmglDOs);
        }
        return wjmbflDOs;
    }

    @Override
    public WjmbflDO getWjmbflsx(int mbid) throws Exception {
        WjmbflDO wjmbflDO = new WjmbflDO();
        wjmbflDO.setMbid(mbid);
        wjmbflDO.setSfyfj("0");
        int maxSx=wjmbflMapper.queryMaxSxByMbid(mbid);
        wjmbflDO.setMbflsx(maxSx+1);
        return  wjmbflDO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWjmbfl(WjmbflDO wjmbflDOP) throws Exception {
        String czlx = wjmbflDOP.getCzlx();
        //判断状态
        if (wjmbflDOP !=null) {
            switch (czlx) {
                case ActionConst.ACTION_ADD:
                    wjmbflMapper.saveOne(wjmbflDOP);
                    break;
                case ActionConst.ACTION_UPDATE:
                    wjmbflMapper.updateOne(wjmbflDOP);
                    break;
                case ActionConst.ACTION_DELETE:
                    wjmbflMapper.deleteOne(wjmbflDOP);
                    break;
                default:
                    break;
            }
        }
    }
}
