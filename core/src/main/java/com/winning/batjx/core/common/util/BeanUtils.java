package com.winning.batjx.core.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * 对象操作的相关方法
 * @author sam.qian
 *
 */
public class BeanUtils {

	/**
	 * 根据属性名称获取属性值
	 * @param fieldName
	 * @param o
	 * @return
	 */
	public static Object getFieldValueByName(String fieldName, Object o) {    
	    try {    
	        String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	        String getter = "get" + firstLetter + fieldName.substring(1);    
	        Method method = o.getClass().getMethod(getter, new Class[] {});    
	        Object value = method.invoke(o, new Object[] {});    
	        return value;    
	    } catch (Exception e) {    
	        System.out.println("属性不存在");    
	        return null;    
	    }    
	}
	
	/**
	 * 获取对象的所有属性名称
	 * @param o
	 * @return
	 */
	public static String[] getFiledName(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	    String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++){  
	        fieldNames[i]=fields[i].getName();  
	    }  
	    return fieldNames;  
	}
}
