package com.winning.batjx.core.khgl.khwj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winning.batjx.core.khgl.khwj.domain.WjmbDO;
import com.winning.batjx.core.khgl.khwj.service.MbdzService;
import com.winning.batjx.core.khgl.khwj.vo.MbzbdzVo;
import com.winning.batjx.core.common.domain.LoginUser;
import com.winning.batjx.core.khgl.khwj.domain.MbzbdzDO;
import com.winning.batjx.core.khgl.khwj.mapper.MbdzMapper;
import com.winning.batjx.core.app.service.QyjxService;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.batjx.core.common.util.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-02 14:38
 **/
@Service("mbdzService")
public class MbdzServiceImpl implements MbdzService {


    @Resource
   private MbdzMapper mbdzMapper;

    @Resource
    private QyjxService qyjxService;


    @Override
    public PageResult getZbdzList(Map<String, Object> params) {
        int pageNum = (Integer) params.get("pageNum");
        int pageSize = (Integer) params.get("pageSize");
        Map map=new HashMap();
        map.put("zbmc",(String)params.get("zbmc"));
        //设置查询参数
        map.put("rygh",(String )params.get("loginUserName"));
        LoginUser loginUser=qyjxService.queryLoginUser(map);
        if(loginUser!=null){
            map.put("jgbm",loginUser.getJgbm());
            map.put("zclsh",loginUser.getId());
        }
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        PageHelper.startPage(pageNum, pageSize);
        // 注意分页工具只对PageHelper下的第一个select语句生效
        List<MbzbdzVo> list = mbdzMapper.queryPageList(map);
        return PageUtils.getPageResult(new PageInfo<>(list));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMbdz(MbzbdzDO mbzbdzDO){
        mbzbdzDO.setWjmbbh(mbzbdzDO.getWjmbbh().trim());
        MbzbdzDO mbzbdzEntity=mbdzMapper.getMbdzByKey(mbzbdzDO);
        if (mbzbdzEntity==null){
            mbdzMapper.insertMbdz(mbzbdzDO);
        }else{
            mbzbdzDO.setId(mbzbdzEntity.getId());
            mbdzMapper.updateMbdz(mbzbdzDO);
        }
    }

    @Override
    public WjmbDO queryWjmbBymbbh(String mbbh) {
        return mbdzMapper.queryMbxx(mbbh);
    }
}
