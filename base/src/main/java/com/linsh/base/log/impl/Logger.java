package com.linsh.base.log.impl;

import android.os.HandlerThread;
import android.util.Log;

import com.linsh.base.log.ILogAdapter;
import com.linsh.base.log.ILogger;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/04
 *    desc   :
 * </pre>
 */
public class Logger implements ILogger {

    private static final String[] PRIORITY_LABELS = new String[]{"V", "D", "I", "W", "E", "Fatal"};
    private static final int[] LOG_PRIORITIES = new int[]{Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR, Log.ERROR};

    private ILogAdapter logcatAdapter;
    private ILogAdapter fileAdapter;

    private String defaultTag;

    public Logger(HandlerThread fileThread, String defaultTag,
                  int printToLogcatLevel, int printToLogFileLevel, int printToErrorFileLevel,
                  long maxFileSize) {
        this.defaultTag = defaultTag;
        this.logcatAdapter = new LogcatAdapter(printToLogcatLevel);
        this.fileAdapter = new FileAdapter(fileThread, new LogFileManager(maxFileSize), printToLogFileLevel, printToErrorFileLevel);
    }

    @Override
    public void v(@Nullable String message) {
        log(VERBOSE, null, message, null);
    }

    @Override
    public void v(@Nullable String tag, @Nullable String message) {
        log(VERBOSE, tag, message, null);
    }

    @Override
    public void v(@Nullable String message, @Nullable Throwable throwable) {
        log(VERBOSE, null, message, throwable);
    }

    @Override
    public void v(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        log(VERBOSE, tag, message, throwable);
    }

    @Override
    public void d(@Nullable String message) {
        log(DEBUG, null, message, null);
    }

    @Override
    public void d(@Nullable String tag, @Nullable String message) {
        log(DEBUG, tag, message, null);
    }

    @Override
    public void d(@Nullable String message, @Nullable Throwable throwable) {
        log(DEBUG, null, message, throwable);
    }

    @Override
    public void d(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        log(DEBUG, tag, message, throwable);
    }

    @Override
    public void i(@Nullable String message) {
        log(INFO, null, message, null);
    }

    @Override
    public void i(@Nullable String tag, @Nullable String message) {
        log(INFO, tag, message, null);
    }

    @Override
    public void i(@Nullable String message, @Nullable Throwable throwable) {
        log(INFO, null, message, throwable);
    }

    @Override
    public void i(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        log(INFO, tag, message, throwable);
    }

    @Override
    public void w(@Nullable String message) {
        log(WARN, null, message, null);
    }

    @Override
    public void w(@Nullable String tag, @Nullable String message) {
        log(WARN, tag, message, null);
    }

    @Override
    public void w(@Nullable String message, @Nullable Throwable throwable) {
        log(WARN, null, message, throwable);
    }

    @Override
    public void w(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        log(WARN, tag, message, throwable);
    }

    @Override
    public void e(@Nullable String message) {
        log(ERROR, null, message, null);
    }

    @Override
    public void e(@Nullable String tag, @Nullable String message) {
        log(ERROR, tag, message, null);
    }

    @Override
    public void e(@Nullable String message, @Nullable Throwable throwable) {
        log(ERROR, null, message, throwable);
    }

    @Override
    public void e(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        log(ERROR, tag, message, throwable);
    }

    @Override
    public void fatal(@Nullable String message) {
        log(FATAL, null, message, null);
    }

    @Override
    public void fatal(@Nullable String tag, @Nullable String message) {
        log(FATAL, tag, message, null);
    }

    @Override
    public void fatal(@Nullable String message, @Nullable Throwable throwable) {
        log(FATAL, null, message, throwable);
    }

    @Override
    public void fatal(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        log(FATAL, tag, message, throwable);
    }

    private void log(int priority, String tag, String message, Throwable throwable) {
        if (logcatAdapter != null) {
            logcatAdapter.log(priority, tag == null ? defaultTag : tag, message, throwable);
        }
        if (fileAdapter != null) {
            fileAdapter.log(priority, tag == null ? defaultTag : tag, message, throwable);
        }
    }

    static String getPriorityLabel(int priority) {
        return PRIORITY_LABELS[priority];
    }

    static int getLogPriority(int priority) {
        return LOG_PRIORITIES[priority];
    }
}
