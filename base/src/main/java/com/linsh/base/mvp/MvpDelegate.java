package com.linsh.base.mvp;

import android.content.Context;

import com.linsh.base.LshLog;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.ListUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   : MVP 线程切换代理
 *
 *             使用代理模式, 将 Presenter 层和 View 层的接口调用线程进行隔离, View 层接口默认分配
 *             主线程进行调用, Presenter 层接口默认分配后台线程(MvpPresenterThread)进行调用, 其中
 *             调用的线程, 可以在接口定义处(Contract)使用注解进行指定.
 *
 *             注: 由于返回值具有时效性, 因为如果在 Contract 中定义了具有返回值的接口, 将不会进行线程
 *             切换, 而是使用原来的线程进行调用. 因此, 在该模式下的 MVP 接口定义时, 应尽量避免使用
 *             具有返回值的接口.
 * </pre>
 */
class MvpDelegate<P extends Contract.Presenter, V extends Contract.View> {

    private static final String TAG = "MvpDelegate";
    private P delegatedPresenter;
    private V delegatedView;
    private P originPresenter;
    private V originView;

    private final DelegatedPresenterInvocationHandler presenterInvocationHandler = new DelegatedPresenterInvocationHandler();
    private final DelegatedViewInvocationHandler viewInvocationHandler = new DelegatedViewInvocationHandler();
    private MvpCallExecutor callExecutor;
    private MvpCallAdapter callAdapter;

    public MvpDelegate(P presenter, V view) {
        this.originPresenter = presenter;
        this.originView = view;
        this.delegatedPresenter = delegatePresenter();
        this.delegatedView = delegateView();
        // 定义 callAdapter 最顶层的 parent
        this.callAdapter = new TransThreadCallAdapter() {

            @Override
            Object abstractInvokePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
                // 交给最外层的 callAdapter 进行推进
                return callAdapter.invokePresenterMethod(proxy, method, args);
            }

            @Override
            Object abstractInvokeViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
                // 交给最外层的 callAdapter 进行推进
                return callAdapter.invokeViewMethod(proxy, method, args);
            }

