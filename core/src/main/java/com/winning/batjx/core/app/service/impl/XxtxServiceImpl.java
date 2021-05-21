package com.winning.batjx.core.app.service.impl;

import com.winning.batjx.core.app.mapper.XxtxMapper;
import com.winning.batjx.core.app.service.XxtxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/21
 */
@Service
public class XxtxServiceImpl implements XxtxService {
    /**
     * roleMapper object
     */
    @Resource
    private XxtxMapper xxtxMapper;

    @Override
    public Map<String,Object> queryXxtxList(Map<String,Object> params) {
        Map result = new HashMap();//最终返回
        Map zxWdSp = null,zxWdYj = null;//最新未读的一条消息
        List<Map> xxListDsp = new ArrayList<>();//待审批
        List<Map> xxListYsp = new ArrayList<>();//已审批
        List<Map> xxListSp = new ArrayList<>();//审批
        List<Map> xxListYj = new ArrayList<>();//预警
        List<Map> xxListWdSP = new ArrayList<>();//未读审批
        List<Map> xxListWdYj = new ArrayList<>();//未读预警
        List<Map> xxList = xxtxMapper.queryXxtxList(params);//取所有消息
        /**
         * xxlx （1审批消息2预警消息）
         * spzt （0待审批1已审批）
         * xxzt （0未读 1已读）
         */
        String xxlx,spzt,xxzt;
        for (Map<String,Object> xx:xxList) {
            xxlx=String.valueOf(xx.get("xxlx"));
            xxzt=String.valueOf(xx.get("xxzt"));
            if("1".equalsIgnoreCase(xxlx)){//审批消息
                spzt=String.valueOf(xx.get("spzt"));
                xxListSp.add(xx);
                if("0".equalsIgnoreCase(spzt)){
                    xxListDsp.add(xx);
                }else if("1".equalsIgnoreCase(spzt)){
                    xxListYsp.add(xx);
                }
                if("0".equalsIgnoreCase(xxzt)){
                    xxListWdSP.add(xx);
                    if(zxWdSp==null){
                        zxWdSp=xx;
                    }
                }
            }else if("2".equalsIgnoreCase(xxlx)){//预警消息
                xxListYj.add(xx);
                if("0".equalsIgnoreCase(xxzt)){
                    xxListWdYj.add(xx);
                    if(zxWdYj==null){
                        zxWdYj=xx;
                    }
                }
            }
        }
        result.put("DSP",xxListDsp);
        result.put("YSP",xxListYsp);
        result.put("SP",xxListSp);
        result.put("YJ",xxListYj);
        result.put("WDSP",xxListWdSP);
        result.put("WDYJ",xxListWdYj);
        result.put("ZXWDSP",zxWdSp);
        result.put("ZXWDYJ",zxWdYj);
        return result;
    }

    @Override
    public boolean ydxx(Map<String,Object> params) {
//        xxtxMapper.ydxx(params);
        return true;
    }

    @Override
    public boolean scxx(Map<String,Object> params) {
        ArrayList <Integer>  checkVal = (ArrayList<Integer>) params.get("checkVal");
        xxtxMapper.scxx(params);
        return true;
    }
}
