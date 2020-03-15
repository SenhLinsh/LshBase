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
public abstract class MvpCallAdapter {

    private MvpCallAdapter parent;

    protected abstract void onBind(Contract.Presenter delegatedPresenter, Contract.View delegatedView, MvpCallExecutor callExecutor);

    void setParent(MvpCallAdapter adapter) {
        parent = adapter;
    }

    protected Object handlePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.handlePresenterMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }

    protected Object handleViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.handleViewMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }

    protected Object invokePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.invokePresenterMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }

    protected Object invokeViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.invokeViewMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }
}
