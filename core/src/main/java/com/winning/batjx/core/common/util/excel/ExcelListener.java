package com.winning.batjx.core.common.util.excel;
/**
 * Created by yxf on 2019/11/8.
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.util.StringUtil;
import com.winning.batjx.core.khgl.khwj.domain.TmExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxf
 * @Description TODO
 * @Date 2019/11/8 9:30
 */
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {
    /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     *
     */
    private final List<T> data = new ArrayList<>();

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //数据存储
        if(t instanceof TmExcel){
            TmExcel temp = (TmExcel)t;
            // 中文字符的，转为英文,
            if(StringUtil.isNotEmpty(temp.getTmmc())){
                temp.setTmmc(temp.getTmmc().replaceAll("，",","));
            }
            if(StringUtil.isNotEmpty(temp.getTmfbt())){
                temp.setTmfbt(temp.getTmfbt().replaceAll("，",","));
            }
        }
        data.add(t);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<T> getData() {
        return new ArrayList<>(data) ;
    }
}
