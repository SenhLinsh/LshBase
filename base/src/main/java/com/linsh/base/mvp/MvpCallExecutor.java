package com.linsh.base.mvp;

import java.lang.reflect.Method;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/03/15
 *    desc   :
 * </pre>
 */
public interface MvpCallExecutor {

    void invoke(Object proxy, Method method, Object[] args);
}
