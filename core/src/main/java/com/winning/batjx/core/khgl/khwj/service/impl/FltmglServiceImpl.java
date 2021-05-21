package com.winning.batjx.core.khgl.khwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winning.batjx.core.common.constant.ActionConst;
import com.winning.batjx.core.khgl.khwj.domain.FltmglDO;
import com.winning.batjx.core.khgl.khwj.domain.WjmbflDO;
import com.winning.batjx.core.khgl.khwj.mapper.FltmglMapper;
import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.batjx.core.common.util.PageUtils;
import com.winning.batjx.core.khgl.khwj.service.FltmglService;
import com.winning.jbase.common.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2021/1/29
 */
@Service("fltmglService")
public class FltmglServiceImpl implements FltmglService {

    @Resource
    private FltmglMapper fltmglMapper;

    @Override
    public PageResult getFltmglDO(int mbid,String tmmc, PageRequest pageRequest) throws Exception {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        PageHelper.startPage(pageNum, pageSize);
        //注意分页工具只对PageHelper下的第一个select语句生效
        List<FltmglDO> list = fltmglMapper.queryByMb(mbid,tmmc);
        return PageUtils.getPageResult(new PageInfo<FltmglDO>(list));
    }

    @Override
    public List<FltmglDO>  getFltmglDO(int mbflid) throws Exception {
        return fltmglMapper.queryByMbfl(mbflid,"ALL");
    }

    @Override
    public  PageResult  queryAllTm(Map<String,Object> params) throws Exception {
        int pageNum = (Integer) params.get("pageNum");
        int pageSize = (Integer) params.get("pageSize");
        //TODO 登录信息放入sql查询
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        PageHelper.startPage(pageNum, pageSize);
        //注意分页工具只对PageHelper下的第一个select语句生效
        List<FltmglDO> list = fltmglMapper.queryAllTm(params);
        return PageUtils.getPageResult(new PageInfo<>(list));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFltmgl(int mbid,FltmglDO fltmglDOP) throws Exception {
        String czlx = fltmglDOP.getCzlx();
        //判断状态
        if (fltmglDOP !=null) {
            switch (czlx) {
                case ActionConst.ACTION_ADD:
                    //判断该题目是否在其他分类下了
                    WjmbflDO wjmbflDO_exist = fltmglMapper.queryMbflByMbAndTmNotfl(mbid,fltmglDOP.getMbflid(),fltmglDOP.getTmid());
                    if(wjmbflDO_exist  != null){
                        throw new ApiException("题目代码"+fltmglDOP.getTmbm()+"在"+wjmbflDO_exist.getMbflmc()+"分类已存在，不能重复添加");
                    }
                    fltmglMapper.saveOne(fltmglDOP);
                    break;
                case ActionConst.ACTION_UPDATE:
                    fltmglMapper.updateOne(fltmglDOP);
                    break;
                case ActionConst.ACTION_DELETE:
                    fltmglMapper.deleteOne(fltmglDOP);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 只有新增 其他预留
     * @param mbid
     * @param mbflid
     * @param fltmglDOPs
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFltmgls(int mbid,int mbflid,List<FltmglDO> fltmglDOPs) throws Exception {
        String czlx;
        String tmlx="1";
        List<FltmglDO> fltmglDOPsAdd = new ArrayList<>();
        List<FltmglDO> fltmglDOPsUpdate = new ArrayList<>();
        List<FltmglDO> fltmglDOPsDelete = new ArrayList<>();
        if (fltmglDOPs !=null && fltmglDOPs.size()>0) {
            int maxSx=fltmglMapper.queryMaxSxByMbflid(mbflid);//自动自增顺序字段
            for (int i = 0; i <fltmglDOPs.size() ; i++) {
                FltmglDO fltmglDOP = JSONObject.parseObject(JSONObject.toJSONString(fltmglDOPs.get(i)), FltmglDO.class);
                //默认单选
                fltmglDOP.setTmdaly("0");
                fltmglDOP.setTkzsfwdf("0");
                fltmglDOP.setTmsffs("1");
                czlx = fltmglDOP.getId()==0?ActionConst.ACTION_ADD:ActionConst.ACTION_UPDATE;
                tmlx = fltmglDOP.getTmlx();
                switch (tmlx) {
                    case "1":
                        break;
                    case "2":
                        fltmglDOP.setTmsffs("2");
                        break;
                    case "3"://填空题
                        fltmglDOP.setTkzsfwdf("1");
                        fltmglDOP.setTmsffs("3");
                        break;
                    default:
                        break;
                }
                switch (czlx) {
                    case ActionConst.ACTION_ADD:
                        WjmbflDO wjmbflDO_exist = fltmglMapper.queryMbflByMbAndTmNotfl(mbid,fltmglDOP.getMbflid(),fltmglDOP.getTmid());
                        if(wjmbflDO_exist  != null){
                            throw new ApiException("题目代码"+fltmglDOP.getTmbm()+"在"+wjmbflDO_exist.getMbflmc()+"分类已存在，不能重复添加");
                        }
                        fltmglDOP.setTmsx(maxSx+1);
                        maxSx++;
                        fltmglDOPsAdd.add(fltmglDOP);
                        break;
                    case ActionConst.ACTION_UPDATE:
                        fltmglDOPsUpdate.add(fltmglDOP);
                        break;
                    case ActionConst.ACTION_DELETE:// 这里预留理论上走不到这个
                        fltmglDOPsDelete.add(fltmglDOP);
                        break;
                    default:
                        break;
                }
            }
        }
//        if(fltmglDOPsDelete !=null && fltmglDOPsDelete.size()>0){
//            fltmglMapper.deleteList(fltmglDOPsDelete);
//        }
        //如果有更新的 先删除除了需要更新的，否则先删除后增加
//        fltmglMapper.deleteListNot(mbflid,fltmglDOPsUpdate);
//        if(fltmglDOPsUpdate !=null && fltmglDOPsUpdate.size()>0){
//            fltmglMapper.updateList(fltmglDOPsUpdate);
//        }
        if(fltmglDOPsAdd !=null && fltmglDOPsAdd.size()>0){
            fltmglMapper.saveList(fltmglDOPsAdd);
        }
    }
}
