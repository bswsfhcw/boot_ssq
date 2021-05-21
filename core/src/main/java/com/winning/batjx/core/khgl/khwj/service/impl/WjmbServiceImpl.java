package com.winning.batjx.core.khgl.khwj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winning.batjx.core.common.constant.ActionConst;
import com.winning.batjx.core.khgl.khwj.domain.*;
import com.winning.batjx.core.khgl.khwj.mapper.*;
import com.winning.batjx.core.khgl.khwj.service.WjmbService;
import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.batjx.core.common.util.PageUtils;
import com.winning.jbase.common.exception.ApiException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
@Service("wjmbService")
public class WjmbServiceImpl implements WjmbService {

    @Resource
    private WjmbMapper wjmbMapper;

    @Resource
    private WjmbflMapper wjmbflMapper;

    @Resource
    private FltmglMapper fltmglMapper;

    @Resource
    private XxfszMapper xxfszMapper;

    @Resource
    private PfgsMapper pfgsMapper;
    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public WjmbDO getWjmbYl(int id) throws Exception {
        WjmbDO wjmbDO = wjmbMapper.queryByid(id);//模板
        if(wjmbDO !=null){
            List<WjmbflDO>  wjmbflDOs = wjmbflMapper.queryByMbid(id);//分类
            for (WjmbflDO wjmbflDO:wjmbflDOs){
                List<FltmglDO>  fltmglDOs = fltmglMapper.queryByMbfl(wjmbflDO.getId(),"ALL");//分类下的题目
                for (FltmglDO fltmglDO:fltmglDOs){
                    List<XxfszDO> xxfszDOs = xxfszMapper.queryByFltmgl(fltmglDO.getId());//题目的选项
                    fltmglDO.setXxfszDOs(xxfszDOs);
                }
                wjmbflDO.setFltmglDOs(fltmglDOs);
            }
            wjmbDO.setWjmbflDOs(wjmbflDOs);
        }
        return wjmbDO;
    }

