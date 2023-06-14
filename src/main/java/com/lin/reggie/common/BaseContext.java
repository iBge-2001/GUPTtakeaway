package com.lin.reggie.common;

/**
 * 工具类ThreadLocal,每个线程的用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static long getCurrentId(){
        return threadLocal.get();
    }
}