            @Override
            protected Object invokePresenterMethod(Object proxy, Method method, Object[] args) throws Throwable {
                if ((originPresenter == null || proxy != originPresenter) && !method.getName().equals("detachView")) {
                    LshLog.d(TAG, "try to invoke presenter method: " + method.getName() + "(), but origin presenter is not attached, ignore it.");
                    return null;
                }
                // 最终执行方法调用
                try {
                    return method.invoke(proxy, args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }

            @Override
            public Object invokeViewMethod(Object proxy, Method method, Object[] args) throws Throwable {
                if ((originView == null || proxy != originView) && !method.getName().equals("detachPresenter")) {
                    // View detach 后, getContext() 方法仍可返回 App Context
                    if (method.getName().equals("getContext") && method.getReturnType() == Context.class) {
                        return ContextUtils.get();
                    }
                    LshLog.d(TAG, "try to invoke view method: " + method.getName() + "(), but origin view is not attached, ignore it.");
                    return null;
                }
                // 最终执行方法调用
                try {
                    return method.invoke(proxy, args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        };
    }

    public void setOriginPresenter(P presenter) {
        this.originPresenter = presenter;
        this.delegatedPresenter.detachView();
        this.delegatedPresenter = delegatePresenter();
        this.delegatedView = delegateView();
        // 通知 call adapter 更新绑定 presenter
        MvpCallAdapter callAdapter = this.callAdapter;
        while (callAdapter != null) {
            callAdapter.onBind(delegatedPresenter, delegatedView, callExecutor);
            callAdapter = callAdapter.getParent();
        }
        attach();
    }

    public void setOriginView(V view) {
        this.originView = view;
        this.delegatedView.detachPresenter();
        this.delegatedPresenter = delegatePresenter();
        this.delegatedView = delegateView();
        // 通知 call adapter 更新绑定 view
        MvpCallAdapter callAdapter = this.callAdapter;
        while (callAdapter != null) {
            callAdapter.onBind(delegatedPresenter, delegatedView, callExecutor);
            callAdapter = callAdapter.getParent();
        }
        attach();
    }

    public V getView() {
        return delegatedView;
    }

    public P getPresenter() {
        return delegatedPresenter;
    }

    public void attach() {
        delegatedPresenter.attachView(delegatedView);
        delegatedView.attachPresenter(delegatedPresenter);
    }

    public void detach() {
        delegatedPresenter.detachView();
        delegatedView.detachPresenter();
        originPresenter = null;
        originView = null;
    }

    public void addCallAdapter(MvpCallAdapter adapter) {
        if (callExecutor == null) {
            callExecutor = new MvpCallExecutor() {
                @Override
                public void invoke(Object proxy, Method method, Object[] args) {
                    LshLog.v(TAG, "invoke method by call adapter, proxy: " + proxy.getClass().getSimpleName() + ", method: " + method.getName());
                    if (proxy == delegatedPresenter) {
                        try {
                            presenterInvocationHandler.invoke(delegatedPresenter, method, args);
                        } catch (Throwable e) {
                            LshLog.w(TAG, "invoke presenter method by call executor throw an error, presenter: "
                                    + proxy.getClass().getSimpleName() + ", method: " + method.getName(), e);
                        }
                    } else if (proxy == delegatedView) {
                        try {
                            viewInvocationHandler.invoke(delegatedView, method, args);
                        } catch (Throwable e) {
                            LshLog.w(TAG, "invoke view method by call executor throw an error, view: "
                                    + proxy.getClass().getSimpleName() + ", method: " + method.getName(), e);
                        }
                    } else {
                        LshLog.d(TAG, "can not find declaring class of invoke method: " + method.getName());
                    }
                }
            };
        }
        adapter.setParent(this.callAdapter);
        callAdapter = adapter;
        adapter.onBind(delegatedPresenter, delegatedView, callExecutor);
    }

    /**
     * 代理 View 层实例, 在 Presenter 调用 View 层接口时, 如果运行线程为非 UI 线程, 则将方法调用转移到 UI 线程进行调用.
     * <p>
     * 注意: 如果方法存在返回值, 将不会自动切换线程, 而是继续在当前的线程进行调用.
     */
    private <T extends Contract.View> T delegateView() {
        Set<Class<?>> interfaces = new HashSet<>();
        Class<?> viewClass = originView.getClass();
        while (viewClass != null) {
            Class<?>[] curInterfaces = viewClass.getInterfaces();
            for (Class<?> anInterface : curInterfaces) {
                if (Contract.View.class.isAssignableFrom(anInterface)) {
                    interfaces.add(anInterface);
                }
            }
            viewClass = viewClass.getSuperclass();
        }
        LshLog.v(TAG, "delegatedView: new proxy instance for Interfaces: " + ListUtils.toString(interfaces));
        return (T) Proxy.newProxyInstance(originView.getClass().getClassLoader(),
                interfaces.toArray(new Class[interfaces.size()]), viewInvocationHandler);
    }

    /**
     * 代理 Presenter 层实例, 在 View 调用 Presenter 层接口时, 如果运行线程为 UI 线程, 则将方法调用转移到后台线程(默认为MvpPresenterThread)进行调用.
     * <p>
     * 注意: 如果方法存在返回值, 将不会自动切换线程, 而是继续在当前的线程进行调用.
     */
    private <T extends Contract.Presenter> T delegatePresenter() {
        Class<?>[] interfaces = new Class[1];
        Class<?> presenterClass = originPresenter.getClass();
        out:
        while (presenterClass != null) {
            Class<?>[] curInterfaces = presenterClass.getInterfaces();
            for (Class<?> anInterface : curInterfaces) {
                if (Contract.Presenter.class.isAssignableFrom(anInterface)) {
                    interfaces[0] = anInterface;
                    // interface 取第一个就好
                    break out;
                }
            }
            presenterClass = presenterClass.getSuperclass();
        }
        LshLog.v(TAG, "delegatedPresenter: new proxy instance for Interfaces: " + Arrays.toString(interfaces));
        return (T) Proxy.newProxyInstance(originPresenter.getClass().getClassLoader(),
                interfaces, presenterInvocationHandler);
    }

    /**
     * DelegatedView 接口代理执行逻辑类
     * <p>
     * 逻辑: 将 DelegatedView 的方法交给 callAdapter 进行调度处理
     */
    private class DelegatedViewInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (originView == null || proxy != delegatedView) {
                // View detach 后, getContext() 方法仍可返回 App Context
                if (method.getName().equals("getContext") && method.getReturnType() == Context.class) {
                    return ContextUtils.get();
                }
                LshLog.d(TAG, "try to delegate view method: " + method.getName() + "(), but origin view is not attached, ignore it.");
                return null;
            }
            LshLog.v(TAG, "delegatedView, view: " + originView.getClass().getSimpleName()
                    + ", method: " + method.getName() + ", thread: " + Thread.currentThread().getName());
            return callAdapter.handleViewMethod(originView, method, args);
        }
    }

    /**
     * DelegatedPresenter 接口代理执行逻辑类
     * <p>
     * 逻辑: 将 DelegatedPresenter 的方法交给 callAdapter 进行调度处理
     */
    private class DelegatedPresenterInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (originPresenter == null || proxy != delegatedPresenter) {
                LshLog.d(TAG, "try to delegate presenter method: " + method.getName() + "(), but origin presenter is not attached, ignore it.");
                return null;
            }
            LshLog.v(TAG, "delegatedPresenter: presenter=" + originPresenter.getClass().getSimpleName()
                    + ", method=" + method.getName() + ", thread: " + Thread.currentThread().getName());
            return callAdapter.handlePresenterMethod(originPresenter, method, args);
        }
    }
}
