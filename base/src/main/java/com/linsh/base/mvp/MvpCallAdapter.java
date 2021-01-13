package com.linsh.base.mvp;

import java.lang.reflect.Method;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/03/15
 *    desc   : 调用调度适配器
 *
 *             可以根据实际场景来选择是否调用, 如何调用 View 与 Presenter 之间定义的接口方法
 * </pre>
 */
public abstract class MvpCallAdapter {

    private MvpCallAdapter parent;

    protected abstract void onBind(Contract.Presenter delegatedPresenter, Contract.View delegatedView, MvpCallExecutor callExecutor);

    void setParent(MvpCallAdapter adapter) {
        parent = adapter;
    }

    MvpCallAdapter getParent() {
        return parent;
    }


    /**
     * 处理 Presenter 方法
     * <p>
     * 用于定制 Presenter 接口方法的调度
     */
    protected Object handlePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.handlePresenterMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }

    /**
     * 处理 View 方法
     * <p>
     * 用于定制 View 接口方法的调度
     */
    protected Object handleViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.handleViewMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }

    /**
     * 执行 Presenter 方法
     * <p>
     * 用于定制 Presenter 接口方法的实现
     */
    protected Object invokePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.invokePresenterMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }

    /**
     * 执行 View 方法
     * <p>
     * 用于定制 View 接口方法的实现
     */
    protected Object invokeViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (parent != null) {
            return parent.invokeViewMethod(proxy, method, args);
        }
        throw new RuntimeException("illegal state, please check");
    }
}
