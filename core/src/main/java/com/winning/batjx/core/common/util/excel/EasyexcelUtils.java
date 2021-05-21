package com.winning.batjx.core.common.util.excel;
/**
 * Created by yxf on 2019/11/8.
 */

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.winning.batjx.core.common.util.excel.ExcelListener;

import java.io.InputStream;
import java.util.List;

/**
 * @author yxf
 * @Description TODO
 * @Date 2019/11/8 14:49
 */
public class EasyexcelUtils {

    public static  <T extends BaseRowModel> List<T> readExcel(final InputStream inputStream, final Class<? extends BaseRowModel> clazz) {
        if (null == inputStream) {
            throw new NullPointerException("the inputStream is null!");
        }
        AnalysisEventListener listener = new ExcelListener<>();
        //读取xls 和 xlxs格式
        //如果POI版本为3.17，可以如下声明
        ExcelReader reader = new ExcelReader(inputStream, null, listener);
        //判断格式，针对POI版本低于3.17
        //ExcelTypeEnum excelTypeEnum = valueOf(inputStream);
        //ExcelReader reader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
        // hidelineMun是标题头的行数
        reader.read(new com.alibaba.excel.metadata.Sheet(1, 4, clazz));

        return ((ExcelListener) listener).getData();
    }

    /**
     * @author yxf@winning.com.cn in 2020/3/17
     * @Description: 考核配置页面 上传的时候从第一行上传区别于基础构建
     **/
    public static  <T extends BaseRowModel> List<T> readExcelKhpz(final InputStream inputStream, final Class<? extends BaseRowModel> clazz) {
        if (null == inputStream) {
            throw new NullPointerException("the inputStream is null!");
        }
        AnalysisEventListener listener = new ExcelListener<>();
        //读取xls 和 xlxs格式
        //如果POI版本为3.17，可以如下声明
        ExcelReader reader = new ExcelReader(inputStream, null, listener);
        //判断格式，针对POI版本低于3.17
        //ExcelTypeEnum excelTypeEnum = valueOf(inputStream);
        //ExcelReader reader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
        // hidelineMun是标题头的行数
        reader.read(new com.alibaba.excel.metadata.Sheet(1, 1, clazz));

        return ((ExcelListener) listener).getData();
    }

}
