package com.linsh.base;

import android.os.HandlerThread;

import com.linsh.base.config.LogConfig;
import com.linsh.base.log.ILogger;
import com.linsh.base.log.impl.Logger;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/04
 *    desc   : Log 打印和储存模块
 *
 *            LshLog 根据配置的级别, 可以分为输出到 logcat 和文件的方式. 详情配置内容见 {@link LogConfig}.
 *                  默认输出到 logcat 的级别为 VERBOSE 以上, 输出到文件的级别为 INFO 以上, 通过 WARN 级别
 *                  以上会单独输出到 log_error 文件夹下.
 * </pre>
 */
public class LshLog {

    public static final int VERBOSE = ILogger.VERBOSE;
    public static final int DEBUG = ILogger.DEBUG;
    public static final int INFO = ILogger.INFO;
    public static final int WARN = ILogger.WARN;
    public static final int ERROR = ILogger.ERROR;
    public static final int FATAL = ILogger.FATAL;

    private static final long DEFAULT_MAX_FILE_SIZE = 500 * 1024L;

    private static final ILogger logger;

    static {
        LogConfig logConfig = LshConfig.get(LogConfig.class);
        HandlerThread handlerThread = new HandlerThread("YxLoggerFileThread");
        handlerThread.start();

        String defaultTag = logConfig.defaultTag;
        int printToLogcatLevel = logConfig.printToLogcatLevel;
        int printToLogFileLevel = logConfig.printToLogFileLevel;
        int printToErrorFileLevel = logConfig.printToErrorFileLevel;
        long maxFileSize = logConfig.maxFileSize > 0 ? logConfig.maxFileSize : DEFAULT_MAX_FILE_SIZE;

        logger = new Logger(handlerThread, defaultTag, printToLogcatLevel,
                printToLogFileLevel, printToErrorFileLevel, maxFileSize);
    }

    private LshLog() {
    }

    /**
     * VERBOSE 级别日志
     * <p>
     * 用于打印可能需要通过 logcat 查看的信息, 级别最低, 默认不会输出到本地日志文件
     */
    public static void v(@Nullable String message) {
        logger.v(message);
    }

    /**
     * VERBOSE 级别日志
     * <p>
     * 用于打印可能需要通过 logcat 查看的信息, 级别最低, 默认不会输出到本地日志文件
     */
    public static void v(@Nullable String tag, @Nullable String message) {
        logger.v(tag, message);
    }

    /**
     * VERBOSE 级别日志
     * <p>
     * 用于打印可能需要通过 logcat 查看的信息, 级别最低, 默认不会输出到本地日志文件
     */
    public static void v(@Nullable String message, @Nullable Throwable throwable) {
        logger.v(message, throwable);
    }

    /**
     * VERBOSE 级别日志
     * <p>
     * 用于打印可能需要通过 logcat 查看的信息, 级别最低, 默认不会输出到本地日志文件
     */
    public static void v(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        logger.v(tag, message, throwable);
    }

    /**
     * DEBUG 级别日志
     * <p>
     * 用于打印需要使用 logcat 进行查看或开发调试的信息, 默认不会输出到本地日志文件
     */
    public static void d(@Nullable String message) {
        logger.d(message);
    }

    /**
     * DEBUG 级别日志
     * <p>
     * 用于打印需要使用 logcat 进行查看或开发调试的信息, 默认不会输出到本地日志文件
     */
    public static void d(@Nullable String tag, @Nullable String message) {
        logger.d(tag, message);
    }

    /**
     * DEBUG 级别日志
     * <p>
     * 用于打印需要使用 logcat 进行查看或开发调试的信息, 默认不会输出到本地日志文件
     */
    public static void d(@Nullable String message, @Nullable Throwable throwable) {
        logger.d(message, throwable);
    }

    /**
     * DEBUG 级别日志
     * <p>
     * 用于打印需要使用 logcat 进行查看或开发调试的信息, 默认不会输出到本地日志文件
     */
    public static void d(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        logger.d(tag, message, throwable);
    }

    /**
     * INFO 级别日志
     * <p>
     * 用于打印需要进行追踪业务流程或记录的信息, 默认会输出到本地日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void i(@Nullable String message) {
        logger.i(message);
    }

    /**
     * INFO 级别日志
     * <p>
     * 用于打印需要进行追踪业务流程或记录的信息, 默认会输出到本地日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void i(@Nullable String tag, @Nullable String message) {
        logger.i(tag, message);
    }

    /**
     * INFO 级别日志
     * <p>
     * 用于打印需要进行追踪业务流程或记录的信息, 默认会输出到本地日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void i(@Nullable String message, @Nullable Throwable throwable) {
        logger.i(message, throwable);
    }

    /**
     * INFO 级别日志
     * <p>
     * 用于打印需要进行追踪业务流程或记录的信息, 默认会输出到本地日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void i(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        logger.i(tag, message, throwable);
    }

    /**
     * WARN 级别日志
     * <p>
     * 用于打印警告的消息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void w(@Nullable String message) {
        logger.w(message);
    }

    /**
     * WARN 级别日志
     * <p>
     * 用于打印警告的消息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void w(@Nullable String tag, @Nullable String message) {
        logger.w(tag, message);
    }

    /**
     * WARN 级别日志
     * <p>
     * 用于打印警告的消息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void w(@Nullable String message, @Nullable Throwable throwable) {
        logger.w(message, throwable);
    }

    /**
     * WARN 级别日志
     * <p>
     * 用于打印警告的消息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void w(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        logger.w(tag, message, throwable);
    }

    /**
     * ERROR 级别日志
     * <p>
     * 用于打印异常和错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void e(@Nullable String message) {
        logger.e(message);
    }

    /**
     * ERROR 级别日志
     * <p>
     * 用于打印异常和错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void e(@Nullable String tag, @Nullable String message) {
        logger.e(tag, message);
    }

    /**
     * ERROR 级别日志
     * <p>
     * 用于打印异常和错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void e(@Nullable String message, @Nullable Throwable throwable) {
        logger.e(message, throwable);
    }

    /**
     * ERROR 级别日志
     * <p>
     * 用于打印异常和错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void e(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        logger.e(tag, message, throwable);
    }

    /**
     * FATAL 级别日志
     * <p>
     * 用于打印可能导致应用或系统发生致命性 BUG 或错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void fatal(@Nullable String message) {
        logger.fatal(message);
    }

    /**
     * FATAL 级别日志
     * <p>
     * 用于打印可能导致应用或系统发生致命性 BUG 或错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void fatal(@Nullable String tag, @Nullable String message) {
        logger.fatal(tag, message);
    }

    /**
     * FATAL 级别日志
     * <p>
     * 用于打印可能导致应用或系统发生致命性 BUG 或错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void fatal(@Nullable String message, @Nullable Throwable throwable) {
        logger.fatal(message, throwable);
    }

    /**
     * FATAL 级别日志
     * <p>
     * 用于打印可能导致应用或系统发生致命性 BUG 或错误的信息, 默认会输出到本地日志文件和错误日志文件
     * <p>
     * 本地日志文件路径: sdcard/imprexion/logger/<timestamp>/log/<package_name>@<yyyyMMddHHmmss>.log
     * 错误日志文件路径: sdcard/imprexion/logger/<timestamp>/log_error/<timestamp>/<package_name>@<yyyyMMddHHmmss>.log
     */
    public static void fatal(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        logger.fatal(tag, message, throwable);
    }
}
