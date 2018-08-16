package com.stylefeng.guns.common.log;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.MessageFormat;

/**
 * @author gjb
 * @Title
 * @Description: log4j日志工具类, 方便直接输出日志信息，不用每个类都声明logger日志对象
 * @Created 2018-06-29
 */
public class Log4jUtil {

    /**
     * 获取最原始被调用的堆栈信息
     *
     * @return
     */
    private static StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        if (null == callStack) {
            return null;
        }
        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;
        // 日志类名称
        String logClassName = Log4jUtil.class.getName();
        // 循环遍历到日志类标识
        boolean isEachLogClass = false;

        // 遍历堆栈信息，获取出最原始被调用的方法信息
        for (StackTraceElement strackTraceEle : callStack) {
            // 遍历到日志类
            if (logClassName.equals(strackTraceEle.getClassName())) {
                isEachLogClass = true;
            }
            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogClass) {
                if (!logClassName.equals(strackTraceEle.getClassName())) {
                    isEachLogClass = false;
                    caller = strackTraceEle;
                    break;
                }
            }
        }
        return caller;
    }


    /**
     * 自动匹配请求类名，生成logger对象，此处 logger name 值为 [className].[methodName]() Line: [fileLine]
     *
     * @return
     * @author yzChen
     * @date 2016年10月13日 下午11:50:59
     */
    private static Logger logger() {
        StackTraceElement caller = findCaller();//最原始被调用的堆栈对象
        if (caller == null) {
            return Logger.getLogger(Log4jUtil.class);
        } else {
            return Logger.getLogger(caller.getClassName() + "." + caller.getMethodName() + "() Line: " + caller.getLineNumber());
        }
    }

    public static void trace(String msg) {
        trace(msg, null);
    }

    public static void trace(String msg, Throwable e) {
        logger().trace(msg, e);
    }

    public static void debug(String msg) {
        debug(msg, null);
    }

    public static void debug(String msg, Throwable e) {
        logger().debug(msg, e);
    }

    public static void info(String msg) {
        info(null, msg, null);
    }

    public static void info(String msg, Object... params) {
        info(null, msg, params);
    }

    public static void info(Throwable e, String msg, Object... params) {
        logger().info(format(msg, params), e);
    }

    public static void warn(String msg) {
        warn(msg, null);
    }

    public static void warn(String msg, Throwable e) {
        logger().warn(msg, e);
    }

    public static void error(String msg) {
        error(msg, null);
    }

    public static void error(String msg, Object... params) {
        error(format(msg, params), null);
    }

    public static void error(Throwable e, String msg, Object... params) {
        logger().error(format(msg, params), e);
    }

    /**
     * 日志信息参数化格式化
     *
     * @param message log信息,如:<code>xxx{0},xxx{1}...</code>
     * @param params  log格式化参数,数组length与message参数化个数相同, 如:
     *                <code>Object[]  object=new Object[]{"xxx","xxx"}</code>
     */
    private static String format(String message, Object... params) {
        if (params != null && params.length != 0) {
            return messageFormat(message, params);
        }
        return message;

    }

    /**
     * 格式化消息信息
     *
     * @param message
     * @param args
     * @return消息信息 x
     */
    private static String messageFormat(String message, Object... args) {
        try {
            if (StringUtils.isBlank(message)) {
                return "";
            }
            String str = MessageFormat.format(message, args);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            /*logger.error(
                    "Log日志工具类messageFormat(String message,Object... args)发生异常:",
                    e);*/
        }
        return "";
    }
}