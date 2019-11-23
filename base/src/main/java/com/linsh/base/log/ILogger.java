package com.linsh.base.log;


import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/04
 *    desc   : 普通 Log 接口
 * </pre>
 */
public interface ILogger {

    int VERBOSE = 0;
    int DEBUG = 1;
    int INFO = 2;
    int WARN = 3;
    int ERROR = 4;
    int FATAL = 5;

    void v(@Nullable String message);

    void v(@Nullable String tag, @Nullable String message);

    void v(@Nullable String message, @Nullable Throwable throwable);

    void v(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void d(@Nullable String message);

    void d(@Nullable String tag, @Nullable String message);

    void d(@Nullable String message, @Nullable Throwable throwable);

    void d(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void i(@Nullable String message);

    void i(@Nullable String tag, @Nullable String message);

    void i(@Nullable String message, @Nullable Throwable throwable);

    void i(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void w(@Nullable String message);

    void w(@Nullable String tag, @Nullable String message);

    void w(@Nullable String message, @Nullable Throwable throwable);

    void w(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void e(@Nullable String message);

    void e(@Nullable String tag, @Nullable String message);

    void e(@Nullable String message, @Nullable Throwable throwable);

    void e(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void fatal(@Nullable String message);

    void fatal(@Nullable String tag, @Nullable String message);

    void fatal(@Nullable String message, @Nullable Throwable throwable);

    void fatal(@Nullable String tag, @Nullable String message, @Nullable Throwable throwable);
}
