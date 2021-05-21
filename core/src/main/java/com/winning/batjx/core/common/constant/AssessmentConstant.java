package com.winning.batjx.core.common.constant;

/**
 * @program: boot-batjx
 * @description 考核问卷相关常量
 * @author: yyh
 * @create: 2021-03-08 09:43
 **/
public class AssessmentConstant {

    /**
     * 问卷模板状态
     *
     * **/
    public enum  TemplateStatusEnum{
        STOP(0,"停用"),START(1,"启用");
        private int code;
        private String msg;

        TemplateStatusEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
