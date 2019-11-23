package com.linsh.base.common;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/04
 *    desc   :
 * </pre>
 */
public interface Consumer<T> extends io.reactivex.functions.Consumer<T> {

    @Override
    void accept(T t) throws Exception;
}
