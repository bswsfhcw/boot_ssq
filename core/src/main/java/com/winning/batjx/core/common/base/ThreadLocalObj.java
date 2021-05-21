package com.winning.batjx.core.common.base;

public class ThreadLocalObj {

    private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();



    public static void setObj(Object obj) {
        threadLocal.set(obj);
    }

    public static Object getObj() {
        return threadLocal.get();
    }

    public static void removeObj() {
        threadLocal.remove();
    }


}
