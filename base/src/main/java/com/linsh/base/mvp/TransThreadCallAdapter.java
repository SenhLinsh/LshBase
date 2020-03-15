package com.linsh.base.mvp;

import com.linsh.base.LshLog;
import com.linsh.base.LshThread;
import com.linsh.base.thread.ThreadPolicy;
import com.linsh.utilseverywhere.ThreadUtils;

import java.lang.reflect.Method;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/03/15
 *    desc   :
 * </pre>
 */
abstract class TransThreadCallAdapter extends MvpCallAdapter {

    private static final String TAG = "TransThreadCallAdapter";
    private final Contract.Presenter originPresenter;
    private final Contract.View originView;

    public TransThreadCallAdapter(Contract.Presenter originPresenter, Contract.View originView) {
        this.originPresenter = originPresenter;
        this.originView = originView;
    }

    @Override
    protected void onBind(Contract.Presenter delegatedPresenter, Contract.View delegatedView, MvpCallExecutor callExecutor) {
    }

    @Override
    protected Object handlePresenterMethod(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (method.getReturnType() == void.class) {
            MvpThread mvpThread = method.getAnnotation(MvpThread.class);
            ThreadPolicy policy = null;
            if (mvpThread != null) {
                policy = mvpThread.value();
                LshLog.d(TAG, "delegate presenter in selected thread: " + policy);
            }
            if (policy == null) {
                policy = ThreadPolicy.PRESENTER;
                LshLog.v(TAG, "delegate presenter in default thread: " + policy);
            }
            LshThread.run(policy, new Runnable() {
                @Override
                public void run() {
                    try {
                        abstractInvokePresenterMethod(proxy, method, args);
                    } catch (Throwable e) {
                        throw new RuntimeException("delegate presenter method " + method.toString() + " throw an exception: ", e);
                    }
                }
            });
            return null;
        }
        if (method.getReturnType() == void.class) {
            LshLog.d(TAG, "delegate presenter with a deprecated return type: " + method.getReturnType() + ", method: " + method.getName());
        }
        if (ThreadUtils.isMainThread()) {
            LshLog.d(TAG, "delegate presenter in a deprecated thread: " + Thread.currentThread().getName() + ", method: " + method.getName());
        }
        return abstractInvokePresenterMethod(proxy, method, args);
    }

    @Override
    protected Object handleViewMethod(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (method.getReturnType() == void.class) {
            MvpThread mvpThread = method.getAnnotation(MvpThread.class);
            ThreadPolicy policy = null;
            if (mvpThread != null) {
                policy = mvpThread.value();
                LshLog.d(TAG, "delegate presenter in selected thread: " + policy);
            }
            if (policy == null) {
                policy = ThreadPolicy.UI;
                LshLog.v(TAG, "delegate presenter in default thread: " + policy);
            }
            LshThread.run(policy, new Runnable() {
                @Override
                public void run() {
                    try {
                        abstractInvokeViewMethod(proxy, method, args);
                    } catch (Throwable e) {
                        throw new RuntimeException("delegate delegatedView method " + method.toString() + " throw an exception: ", e);
                    }
                }
            });
            return null;
        }
        if (method.getReturnType() == void.class) {
            LshLog.d(TAG, "delegate view with a deprecated return type: " + method.getReturnType() + ", method: " + method.getName());
        }
        if (!ThreadUtils.isMainThread()) {
            LshLog.d(TAG, "delegate view in a deprecated thread: " + Thread.currentThread().getName() + ", method: " + method.getName());
        }
        return abstractInvokeViewMethod(proxy, method, args);
    }

    abstract Object abstractInvokePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable;

    abstract Object abstractInvokeViewMethod(Object proxy, Method method, Object[] args) throws Throwable;

    @Override
    protected Object invokePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(originPresenter, args);
    }

    @Override
    public Object invokeViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(originView, args);
    }
}
