package com.example.multipledatasource.base;

/**
 * @author : JJZheng
 * @since 2022/7/20 20:35
 */
public class ThreadLocalDatasource {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 将构造方法私有化
     */
    private ThreadLocalDatasource(){}

    static void setDatasourceKey(String datasourceKey){
        THREAD_LOCAL.set(datasourceKey);
    }
    static String getDatasourceKey(){
        return THREAD_LOCAL.get();
    }
    static void removeDatasourceKey(){
        THREAD_LOCAL.remove();
    }
}
