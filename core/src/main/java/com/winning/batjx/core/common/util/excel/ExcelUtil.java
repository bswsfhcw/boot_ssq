package com.winning.batjx.core.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

/**
 * The <code>ExcelUtil</code> 与 {@link ExcelCell}搭配使用
 */
public class ExcelUtil
{
    
    private static Log LG =  LogFactory.getLog(ExcelUtil.class);
    
    /**
     * 用来验证excel与Vo中的类型是否一致 <br>
     * Map<栏位类型,只能是哪些Cell类型>
     */
    private static Map<Class<?>, Integer[]> validateMap = new HashMap<Class<?>, Integer[]>();
    
    static
    {
        validateMap.put(String[].class, new Integer[] {Cell.CELL_TYPE_STRING});
        validateMap.put(Double[].class, new Integer[] {Cell.CELL_TYPE_NUMERIC});
        validateMap.put(String.class, new Integer[] {Cell.CELL_TYPE_STRING});
        validateMap.put(Double.class, new Integer[] {Cell.CELL_TYPE_NUMERIC});
        validateMap.put(Date.class, new Integer[] {Cell.CELL_TYPE_NUMERIC, Cell.CELL_TYPE_STRING});
        validateMap.put(Integer.class, new Integer[] {Cell.CELL_TYPE_NUMERIC});
        validateMap.put(Float.class, new Integer[] {Cell.CELL_TYPE_NUMERIC});
        validateMap.put(Long.class, new Integer[] {Cell.CELL_TYPE_NUMERIC});
        validateMap.put(Boolean.class, new Integer[] {Cell.CELL_TYPE_BOOLEAN});
    }
    
    /**
     * 获取cell类型的文字描述
     * 
     * @param cellType <pre>
     *                 Cell.CELL_TYPE_BLANK
     *                 Cell.CELL_TYPE_BOOLEAN
     *                 Cell.CELL_TYPE_ERROR
     *                 Cell.CELL_TYPE_FORMULA
     *                 Cell.CELL_TYPE_NUMERIC
     *                 Cell.CELL_TYPE_STRING
     * </pre>
     * @return
     */
    private static String getCellTypeByInt(int cellType)
    {
        switch (cellType)
        {
            case Cell.CELL_TYPE_BLANK:
                return "Null type";
            case Cell.CELL_TYPE_BOOLEAN:
                return "Boolean type";
            case Cell.CELL_TYPE_ERROR:
                return "Error type";
            case Cell.CELL_TYPE_FORMULA:
                return "Formula type";
            case Cell.CELL_TYPE_NUMERIC:
                return "Numeric type";
            case Cell.CELL_TYPE_STRING:
                return "String type";
            default:
                return "Unknown type";
        }
    }
    
