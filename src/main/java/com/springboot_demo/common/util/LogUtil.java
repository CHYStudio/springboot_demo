package com.springboot_demo.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName LogUtil
 * @Description: TODO  logback 日志就工具
 * @Author Administrator
 * @Date 2020/6/11
 * @Version V1.0
 **/
public class LogUtil {

    /**
     * 获得日志记录类
     *
     * @param T
     * @return object
     */
    public static Object getLogger(Class T) {
        Logger logger = LogManager.getLogger(T);
        return logger;
    }

    /**
     * 打印info日志
     *
     * @param o
     * @param info
     */
    public static void prinLogInfo(Object o, String info) {
        ((Logger) o).info(info);
    }

    public static void prinLogInfo(Object o, Object info) {
        ((Logger) o).info(info);
    }

    /**
     * 打印error日志
     *
     * @param o
     * @param error
     */
    public static void prinLogError(Object o, String error) {
        ((Logger) o).error(error);
    }

    public static void prinLogError(Object o, Object error) {
        ((Logger) o).error(error);
    }

    /**
     * 打印debug日志
     *
     * @param o
     * @param debug
     */
    public static void printLogDebug(Object o, String debug) {
        ((Logger) o).debug(debug);
    }

    public static void printLogDebug(Object o, Object debug) {
        ((Logger) o).debug(debug);
    }

}