    @Override
    public WjmbDO getWjmbbh () throws Exception {
        WjmbDO wjmbDO = new WjmbDO();
        dealMbbh(wjmbDO);
        return  wjmbDO;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public WjmbDO fzWjmb(int idOld) throws Exception {
        WjmbDO wjmbDO_old = wjmbMapper.queryByid(idOld);
        WjmbDO wjmbDO_new = null;
        if(wjmbDO_old !=null){
            wjmbDO_new  = wjmbDO_old.clone();
//            SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
//            Date date=new Date();
//            String dateStr= sdf1.format(date);
//            wjmbDO_new.setMbbh("mb"+dateStr+"");//生成一个唯一的编号？
            dealMbbh(wjmbDO_new);
            wjmbDO_new.setCjsj(new Date());
            wjmbDO_new.setGxsj(new Date());
            wjmbDO_new.setMbmc(wjmbDO_new.getMbmc()+"-副本");
            wjmbMapper.copyOne(wjmbDO_new);//复制模板
            List<WjmbflDO>  wjmbflDOs_old = wjmbflMapper.queryByMbid(idOld);//分类
            WjmbflDO wjmbflDO_new = null;
            for (WjmbflDO wjmbflDO_old:wjmbflDOs_old){
                wjmbflDO_new = wjmbflDO_old.clone();
                wjmbflDO_new.setMbid(wjmbDO_new.getId());
                wjmbflMapper.copyOne(wjmbflDO_new);//复制分类
                List<FltmglDO>  fltmglDOs_old = fltmglMapper.queryByMbfl(wjmbflDO_old.getId(),"ALL");//分类下的题目
                FltmglDO fltmglDO_new = null;
                for (FltmglDO fltmglDO_old:fltmglDOs_old){
                    fltmglDO_new = fltmglDO_old.clone();
                    fltmglDO_new.setMbflid(wjmbflDO_new.getId());//取新的分类id
                    fltmglMapper.copyOne(fltmglDO_new);//复制题目
                    List<XxfszDO> xxfszDOs_old = xxfszMapper.queryByFltmgl(fltmglDO_old.getId());//题目的选项
                    XxfszDO xxfszDOs_new = null;
                    for (XxfszDO xxfszDO_old:xxfszDOs_old){
                        xxfszDOs_new = xxfszDO_old.clone();
                        xxfszDOs_new.setFltmglid (fltmglDO_new.getId());//取新的题目
                        xxfszMapper.copyOne(xxfszDOs_new);//复制题目选项
                    }
                    List<PfgsDO> pfgsDOs_old = pfgsMapper.queryByFltmgl(fltmglDO_old.getId());//评分设置
                    PfgsDO pfgsDOs_new = null;
                    for (PfgsDO pfgsDO_old:pfgsDOs_old){
                        pfgsDOs_new = pfgsDO_old.clone();
                        pfgsDOs_new.setFltmglid (fltmglDO_new.getId());//取新的题目
                        pfgsMapper.copyOne(pfgsDOs_new);//复制题目选项
                    }
                }
            }
        }
        return wjmbDO_new;
    }

    @Override
    public PageResult getWjmbDO(WjmbDO wjmbDOP, PageRequest pageRequest) throws Exception {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        PageHelper.startPage(pageNum, pageSize);
        //注意分页工具只对PageHelper下的第一个select语句生效
        List<WjmbDO> list = wjmbMapper.queryList(wjmbDOP);
        return PageUtils.getPageResult(new PageInfo<WjmbDO>(list));
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<WjmbDO>  getWjmbDO(WjmbDO wjmbDOP) throws Exception {
        return wjmbMapper.queryList(wjmbDOP);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWjmb(WjmbDO wjmbDOP) throws Exception {
        String czlx = wjmbDOP.getCzlx();
        //判断状态
        if (wjmbDOP !=null) {
//            wjmbDOP.setCjr(SessionUtil.getLoginUser().getUserName());
//            wjmbDOP.setGxr(SessionUtil.getLoginUser().getUserName());
            wjmbDOP.setCjsj(new Date());
            wjmbDOP.setGxsj(new Date());
            switch (czlx) {
                case ActionConst.ACTION_ADD:
                    checkMbbh(wjmbDOP);
                    wjmbMapper.saveOne(wjmbDOP);
                    break;
                case ActionConst.ACTION_UPDATE:
                    checkMbbh(wjmbDOP);
                    wjmbMapper.updateOne(wjmbDOP);
                    break;
                case ActionConst.ACTION_DELETE:
                    wjmbMapper.deleteOne(wjmbDOP);
                    break;
                case ActionConst.ACTION_STATU:
                    wjmbMapper.statuOne(wjmbDOP);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * mb20210305001
     * 模板编号生成规则
     * @param wjmbDOP
     */
    private void dealMbbh(WjmbDO wjmbDOP) {
        int mbsl=wjmbMapper.queryMbsl()+1;
        String mbbhHz=mbsl+"";//后缀
        if(mbsl<10){
            mbbhHz="00"+mbbhHz;
        }else if(mbsl<100){
            mbbhHz="0"+mbbhHz;
        }
        //创建SimpleDateFormat对象，指定样式    2019-05-13 22:39:30
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        //使用format()方法格式化Date对象为字符串，返回字符串
        String dateStr= sdf1.format(date);
        String mbbhNew = "mb"+dateStr+mbbhHz;
        wjmbDOP.setMbbh(mbbhNew);
    }
    private void checkMbbh(WjmbDO wjmbDOP) {
        if(StringUtils.isEmpty(wjmbDOP.getMbbh())){
            throw  new ApiException("模板编号不能为空");
        }
        //判断模板编号的唯一性
        int mbsl=wjmbMapper.queryMbslByMbbh(wjmbDOP);
        if(mbsl > 0){
            throw  new ApiException("模板编号："+wjmbDOP.getMbbh()+"已存在");
        }
    }
}
