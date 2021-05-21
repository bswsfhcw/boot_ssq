package com.winning.batjx.core.common.util;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with 基础框架项目
 * USER: 项鸿铭
 * DATE: 2017/9/21.
 * TIME: 8:51.
 * winning_frame  公式计算引擎
 */
public class JexlUtils {


    private static Log log = LogFactory.getLog(JexlUtils.class);

    public static final JexlEngine JEXL = new Engine();

    public static final String GS_FLG = "WINNING";

    public static final String PATTERN = "[^.\\d][0-9]+/|^[0-9]+/|[(][0-9]+[)]";

    public static String calculate(String script){
        JexlContext jc = new MapContext();
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(script);
        while (matcher.find()) {
            String bm = matcher.group(0);
            String s1=   bm.substring(0,bm.length()-1);
            String s2 =  bm.substring(bm.length()-1,bm.length());
            script = script.replace(bm,s1+".0"+s2);
        }
        Foo foo = new Foo();
        JexlScript e = JEXL.createScript(JexlUtils.script+script);
        jc.set("foo", foo);
        Object o = "0";
        try {
             o = e.execute(jc);
        }catch (Exception ex){
            log.error(e);
        }
        return o == null ? "0" : o.toString();
    }

    public static void main(String[] args) {
        System.out.println(calculate("{(12)*12-123}"));
    }

    public static final String script = "var toFix3 = function(vl) { return foo.toFix3(vl)}; var toFix2 = function(vl) { return foo.toFix2(vl)}; var toFix1 = function(vl)" +
            " { return foo.toFix1(vl)}; var floor = function(vl) { return foo.floor(vl)}; var abs = function(vl) { return foo.abs(vl)};  var sigma = function(vl) { return jxjs.sigma(vl)};  var avg = function(vl) { return jxjs.avg(vl)};  var t1 = function(vl) { return jxjs.t1(vl)}; var t2 = function(vl) { return jxjs.t2(vl)};";

    public static class Foo {


        public BigDecimal toFix3(Object bj){
            DecimalFormat df = new DecimalFormat("#.000");
            return  new BigDecimal(df.format(bj));
        }

        public BigDecimal toFix2(Object bj){
            DecimalFormat df = new DecimalFormat("#.00");
            return  new BigDecimal(df.format(bj));
        }

        public BigDecimal toFix1(Object bj){
            DecimalFormat df = new DecimalFormat("#.0");
            return  new BigDecimal(df.format(bj));
        }


        public BigDecimal floor(Object bj){
            DecimalFormat df = new DecimalFormat("#");
            return  new BigDecimal(df.format(bj));
        }

        public BigDecimal abs(Object bj){
            return BigDecimal.valueOf(Math.abs(new Double(bj.toString()))).setScale(5,   BigDecimal.ROUND_HALF_UP);
        }


    }


}
