package com.linsh.base.common;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/04
 *    desc   :
 * </pre>
 */
public interface Callback<T> {

    void onSuccess(T t);

    void onFailed(Throwable throwable);
}
