package com.linsh.base.log;


import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/05
 *    desc   : 日志输出适配器
 * </pre>
 */
public interface ILogAdapter {

    /**
     * Each log will use this pipeline
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param tag      is the given tag for the log message.
     * @param message  is the given message for the log message.
     */
    void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);
}