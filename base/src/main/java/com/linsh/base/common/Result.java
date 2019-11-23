package com.linsh.base.common;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
public interface Result {

    int getCode();

    String getMessage();

    Throwable getThrowable();
}
