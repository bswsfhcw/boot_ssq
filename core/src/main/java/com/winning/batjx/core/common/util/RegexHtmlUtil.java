package com.winning.batjx.core.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHtmlUtil {
	
	public static List<String> getInputSource(String content){
	        List<String> list = new ArrayList<String>();
	        //目前input标签标示有3种表达式
	        //<input alt="" src="1.jpg"/>   <input alt="" src="1.jpg"></input>     <input alt="" src="1.jpg">
	        //开始匹配content中的<input />标签
	        Pattern p_input = Pattern.compile("<(input|INPUT)(.*?)(/>|></input>|>)");
	        Matcher m_input = p_input.matcher(content);
	        boolean result_input = m_input.find();
	        if (result_input) {
	            while (result_input) {
	                //获取到匹配的<input />标签中的内容
	                String str_input = m_input.group(2);
	                 
	                //开始匹配<input />标签中的orgdatasource
	                Pattern p_src = Pattern.compile("(orgdatasource|ORGDATASOURCE)=(\"|\')(.*?)(\"|\')");
	                Matcher m_src = p_src.matcher(str_input);
	                if (m_src.find()) {
	                    String str_src = m_src.group(3);
	                    list.add(str_src);
	                }
	                //结束匹配<input />标签中的src
	                //匹配content中是否存在下一个<input />标签，有则继续以上步骤匹配<input />标签中的src
	                result_input = m_input.find();
	            }
	        }
	        return list;
	}
	
		public static List<String> getInputcolumn(String content){
	        List<String> list = new ArrayList<String>();
	        //目前input标签标示有3种表达式
	        //<input alt="" src="1.jpg"/>   <input alt="" src="1.jpg"></input>     <input alt="" src="1.jpg">
	        //开始匹配content中的<input />标签
	        Pattern p_input = Pattern.compile("<(input|INPUT)(.*?)(/>|></input>|>)");
	        Matcher m_input = p_input.matcher(content);
	        boolean result_input = m_input.find();
	        if (result_input) {
	            while (result_input) {
	                //获取到匹配的<input />标签中的内容
	                String str_input = m_input.group(2);
	                 
	                //开始匹配<input />标签中的orgdatasource
	                Pattern p_src = Pattern.compile("(orgdatacolumn|ORGDATACOLUMN)=(\"|\')(.*?)(\"|\')");
	                Matcher m_src = p_src.matcher(str_input);
	                if (m_src.find()) {
	                    String str_src = m_src.group(3);
	                    list.add(str_src);
	                }
	                //结束匹配<input />标签中的src
	                //匹配content中是否存在下一个<input />标签，有则继续以上步骤匹配<input />标签中的src
	                result_input = m_input.find();
	            }
	        }
	        return list;
	     }
		
		public static List<String> getInputValue(String content){
	        List<String> list = new ArrayList<String>();
	        //目前input标签标示有3种表达式
	        //<input alt="" src="1.jpg"/>   <input alt="" src="1.jpg"></input>     <input alt="" src="1.jpg">
	        //开始匹配content中的<input />标签
	        Pattern p_input = Pattern.compile("<(input|INPUT)(.*?)(/>|></input>|>)");
	        Matcher m_input = p_input.matcher(content);
	        boolean result_input = m_input.find();
	        if (result_input) {
	            while (result_input) {
	                //获取到匹配的<input />标签中的内容
	                String str_input = m_input.group(2);
	                 
	                //开始匹配<input />标签中的orgdatasource
	                Pattern p_src = Pattern.compile("(value|VALUE)=(\"|\')(.*?)(\"|\')");
	                Matcher m_src = p_src.matcher(str_input);
	                if (m_src.find()) {
	                    String str_src = m_src.group(3);
	                    list.add(str_src);
	                }
	                //结束匹配<input />标签中的src
	                //匹配content中是否存在下一个<input />标签，有则继续以上步骤匹配<input />标签中的src
	                result_input = m_input.find();
	            }
	        }
	        return list;
		}
		
		//获取sql中的参数  通过正则  提取sql的参数形式如  '${synd}' ${year}
		public static List<String> getSqlParams(String content){
	        List<String> list = new ArrayList<String>();
	        Pattern p_input = Pattern.compile("(\"|\')?(\\$\\{)(.*?)(\\})(\"|\')?");
	        Matcher m_input = p_input.matcher(content);
	        boolean result_input = m_input.find();
	        if (result_input) {
	            while (result_input) {
	                String str_input = m_input.group(3);
	                list.add(str_input);
	                result_input = m_input.find();
	            }
	        }
	        return list;
	     }
	     /**
	      * @author yxf@winning.com.cn in 2020/9/16
	      * @Description: 指标公式 ({0.0.9})/({0.0.10})*100 中提取0.0.9 和 0.0.10
	      **/
         public static List<String> getElementsGsbm(String content){
             List<String> list = new ArrayList<String>();
             Pattern p_input = Pattern.compile("\\{([^}]*)\\}");
             Matcher m_input = p_input.matcher(content);
             boolean result_input = m_input.find();
             if (result_input) {
                 while (result_input) {
                     String str_input = m_input.group(1);
                     list.add(str_input);
                     result_input = m_input.find();
                 }
             }
             return list;
         }


	public static String lastName(String filename) {
		if (filename.lastIndexOf(".") == -1) {
			return "";//文件没有后缀名的情况
		}
		//此时返回的是带有 . 的后缀名，
		return filename.substring(filename.lastIndexOf("."));

	}
		
		public static String replaceString(String searchStr,String str, String rstr, int a) {
			int index = str.indexOf(searchStr);
			int count = 1;
			while (count != a) {
				index = str.indexOf(searchStr, index + 1);
				count++;
			}

			return str.substring(0, index) + rstr + str.substring(index + 1);
		}

    public static void main(String[] args) {
        for (String s : getElementsGsbm("{0.0.9}*120*({0.0.9})/({0.0.10})*100")) {
            System.out.println(s);
        }
        String a = "{0.0.9}*120*({0.0.9})/({13})*1";
        a = a.replaceAll("0.0.9","12").replaceAll("\\{","").replaceAll("\\}","");
		System.out.println(a);

    }
}