    /**
     * 获取单元格值
     * 
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell)
    {
        if (cell == null
            || (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isBlank(cell.getStringCellValue())))
        {
            return null;
        }
        int cellType = cell.getCellType();
        switch (cellType)
        {
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }
    
    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     * 
     * @param <T>
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    public static <T> void exportExcel(String[] headers, Collection<T> dataset, OutputStream out)
    {
        exportExcel(headers, dataset, out, null);
    }

    /**
     * yxf 2018-01-12勿动
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     *
     * @param <T>
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    public static <T> void exportMyExcel(String[] headers, Collection<T> dataset, OutputStream out)
    {
        exportMyExcel(headers, dataset, out, null);
    }

    /**
     * 【警告！】-此方法为舟山绩效-绩效清单维护拓展方法-勿动！
     * @param headers
     * @param dataset
     * @param out
     * @param <T>
     */
    public static <T> void exportExcelCanHiddenLie(String[] headers, Collection<T> dataset, OutputStream out)
    {
        exportExcelCanHiddenLie(headers, dataset, out, null);
    }

    
    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     * 
     * @param <T>
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static <T> void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern)
    {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();


        write2Sheet(workbook,sheet, headers, dataset, pattern);
        try
        {
            workbook.write(out);
            if(out!=null){
                out.close();
            }
        }
        catch (IOException e)
        {
             LG.error(e.toString(), e);
        }
    }
    /**
     * yxf2018-01-12修改  勿动
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于单个sheet
     *
     * @param <T>
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 javabean属性的数据类型有基本数据类型及String,Date,String[],Double[]
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static <T> void exportMyExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern)
    {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();

        write2MySheet(workbook,sheet, headers, dataset, pattern);

        try
        {
            workbook.write(out);

        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
    }

    /*
    【警告】舟山绩效-绩效清单维护特有隐藏列，勿动
     */
    public static <T> void exportExcelCanHiddenLie(String[] headers, Collection<T> dataset, OutputStream out, String pattern)
    {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();

        //将第一列的rylsh 和 ztbm 列隐藏
        sheet.setColumnHidden((short)0,true);
        sheet.setColumnHidden((short)1,true);

        write2Sheet(workbook,sheet, headers, dataset, pattern);
        try
        {
            workbook.write(out);
            if(out!=null){
                out.close();
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
    }
    
    public static void exportExcel(String[][] datalist, OutputStream out)
    {
        HSSFWorkbook workbook = null;
        try
        {
            // 声明一个工作薄
            workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet();
            
            for (int i = 0; i < datalist.length; i++)
            {
                String[] r = datalist[i];
                HSSFRow row = sheet.createRow(i);
                for (int j = 0; j < r.length; j++)
                {
                    HSSFCell cell = row.createCell(j);
                    // cell max length 32767
                    if (r[j].length() > 32767)
                    {
                        r[j] = "--此字段过长(超过32767),已被截断--" + r[j];
                        r[j] = r[j].substring(0, 32766);
                    }
                    cell.setCellValue(r[j]);
                }
            }
            // 自动列宽
            if (datalist.length > 0)
            {
                int colcount = datalist[0].length;
                for (int i = 0; i < colcount; i++)
                {
                    sheet.autoSizeColumn(i);
                }
            }
            workbook.write(out);
        }
        catch (IOException e)
        {
             LG.error(e.toString(), e);
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                }finally {
                    workbook = null;
                }
            }
        }
    }
    
    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于多个sheet
     * 
     * @param <T>
     * @param sheets {@link ExcelSheet}的集合
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    public static <T> void exportExcel(List<ExcelSheet<T>> sheets, OutputStream out)
    {
        exportExcel(sheets, out, null);
    }
    
    /**
     * 利用JAVA的反射机制，将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上<br>
     * 用于多个sheet
     * 
     * @param <T>
     * @param sheets {@link ExcelSheet}的集合
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public static <T> void exportExcel(List<ExcelSheet<T>> sheets, OutputStream out, String pattern)
    {
        if (sheets == null || sheets.size() == 0)
        {
            return;
        }
        HSSFWorkbook workbook = null;
        try
        {
        // 声明一个工作薄
        workbook = new HSSFWorkbook();
        for (ExcelSheet<T> sheet : sheets)
        {
            // 生成一个表格
            HSSFSheet hssfSheet = workbook.createSheet(sheet.getSheetName());
            write2Sheet(hssfSheet, sheet.getHeaders(), sheet.getDataset(), pattern);
        }
            workbook.write(out);
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }finally {
            if(workbook !=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                } finally {
                    workbook =null;
                }
            }
        }
    }

    public static <T> void write2Sheet(XSSFSheet sheet, String[] headers, Collection<T> dataset, String pattern)
    {

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);
            T t = (T)it.next();
            try
            {
                if (t instanceof Map)
                {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>)t;
                    int cellNum = 0;
                    for (String k : headers)
                    {
                        if (map.containsKey(k) == false)
                        {
                            LG.error("Map 中 不存在 key [" + k + "]");
                            continue;
                        }
                        Object value = map.get(k);
                        XSSFCell cell = row.createCell(cellNum);
                        if(value == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(String.valueOf(value));
                        }
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cellNum++;
                    }
                }
                else
                {
                    List<FieldForSortting> fields = sortFieldByAnno(t.getClass());
                    int cellNum = 0;
                    for (int i = 0; i < fields.size(); i++)
                    {
                        XSSFCell cell = row.createCell(cellNum);
                        Field field = fields.get(i).getField();
                        field.setAccessible(true);
                        Object value = field.get(t);
                        String textValue = null;
                        if (value instanceof Integer)
                        {
                            int intValue = (Integer)value;
                            cell.setCellValue(intValue);
                        }
                        else if (value instanceof Float)
                        {
                            float fValue = (Float)value;
                            cell.setCellValue(fValue);
                        }
                        else if (value instanceof Double)
                        {
                            double dValue = (Double)value;
                            cell.setCellValue(dValue);
                        }
                        else if (value instanceof Long)
                        {
                            long longValue = (Long)value;
                            cell.setCellValue(longValue);
                        }
                        else if (value instanceof Boolean)
                        {
                            boolean bValue = (Boolean)value;
                            cell.setCellValue(bValue);
                        }
                        else if (value instanceof Date)
                        {
                            Date date = (Date)value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        }
                        else if (value instanceof String[])
                        {
                            String[] strArr = (String[])value;
                            for (int j = 0; j < strArr.length; j++)
                            {
                                String str = strArr[j];
                                cell.setCellValue(str);
                                if (j != strArr.length - 1)
                                {
                                    cellNum++;
                                    cell = row.createCell(cellNum);
                                }
                            }
                        }
                        else if (value instanceof Double[])
                        {
                            Double[] douArr = (Double[])value;
                            for (int j = 0; j < douArr.length; j++)
                            {
                                Double val = douArr[j];
                                // 资料不为空则set Value
                                if (val != null)
                                {
                                    cell.setCellValue(val);
                                }

                                if (j != douArr.length - 1)
                                {
                                    cellNum++;
                                    cell = row.createCell(cellNum);
                                }
                            }
                        }
                        else
                        {
                            // 其它数据类型都当作字符串简单处理
                            String empty = StringUtils.EMPTY;
                            ExcelCell anno = field.getAnnotation(ExcelCell.class);
                            if (anno != null)
                            {
                                empty = anno.defaultValue();
                            }
                            textValue = value == null ? empty : value.toString();
                        }
                        if (textValue != null)
                        {
                            XSSFRichTextString richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cellNum++;
                    }
                }
            }
            catch (Exception e)
            {
                LG.error(e.toString(), e);
            }
        }
        // 设定自动宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
        }
    }
    /**
     * 每个sheet的写入
     * 
     * @param sheet 页签
     * @param headers 表头
     * @param dataset 数据集合
     * @param pattern 日期格式
     */
    public static <T> void write2Sheet(XSSFWorkbook workbook,XSSFSheet sheet, String[] headers, Collection<T> dataset, String pattern)
    {

        XSSFCellStyle cellStyle2 = workbook.createCellStyle();
        XSSFDataFormat format = workbook.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("@"));
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellStyle(cellStyle2);
            cell.setCellValue(text);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
        
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);
            T t = (T)it.next();
            try
            {
                if (t instanceof Map)
                {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>)t;
                    int cellNum = 0;
                    for (String k : headers)
                    {
                        if (map.containsKey(k) == false)
                        {
                            LG.error("Map 中 不存在 key [" + k + "]");
                            continue;
                        }
                        Object value = map.get(k);
                        XSSFCell cell = row.createCell(cellNum);
                        cell.setCellStyle(cellStyle2);
                        if(value == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(String.valueOf(value));
                        }
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cellNum++;
                    }
                }
                else
                {
                    List<FieldForSortting> fields = sortFieldByAnno(t.getClass());
                    int cellNum = 0;
                    for (int i = 0; i < fields.size(); i++)
                    {
                        XSSFCell cell = row.createCell(cellNum);
                        cell.setCellStyle(cellStyle2);
                        Field field = fields.get(i).getField();
                        field.setAccessible(true);
                        Object value = field.get(t);
                        String textValue = null;
                        if (value instanceof Integer)
                        {
                            int intValue = (Integer)value;
                            cell.setCellValue(intValue);
                        }
                        else if (value instanceof Float)
                        {
                            float fValue = (Float)value;
                            cell.setCellValue(fValue);
                        }
                        else if (value instanceof Double)
                        {
                            double dValue = (Double)value;
                            cell.setCellValue(dValue);
                        }
                        else if (value instanceof Long)
                        {
                            long longValue = (Long)value;
                            cell.setCellValue(longValue);
                        }
                        else if (value instanceof Boolean)
                        {
                            boolean bValue = (Boolean)value;
                            cell.setCellValue(bValue);
                        }
                        else if (value instanceof Date)
                        {
                            Date date = (Date)value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        }
                        else if (value instanceof String[])
                        {
                            String[] strArr = (String[])value;
                            for (int j = 0; j < strArr.length; j++)
                            {
                                String str = strArr[j];
                                cell.setCellValue(str);
                                if (j != strArr.length - 1)
                                {
                                    cellNum++;
                                    cell = row.createCell(cellNum);
                                }
                            }
                        }
                        else if (value instanceof Double[])
                        {
                            Double[] douArr = (Double[])value;
                            for (int j = 0; j < douArr.length; j++)
                            {
                                Double val = douArr[j];
                                // 资料不为空则set Value
                                if (val != null)
                                {
                                    cell.setCellValue(val);
                                }
                                
                                if (j != douArr.length - 1)
                                {
                                    cellNum++;
                                    cell = row.createCell(cellNum);
                                }
                            }
                        }
                        else
                        {
                            // 其它数据类型都当作字符串简单处理
                            String empty = StringUtils.EMPTY;
                            ExcelCell anno = field.getAnnotation(ExcelCell.class);
                            if (anno != null)
                            {
                                empty = anno.defaultValue();
                            }
                            textValue = value == null ? empty : value.toString();
                        }
                        if (textValue != null)
                        {
                            XSSFRichTextString richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cellNum++;
                    }
                }
            }
            catch (Exception e)
            {
                LG.error(e.toString(), e);
            }
        }
        // 设定自动宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
        }
    }


    /**
     * yxf 2018-01-12
     * 勿动 自定义写sheet数据
     * 每个sheet的写入
     * @param sheet 页签
     * @param headers 表头
     * @param dataset 数据集合
     * @param pattern 日期格式
     */
    private static <T> void write2MySheet(XSSFWorkbook xssfWorkbook,XSSFSheet sheet, String[] headers, Collection<T> dataset, String pattern)
    {
//        //字体样式
//        XSSFFont xssfFont = xssfWorkbook.createFont();
//        xssfFont.setColor(IndexedColors.RED.getIndex());
//
//        //单元格样式
//        XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
//        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
//        cellStyle.setWrapText(true);
//        cellStyle.setAlignment(XSSFCellStyle.VERTICAL_TOP);
//        cellStyle.setFont(xssfFont);
//
//
//        // 手动设置首行说明
//        XSSFRow firstRow = sheet.createRow(0);
//        String remarkText = "说明：1，该EXCEL表数据入后台表SYS_ZCJG表，每导入一条数据会通过机构编码判断后台库是否存在该条数据（后台表已经存在则该条数据以excel为主，不存在则直接插入）";
//        CellRangeAddress rangeAddress = new CellRangeAddress(0,0,0,6);
//        //在sheet里增加合并单元格
//        sheet.addMergedRegion(rangeAddress);
//        firstRow.setHeight((short)(20*40));
//        firstRow.setRowStyle(cellStyle);

        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setBold(true);
        XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
        cellStyle.setFont(xssfFont);
//        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        XSSFCellStyle cellStyleContent = xssfWorkbook.createCellStyle();
//        cellStyleContent.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        row.setHeight((short)(20*20));
        for (int i = 0; i < headers.length; i++)
        {

            sheet.autoSizeColumn((short)i);
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);
        }

        if(dataset == null)
            return;

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        XSSFCell cell = null;
        Object value = null;
        Map<String, Object> map = null;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);
            T t = (T)it.next();
            try
            {
                if (t instanceof Map)
                {
                    map = (Map<String, Object>)t;
                    int cellNum = 0;
                    for (String k : headers)
                    {
                        if (map.containsKey(k) == false)
                        {
                            LG.error("Map 中 不存在 key [" + k + "]");
                            continue;
                        }
                        value = map.get(k);
                        cell = row.createCell(cellNum);
                        if(value == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(String.valueOf(value));
                            cell.setCellStyle(cellStyleContent);
                        }
                        cellNum++;
                    }
                }
            }
            catch (Exception e)
            {
                LG.error(e.toString(), e);
            }
        }
        // 设定自动宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,headers[i].getBytes().length*2*256);//设置列名自动长度
        }
    }



    /**
     * 每个sheet的写入
     * 
     * @param sheet 页签
     * @param headers 表头
     * @param dataset 数据集合
     * @param pattern 日期格式
     */
    private static <T> void write2Sheet(HSSFSheet sheet, String[] headers, Collection<T> dataset, String pattern)
    {
    
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);
            T t = (T)it.next();
            try
            {
                if (t instanceof Map)
                {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>)t;
                    int cellNum = 0;
                    for (String k : headers)
                    {
                        if (map.containsKey(k) == false)
                        {
                            LG.error("Map 中 不存在 key [" + k + "]");
                            continue;
                        }
                        Object value = map.get(k);
                        HSSFCell cell = row.createCell(cellNum);
                        if(value == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(String.valueOf(value));
                        }

                        cellNum++;
                    }
                }
                else
                {
                    List<FieldForSortting> fields = sortFieldByAnno(t.getClass());
                    int cellNum = 0;
                    for (int i = 0; i < fields.size(); i++)
                    {
                        HSSFCell cell = row.createCell(cellNum);
                        Field field = fields.get(i).getField();
                        field.setAccessible(true);
                        Object value = field.get(t);
                        String textValue = null;
                        if (value instanceof Integer)
                        {
                            int intValue = (Integer)value;
                            cell.setCellValue(intValue);
                        }
                        else if (value instanceof Float)
                        {
                            float fValue = (Float)value;
                            cell.setCellValue(fValue);
                        }
                        else if (value instanceof Double)
                        {
                            double dValue = (Double)value;
                            cell.setCellValue(dValue);
                        }
                        else if (value instanceof Long)
                        {
                            long longValue = (Long)value;
                            cell.setCellValue(longValue);
                        }
                        else if (value instanceof Boolean)
                        {
                            boolean bValue = (Boolean)value;
                            cell.setCellValue(bValue);
                        }
                        else if (value instanceof Date)
                        {
                            Date date = (Date)value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        }
                        else if (value instanceof String[])
                        {
                            String[] strArr = (String[])value;
                            for (int j = 0; j < strArr.length; j++)
                            {
                                String str = strArr[j];
                                cell.setCellValue(str);
                                if (j != strArr.length - 1)
                                {
                                    cellNum++;
                                    cell = row.createCell(cellNum);
                                }
                            }
                        }
                        else if (value instanceof Double[])
                        {
                            Double[] douArr = (Double[])value;
                            for (int j = 0; j < douArr.length; j++)
                            {
                                Double val = douArr[j];
                                // 资料不为空则set Value
                                if (val != null)
                                {
                                    cell.setCellValue(val);
                                }
                                
                                if (j != douArr.length - 1)
                                {
                                    cellNum++;
                                    cell = row.createCell(cellNum);
                                }
                            }
                        }
                        else
                        {
                            // 其它数据类型都当作字符串简单处理
                            String empty = StringUtils.EMPTY;
                            ExcelCell anno = field.getAnnotation(ExcelCell.class);
                            if (anno != null)
                            {
                                empty = anno.defaultValue();
                            }
                            textValue = value == null ? empty : value.toString();
                        }
                        if (textValue != null)
                        {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                        
                        cellNum++;
                    }
                }
            }
            catch (Exception e)
            {
                LG.error(e.toString(), e);
            }
        }
        // 设定自动宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
        }
    }
    
    public static boolean isExcel2003(String filePath)
    {
        
        return filePath.matches("^.+\\.(?i)(xls)$");
        
    }
    
    public static boolean isExcel2007(String filePath)
    {
        
        return filePath.matches("^.+\\.(?i)(xlsx)$");
        
    }
    
    /**
     * 把Excel的数据封装成voList
     * 
     * @param clazz vo的Class
     * @param inputStream excel输入流
     * @param pattern 如果有时间数据，设定输入格式。默认为"yyy-MM-dd"
     * @param logs 错误log集合
     * @param arrayCount 如果vo中有数组类型,那就按照index顺序,把数组应该有几个值写上.
     * @return voList
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> importExcel(String filename, Class<T> clazz, InputStream inputStream,
        String pattern, ExcelLogs logs, Integer... arrayCount)
    {
        Workbook workBook = null;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
        List<T> list = new ArrayList<T>();
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        try
        {
            List<ExcelLog> logList = new ArrayList<ExcelLog>();
            // Map<title,index>
            Map<String, Integer> titleMap = new HashMap<String, Integer>();
            
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0)
                {
                    if (clazz == Map.class)
                    {
                        // 解析map用的key,就是excel标题行
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Integer index = 0;
                        while (cellIterator.hasNext())
                        {
                            String value = cellIterator.next().getStringCellValue();
                            titleMap.put(value, index);
                            index++;
                        }
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null)
                    {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull)
                {
                    LG.warn("Excel row " + row.getRowNum() + " all row value is null!");
                    continue;
                }
                T t = null;
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String k : titleMap.keySet())
                    {
                        Integer index = titleMap.get(k);
                        if(row.getCell(index)!=null){
                            row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(index).getStringCellValue();
                            map.put(k, value);
                        }
                       
                    }
                    list.add((T)map);
                    
                }
                else
                {
                    t = clazz.newInstance();
                    int arrayIndex = 0;// 标识当前第几个数组了
                    int cellIndex = 0;// 标识当前读到这一行的第几个cell了
                    List<FieldForSortting> fields = sortFieldByAnno(clazz);
                    for (FieldForSortting ffs : fields)
                    {
                        Field field = ffs.getField();
                        field.setAccessible(true);
                        if (field.getType().isArray())
                        {
                            Integer count = arrayCount[arrayIndex];
                            Object[] value = null;
                            if (field.getType().equals(String[].class))
                            {
                                value = new String[count];
                            }
                            else
                            {
                                // 目前只支持String[]和Double[]
                                value = new Double[count];
                            }
                            for (int i = 0; i < count; i++)
                            {
                                Cell cell = row.getCell(cellIndex);
                                String errMsg = validateCell(cell, field, cellIndex);
                                if (StringUtils.isBlank(errMsg))
                                {
                                    value[i] = getCellValue(cell);
                                }
                                else
                                {
                                    log.append(errMsg);
                                    log.append(";");
                                    logs.setHasError(true);
                                }
                                cellIndex++;
                            }
                            field.set(t, value);
                            arrayIndex++;
                        }
                        else
                        {
                            Cell cell = row.getCell(cellIndex);
                            String errMsg = validateCell(cell, field, cellIndex);
                            if (StringUtils.isBlank(errMsg))
                            {
                                Object value = null;
                                // 处理特殊情况,Excel中的String,转换成Bean的Date
                                if (field.getType().equals(Date.class) && cell.getCellType() == Cell.CELL_TYPE_STRING)
                                {
                                    Object strDate = getCellValue(cell);
                                    try
                                    {
                                        value = new SimpleDateFormat(pattern).parse(strDate==null?"":strDate.toString());
                                    }
                                    catch (ParseException e)
                                    {
                                        
                                        errMsg =
                                            MessageFormat.format("the cell [{0}] can not be converted to a date ",
                                                CellReference.convertNumToColString(cell.getColumnIndex()));
                                    }
                                }
                                else
                                {
                                    value = getCellValue(cell);
                                    // 处理特殊情况,excel的value为String,且bean中为其他,且defaultValue不为空,那就=defaultValue
                                    ExcelCell annoCell = field.getAnnotation(ExcelCell.class);
                                    if (value instanceof String && !field.getType().equals(String.class)
                                        && StringUtils.isNotBlank(annoCell.defaultValue()))
                                    {
                                        value = annoCell.defaultValue();
                                    }
                                }
                                field.set(t, value);
                            }
                            if (StringUtils.isNotBlank(errMsg))
                            {
                                log.append(errMsg);
                                log.append(";");
                                logs.setHasError(true);
                            }
                            cellIndex++;
                        }
                    }
                    list.add(t);
                    logList.add(new ExcelLog(t, log.toString(), row.getRowNum() + 1));
                }
            }
            logs.setLogList(logList);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        return list;
    }

    /**
     * 获取sheet数量
     * */
    public static int getSheetCount(String filename, InputStream inputStream){
        Workbook workBook = null;
        int sheetNum = 0;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
        if(workBook!=null){
            sheetNum = workBook.getNumberOfSheets();
        }
        return  sheetNum;
    }


    /**
     * 是否计量分类
     * 工作量  jlfl
     * 质量  zlfl
     * 岗位权重 gwqzfl
     * 质控  zkfl
     * */
    public static String getSheetType(String filename, InputStream inputStream){
        String type = "";
        Workbook workBook = null;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }

            if(workBook.getNumberOfSheets()!=5 && workBook.getNumberOfSheets()==3){
                Sheet sheet2 = workBook.getSheetAt(1);
                if(sheet2.getSheetName().equals("计量指标")){
                    type = "jlfl";
                }else if(sheet2.getSheetName().equals("质量指标")){
                    type = "zlfl";
                }else if(sheet2.getSheetName().equals("岗位权重指标")){
                    type = "gwqzfl";
                }
            }else{
                type = "zkfl";
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }

        return type;
    }


    /**
     * yxf 2018-01-11 修改
     * 勿动 第一行是说明  第二行是标题 第三行是说明的excel解析其数据
     * 把Excel的数据封装成voList
     * @param clazz vo的Class
     * @param inputStream excel输入流
     * @param pattern 如果有时间数据，设定输入格式。默认为"yyy-MM-dd"
     * @param logs 错误log集合
     * @param arrayCount 如果vo中有数组类型,那就按照index顺序,把数组应该有几个值写上.
     * @return voList
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> importMyExcel(String filename, Class<T> clazz, InputStream inputStream,
                                                String pattern, ExcelLogs logs, Integer... arrayCount)
    {
        Workbook workBook = null;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
        List<T> list = new ArrayList<T>();
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        try
        {
            List<ExcelLog> logList = new ArrayList<ExcelLog>();
            // Map<title,index>
            Map<String, Integer> titleMap = new HashMap<String, Integer>();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; //第一行是说明跳过
                if (row.getRowNum() == 2) continue;//第三行是说明跳过
                if (row.getRowNum() == 1)
                {
                    if (clazz == Map.class)
                    {
                        // 解析map用的key,就是excel标题行
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Integer index = 0;
                        while (cellIterator.hasNext())
                        {
                            String value = cellIterator.next().getStringCellValue();
                            titleMap.put(value, index);
                            index++;
                        }
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null)
                    {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull)
                {
                    LG.warn("Excel row " + row.getRowNum() + " all row value is null!");
                    continue;
                }
                T t = null;
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String k : titleMap.keySet())
                    {
                        Integer index = titleMap.get(k);
                        if(row.getCell(index)!=null){
                            row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(index).getStringCellValue();
                            map.put(k, value);
                        }
                    }
                    list.add((T)map);
                }
            }
            logs.setLogList(logList);
        }
        catch (Exception e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }

        return list;
    }


    /**
     * yxf 2018-01-11 修改
     * 勿动 南京绩效  表头合并不固定格式解析
     * 把Excel的数据封装成voList
     * @param clazz vo的Class
     * @param inputStream excel输入流
     * @param pattern 如果有时间数据，设定输入格式。默认为"yyy-MM-dd"
     * @param logs 错误log集合
     * @param arrayCount 如果vo中有数组类型,那就按照index顺序,把数组应该有几个值写上.
     * @return voList
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> importAssertsExcel(String filename, Class<T> clazz, InputStream inputStream,
                                                  String pattern, ExcelLogs logs, Integer... arrayCount)
    {
        Workbook workBook = null;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
        List<T> list = new ArrayList<T>();
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        try
        {
            List<ExcelLog> logList = new ArrayList<ExcelLog>();
            // Map<title,index>
            Map<String, Integer> titleMap = new HashMap<String, Integer>();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();

                if (row.getRowNum() == 2  || row.getRowNum() == 3 || row.getRowNum() == 4)
                {
                    if (clazz == Map.class)
                    {
                        // 解析map用的key,就是excel标题行
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Integer index = 0;
                        while (cellIterator.hasNext())
                        {
                            String value = cellIterator.next().getStringCellValue();
                            titleMap.put(value, index);
                            index++;
                        }
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null)
                    {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull)
                {
                    LG.warn("Excel row " + row.getRowNum() + " all row value is null!");
                    continue;
                }
                T t = null;
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String k : titleMap.keySet())
                    {
                        Integer index = titleMap.get(k);
                        if(row.getCell(index)!=null){
                            row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(index).getStringCellValue();
                            map.put(k, value);
                        }
                    }
                    list.add((T)map);
                }
            }
            logs.setLogList(logList);
        }
        catch (Exception e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }

        return list;
    }
    
    /**
     * 驗證Cell類型是否正確
     * 
     * @param cell cell單元格
     * @param field 欄位
     * @param cellNum 第幾個欄位,用於errMsg
     * @return
     */
    private static String validateCell(Cell cell, Field field, int cellNum)
    {
        String columnName = CellReference.convertNumToColString(cellNum);
        String result = null;
        Integer[] integers = validateMap.get(field.getType());
        if (integers == null)
        {
            result = MessageFormat.format("Unsupported type [{0}]", field.getType().getSimpleName());
            return result;
        }
        ExcelCell annoCell = field.getAnnotation(ExcelCell.class);
        if (cell == null
            || (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isBlank(cell.getStringCellValue())))
        {
            if (annoCell != null && annoCell.valid().allowNull() == false)
            {
                result = MessageFormat.format("the cell [{0}] can not null", columnName);
            }
            ;
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_BLANK && annoCell.valid().allowNull())
        {
            return result;
        }
        else
        {
            List<Integer> cellTypes = Arrays.asList(integers);
            
            // 如果類型不在指定範圍內,並且沒有默認值
            if (!(cellTypes.contains(cell.getCellType())) || StringUtils.isNotBlank(annoCell.defaultValue())
                && cell.getCellType() == Cell.CELL_TYPE_STRING)
            {
                StringBuilder strType = new StringBuilder();
                for (int i = 0; i < cellTypes.size(); i++)
                {
                    Integer intType = cellTypes.get(i);
                    strType.append(getCellTypeByInt(intType));
                    if (i != cellTypes.size() - 1)
                    {
                        strType.append(",");
                    }
                }
                result = MessageFormat.format("the cell [{0}] type must [{1}]", columnName, strType.toString());
            }
            else
            {
                // 类型符合验证,但值不在要求范围内的
                // String in
                if (annoCell.valid().in().length != 0 && cell.getCellType() == Cell.CELL_TYPE_STRING)
                {
                    String[] in = annoCell.valid().in();
                    String cellValue = cell.getStringCellValue();
                    boolean isIn = false;
                    for (String str : in)
                    {
                        if (str.equals(cellValue))
                        {
                            isIn = true;
                        }
                    }
                    if (!isIn)
                    {
                        result = MessageFormat.format("the cell [{0}] value must in {1}", columnName, in);
                    }
                }
                // 数字型
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                {
                    double cellValue = cell.getNumericCellValue();
                    // 小于
                    if (!Double.isNaN(annoCell.valid().lt()))
                    {
                        if (!(cellValue < annoCell.valid().lt()))
                        {
                            result =
                                MessageFormat.format("the cell [{0}] value must less than [{1}]",
                                    columnName,
                                    annoCell.valid().lt());
                        }
                    }
                    // 大于
                    if (!Double.isNaN(annoCell.valid().gt()))
                    {
                        if (!(cellValue > annoCell.valid().gt()))
                        {
                            result =
                                MessageFormat.format("the cell [{0}] value must greater than [{1}]",
                                    columnName,
                                    annoCell.valid().gt());
                        }
                    }
                    // 小于等于
                    if (!Double.isNaN(annoCell.valid().le()))
                    {
                        if (!(cellValue <= annoCell.valid().le()))
                        {
                            result =
                                MessageFormat.format("the cell [{0}] value must less than or equal [{1}]",
                                    columnName,
                                    annoCell.valid().le());
                        }
                    }
                    // 大于等于
                    if (!Double.isNaN(annoCell.valid().ge()))
                    {
                        if (!(cellValue >= annoCell.valid().ge()))
                        {
                            result =
                                MessageFormat.format("the cell [{0}] value must greater than or equal [{1}]",
                                    columnName,
                                    annoCell.valid().ge());
                        }
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 根据annotation的seq排序后的栏位
     * 
     * @param clazz
     * @return
     */
    private static List<FieldForSortting> sortFieldByAnno(Class<?> clazz)
    {
        Field[] fieldsArr = clazz.getDeclaredFields();
        List<FieldForSortting> fields = new ArrayList<FieldForSortting>();
        List<FieldForSortting> annoNullFields = new ArrayList<FieldForSortting>();
        for (Field field : fieldsArr)
        {
            ExcelCell ec = field.getAnnotation(ExcelCell.class);
            if (ec == null)
            {
                // 没有ExcelCell Annotation 视为不汇入
                continue;
            }
            int id = ec.index();
            fields.add(new FieldForSortting(field, id));
        }
        fields.addAll(annoNullFields);
        sortByProperties(fields, true, false, "index");
        return fields;
    }
    
    @SuppressWarnings("unchecked")
    private static void sortByProperties(List<? extends Object> list, boolean isNullHigh, boolean isReversed,
        String... props)
    {
        
        if (list != null && list.size() > 0)
        {
            Comparator<?> typeComp = ComparableComparator.getInstance();
            if (isNullHigh == true)
            {
                typeComp = ComparatorUtils.nullHighComparator(typeComp);
            }
            else
            {
                typeComp = ComparatorUtils.nullLowComparator(typeComp);
            }
            if (isReversed)
            {
                typeComp = ComparatorUtils.reversedComparator(typeComp);
            }
            
            List<Object> sortCols = new ArrayList<Object>();
            
            if (props != null)
            {
                for (String prop : props)
                {
                    sortCols.add(new BeanComparator(prop, typeComp));
                }
            }
            if (sortCols.size() > 0)
            {
                Comparator<Object> sortChain = new ComparatorChain(sortCols);
                Collections.sort(list, sortChain);
            }
        }
    }


    /*
    本方法适用于南京绩效-医改监测分析报表一，只将excl表格内的数据进行上传
     */
    public static <T> Collection<T> importExcelNotFristRow(String filename, Class<T> clazz, InputStream inputStream,
                                                String pattern, ExcelLogs logs, Integer... arrayCount)
    {
        Workbook workBook = null;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
        List<T> list = new ArrayList<T>();
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        try
        {
            List<ExcelLog> logList = new ArrayList<ExcelLog>();
            // Map<title,index>
            Map<String, Integer> titleMap = new HashMap<String, Integer>();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if(row.getRowNum()==0||row.getRowNum()==1){
                    continue;
                }
                if (row.getRowNum() == 2)
                {
                    if (clazz == Map.class)
                    {
                        // 解析map用的key,就是excel标题行
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Integer index = 0;
                        while (cellIterator.hasNext())
                        {
                            String value = cellIterator.next().getStringCellValue();
                            titleMap.put(value, index);
                            index++;
                        }
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null)
                    {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull)
                {
                    LG.warn("Excel row " + row.getRowNum() + " all row value is null!");
                    continue;
                }
                T t = null;
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String k : titleMap.keySet())
                    {
                        Integer index = titleMap.get(k);
                        if(row.getCell(index)!=null){
                            row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(index).getStringCellValue();
                            map.put(k, value);
                        }

                    }
                    list.add((T)map);

                }
                else
                {
                    t = clazz.newInstance();
                    int arrayIndex = 0;// 标识当前第几个数组了
                    int cellIndex = 0;// 标识当前读到这一行的第几个cell了
                    List<FieldForSortting> fields = sortFieldByAnno(clazz);
                    for (FieldForSortting ffs : fields)
                    {
                        Field field = ffs.getField();
                        field.setAccessible(true);
                        if (field.getType().isArray())
                        {
                            Integer count = arrayCount[arrayIndex];
                            Object[] value = null;
                            if (field.getType().equals(String[].class))
                            {
                                value = new String[count];
                            }
                            else
                            {
                                // 目前只支持String[]和Double[]
                                value = new Double[count];
                            }
                            for (int i = 0; i < count; i++)
                            {
                                Cell cell = row.getCell(cellIndex);
                                String errMsg = validateCell(cell, field, cellIndex);
                                if (StringUtils.isBlank(errMsg))
                                {
                                    value[i] = getCellValue(cell);
                                }
                                else
                                {
                                    log.append(errMsg);
                                    log.append(";");
                                    logs.setHasError(true);
                                }
                                cellIndex++;
                            }
                            field.set(t, value);
                            arrayIndex++;
                        }
                        else
                        {
                            Cell cell = row.getCell(cellIndex);
                            String errMsg = validateCell(cell, field, cellIndex);
                            if (StringUtils.isBlank(errMsg))
                            {
                                Object value = null;
                                // 处理特殊情况,Excel中的String,转换成Bean的Date
                                if (field.getType().equals(Date.class) && cell.getCellType() == Cell.CELL_TYPE_STRING)
                                {
                                    Object strDate = getCellValue(cell);
                                    try
                                    {
                                        value = new SimpleDateFormat(pattern).parse(strDate==null?"":strDate.toString());
                                    }
                                    catch (ParseException e)
                                    {

                                        errMsg =
                                                MessageFormat.format("the cell [{0}] can not be converted to a date ",
                                                        CellReference.convertNumToColString(cell.getColumnIndex()));
                                    }
                                }
                                else
                                {
                                    value = getCellValue(cell);
                                    // 处理特殊情况,excel的value为String,且bean中为其他,且defaultValue不为空,那就=defaultValue
                                    ExcelCell annoCell = field.getAnnotation(ExcelCell.class);
                                    if (value instanceof String && !field.getType().equals(String.class)
                                            && StringUtils.isNotBlank(annoCell.defaultValue()))
                                    {
                                        value = annoCell.defaultValue();
                                    }
                                }
                                field.set(t, value);
                            }
                            if (StringUtils.isNotBlank(errMsg))
                            {
                                log.append(errMsg);
                                log.append(";");
                                logs.setHasError(true);
                            }
                            cellIndex++;
                        }
                    }
                    list.add(t);
                    logList.add(new ExcelLog(t, log.toString(), row.getRowNum() + 1));
                }
            }
            logs.setLogList(logList);
            if(null != inputStream){
                try {
                    inputStream.close();
                }catch (Exception e){
                }
            }
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        return list;
    }




    /**
     * 一个excl 里存在多个sheet页，数据的抓取--适用于【南京绩效-财务通用报表】
     *
     * @param clazz vo的Class
     * @param inputStream excel输入流
     * @param pattern 如果有时间数据，设定输入格式。默认为"yyy-MM-dd"
     * @param logs 错误log集合
     * @param arrayCount 如果vo中有数组类型,那就按照index顺序,把数组应该有几个值写上.
     * @return voList
     * @throws RuntimeException
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> importExcelBySheetNum(String filename, Class<T> clazz, InputStream inputStream,
                                                String pattern, ExcelLogs logs,int sheetNum, Integer... arrayCount)
    {
        Workbook workBook = null;
        try
        {
            if (isExcel2003(filename))
            {
                workBook = new HSSFWorkbook(inputStream);
            }
            else
            {
                workBook = new XSSFWorkbook(inputStream);
            }
        }
        catch (IOException e)
        {
            LG.error(e.toString(), e);
        }
        List<T> list = new ArrayList<T>();
        Sheet sheet = workBook.getSheetAt(sheetNum);
        Iterator<Row> rowIterator = sheet.rowIterator();
        try
        {
            List<ExcelLog> logList = new ArrayList<ExcelLog>();
            // Map<title,index>
            Map<String, Integer> titleMap = new HashMap<String, Integer>();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0)
                {
                    if (clazz == Map.class)
                    {
                        // 解析map用的key,就是excel标题行
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Integer index = 0;
                        while (cellIterator.hasNext())
                        {
                            String value = cellIterator.next().getStringCellValue();
                            titleMap.put(value, index);
                            index++;
                        }
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null)
                    {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull)
                {
                    LG.warn("Excel row " + row.getRowNum() + " all row value is null!");
                    continue;
                }
                T t = null;
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (String k : titleMap.keySet())
                    {
                        Integer index = titleMap.get(k);
                        if(row.getCell(index)!=null){
                            row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(index).getStringCellValue();
                            map.put(k, value);
                        }

                    }
                    list.add((T)map);

                }
                else
                {
                    t = clazz.newInstance();
                    int arrayIndex = 0;// 标识当前第几个数组了
                    int cellIndex = 0;// 标识当前读到这一行的第几个cell了
                    List<FieldForSortting> fields = sortFieldByAnno(clazz);
                    for (FieldForSortting ffs : fields)
                    {
                        Field field = ffs.getField();
                        field.setAccessible(true);
                        if (field.getType().isArray())
                        {
                            Integer count = arrayCount[arrayIndex];
                            Object[] value = null;
                            if (field.getType().equals(String[].class))
                            {
                                value = new String[count];
                            }
                            else
                            {
                                // 目前只支持String[]和Double[]
                                value = new Double[count];
                            }
                            for (int i = 0; i < count; i++)
                            {
                                Cell cell = row.getCell(cellIndex);
                                String errMsg = validateCell(cell, field, cellIndex);
                                if (StringUtils.isBlank(errMsg))
                                {
                                    value[i] = getCellValue(cell);
                                }
                                else
                                {
                                    log.append(errMsg);
                                    log.append(";");
                                    logs.setHasError(true);
                                }
                                cellIndex++;
                            }
                            field.set(t, value);
                            arrayIndex++;
                        }
                        else
                        {
                            Cell cell = row.getCell(cellIndex);
                            String errMsg = validateCell(cell, field, cellIndex);
                            if (StringUtils.isBlank(errMsg))
                            {
                                Object value = null;
                                // 处理特殊情况,Excel中的String,转换成Bean的Date
                                if (field.getType().equals(Date.class) && cell.getCellType() == Cell.CELL_TYPE_STRING)
                                {
                                    Object strDate = getCellValue(cell);
                                    try
                                    {
                                        value = new SimpleDateFormat(pattern).parse(strDate==null?"":strDate.toString());
                                    }
                                    catch (ParseException e)
                                    {

                                        errMsg =
                                                MessageFormat.format("the cell [{0}] can not be converted to a date ",
                                                        CellReference.convertNumToColString(cell.getColumnIndex()));
                                    }
                                }
                                else
                                {
                                    value = getCellValue(cell);
                                    // 处理特殊情况,excel的value为String,且bean中为其他,且defaultValue不为空,那就=defaultValue
                                    ExcelCell annoCell = field.getAnnotation(ExcelCell.class);
                                    if (value instanceof String && !field.getType().equals(String.class)
                                            && StringUtils.isNotBlank(annoCell.defaultValue()))
                                    {
                                        value = annoCell.defaultValue();
                                    }
                                }
                                field.set(t, value);
                            }
                            if (StringUtils.isNotBlank(errMsg))
                            {
                                log.append(errMsg);
                                log.append(";");
                                logs.setHasError(true);
                            }
                            cellIndex++;
                        }
                    }
                    list.add(t);
                    logList.add(new ExcelLog(t, log.toString(), row.getRowNum() + 1));
                }
            }
            logs.setLogList(logList);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        return list;
    }
    
    public static <T> Collection<T> importExcelForKhzb(String filename, Class<T> clazz, InputStream inputStream,
            String pattern, ExcelLogs logs, Integer... arrayCount){
    	Integer[] jump = new Integer[] {0,1,2};
    	Integer[] title = new Integer[] {3};
    	Collection<List<T>> result = importExcelForSegment(filename,clazz,inputStream,pattern,logs,jump,title,arrayCount);
    	return result.iterator().next();
    }
    
    public static <T> Collection<List<T>> importExcelForKhfa(String filename, Class<T> clazz, InputStream inputStream,
            String pattern, ExcelLogs logs, Integer... arrayCount){
    	Integer[] jump = new Integer[] {0,1,2,5,6,13,14};
    	Integer[] title = new Integer[] {3,7,15};
    	Collection<List<T>> result = importExcelForSegment(filename,clazz,inputStream,pattern,logs,jump,title,arrayCount);
    	return result;
    }
    
    /**
     * 导入方法
     * @param <T>
     * @param filename
     * @param clazz
     * @param inputStream
     * @param pattern
     * @param logs
     * @param jump 跳过的行数
     * @param title 标题行数
     * @param arrayCount
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<List<T>> importExcelForSegment(String filename, Class<T> clazz, InputStream inputStream,
                                                String pattern, ExcelLogs logs, Integer[] jump, Integer[] title, Integer... arrayCount){
    	List<Integer> jumpRow = Arrays.asList(jump);
    	List<Integer> titleRow = Arrays.asList(title);
        Workbook workBook = null;
        try{
            if (isExcel2003(filename)){
                workBook = new HSSFWorkbook(inputStream);
            }else{
                workBook = new XSSFWorkbook(inputStream);
            }
        }catch (IOException e){
            LG.error(e.toString(), e);
        }
        List<List<T>> allData = new ArrayList<List<T>>(titleRow.size());
		for (int i = 0; i < titleRow.size(); i++) {
			allData.add(new ArrayList<T>());
		}
        
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        try{
            List<ExcelLog> logList = new ArrayList<ExcelLog>();
            // 标题map
            Map<String, Integer> titleMap = new HashMap<String, Integer>();
            int segment = -1;
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(jumpRow.contains(row.getRowNum())) continue;
                if (titleRow.contains(row.getRowNum())){//标题行
                    if (clazz == Map.class){
                    	getTitle(row,titleMap);
                    	segment++;
                    }
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null){
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull){
                    LG.warn("Excel row " + row.getRowNum() + " all row value is null!");
                    continue;
                }
                StringBuilder log = new StringBuilder();
                if (clazz == Map.class){
                    Map<String, Object> map = new HashMap<String, Object>();//每一行的值
                    for (String k : titleMap.keySet()){
                        Integer index = titleMap.get(k);
                        if(row.getCell(index)!=null){
                            row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
                            String value = row.getCell(index).getStringCellValue();
                            map.put(k, value);
                        }
                    }
                    allData.get(segment).add((T)map);
                }
            }
            logs.setLogList(logList);
        }
        catch (Exception e){
            throw new RuntimeException(MessageFormat.format("can not instance class:{0}", clazz.getSimpleName()), e);
        }
        return allData;
    }
    
    //获取标题
    private static void getTitle(Row row,Map<String, Integer> titleMap) {
    	titleMap.clear();
    	 Iterator<Cell> cellIterator = row.cellIterator();
         Integer index = 0;
         while (cellIterator.hasNext()){
             String value = cellIterator.next().getStringCellValue();
             if(!StringUtils.isBlank(value)) {
            	 titleMap.put(value, index);
             }
             index++;
         }
    }
    /**
           * 导出，带有单元格合并功能
     * @param <T>
     * @param headers
     * @param dataset
     * @param out
     */
    public static <T> void exportMyExcelForMergeCells(String[] headers, List<T> dataset, OutputStream out,String title)
    {
    	List<int[]> ejflMerges = new ArrayList<int[]>();
    	List<int[]> yjflMerges = new ArrayList<int[]>();
    	Map<Integer,int[]> map = new HashMap<Integer, int[]>();
    	//扣除汇总
    	List<T> subList = dataset.subList(0, dataset.size() - 1);
    	String ejflmc = (String)((Map)subList.get(0)).get(headers[1]);
    	int index = 0;
    	for(int j=0;j<subList.size();j++) {
    		T t = subList.get(j);
    		if(t instanceof Map) {
    			Map record = (Map)t;
    			String mc = (String)record.get(headers[1]);//二级分类名称
    			if(ejflmc.equals(mc)) {//存在相同名称
    				int[] a = {index+1,j+1};
    				map.put(index+1, a);
    			}else {//不存在相同名称
    				ejflmc = mc;
    				int[] a = {j+1,j+1};
    				map.put(j+1, a);
    				index = j;
    			}
    		}
    	}
    	for(Map.Entry<Integer, int[]> entry : map.entrySet()) {
    		ejflMerges.add(entry.getValue());
    	}
    	
    	map.clear();
    	index = 0;
    	String yjflmc = (String)((Map)subList.get(0)).get(headers[0]);
    	for(int j=0;j<subList.size();j++) {
    		T t = subList.get(j);
    		if(t instanceof Map) {
    			Map record = (Map)t;
    			String mc = (String)record.get(headers[0]);//一级分类名称
    			if(yjflmc.equals(mc)) {//存在相同名称
    				int[] a = {index+1,j+1};
    				map.put(index+1, a);
    			}else {//不存在相同名称
    				yjflmc = mc;
    				int[] a = {j+1,j+1};
    				map.put(j+1, a);
    				index = j;
    			}
    		}
    	}
    	System.out.println(map);
    	for(Map.Entry<Integer, int[]> entry : map.entrySet()) {
    		yjflMerges.add(entry.getValue());
    	}
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        writeToMySheetForMergeCells(yjflMerges,ejflMerges,workbook,sheet, headers, dataset, null,title);
        try{
            workbook.write(out);
        }catch (IOException e){
            LG.error(e.toString(), e);
        }
    }
    
    private static <T> void writeToMySheetForMergeCells(List<int[]> yjflMerge,List<int[]> ejflMerge,XSSFWorkbook xssfWorkbook,XSSFSheet sheet, String[] headers, Collection<T> dataset, String pattern,String title)
    {
    	for(int[] array : yjflMerge) {
            if(array[0] !=array[1]){
                sheet.addMergedRegion(new CellRangeAddress(array[0]+1,array[1]+1,0,0));
            }
    	}
    	for(int[] array : ejflMerge) {
    	    if(array[0] !=array[1]){
                sheet.addMergedRegion(new CellRangeAddress(array[0]+1,array[1]+1,1,1));
            }
    	}

        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setBold(true);
        XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
        cellStyle.setFont(xssfFont);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        XSSFCellStyle cellStyleContent = xssfWorkbook.createCellStyle();
        cellStyleContent.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleContent.setAlignment(HorizontalAlignment.CENTER);
        cellStyleContent.setBorderBottom(BorderStyle.THIN);
        cellStyleContent.setBorderLeft(BorderStyle.THIN);
        cellStyleContent.setBorderRight(BorderStyle.THIN);
        cellStyleContent.setBorderTop(BorderStyle.THIN);

        //产生标题
        XSSFFont xssfFontTitle = xssfWorkbook.createFont();
        xssfFontTitle.setFontName("宋体");
        xssfFontTitle.setFontHeightInPoints((short) 14);
        XSSFCellStyle cellStyleTitle = xssfWorkbook.createCellStyle();
        cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTitle.setBorderBottom(BorderStyle.THIN);
        cellStyleTitle.setBorderLeft(BorderStyle.THIN);
        cellStyleTitle.setBorderRight(BorderStyle.THIN);
        cellStyleTitle.setBorderTop(BorderStyle.THIN);
        cellStyleTitle.setFont(xssfFontTitle);
        XSSFRow rowTitle = sheet.createRow(0);
        XSSFCell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue(title);
        cellTitle.setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));
        
        // 产生表格标题行
        XSSFRow row = sheet.createRow(1);
        row.setHeight((short)(20*20));
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn((short)i);
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);
        }

        if(dataset == null)
            return;

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 1;
        XSSFCell cell = null;
        Object value = null;
        Map<String, Object> map = null;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);
            T t = (T)it.next();
            try
            {
                if (t instanceof Map)
                {
                    map = (Map<String, Object>)t;
                    int cellNum = 0;
                    for (String k : headers)
                    {
                        if (map.containsKey(k) == false)
                        {
                            LG.error("Map 中 不存在 key [" + k + "]");
                            continue;
                        }
                        value = map.get(k);
                        cell = row.createCell(cellNum);
                        if(value == null){
                            cell.setCellValue("");
                        }else{
                            cell.setCellValue(String.valueOf(value));
                            cell.setCellStyle(cellStyleContent);
                        }
                        cellNum++;
                    }
                }
            }
            catch (Exception e)
            {
                LG.error(e.toString(), e);
            }
        }
        // 设定自动宽度
        for (int i = 0; i < headers.length; i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,headers[i].getBytes().length*256+184);//设置列名自动长度
        }
    }
}
