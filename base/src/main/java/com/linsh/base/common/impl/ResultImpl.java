package com.linsh.base.common.impl;


import com.linsh.base.common.Result;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   :
 * </pre>
 */
public class ResultImpl implements Result {

    private final int code;
    private final String message;
    private final Throwable throwable;

    public ResultImpl() {
        this(0, null, null);
    }

    public ResultImpl(int code, String message) {
        this(code, message, null);
    }

    public ResultImpl(int code, String message, Throwable throwable) {
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }
}
