package com.linsh.base.mvp;

import android.content.Context;

import com.linsh.base.LshLog;
import com.linsh.utilseverywhere.ClassUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/02/25
 *    desc   :
 * </pre>
 */
public abstract class BaseMvpViewImpl<P extends Contract.Presenter> implements Contract.View<P> {

    private static final String TAG = "BaseMvpViewImpl";
    private TransThreadMvpDelegate<P, Contract.View> mvpDelegate;
    private Context context;

    protected void attachView(Context context) {
        this.context = context;
        mvpDelegate = new TransThreadMvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attachView();
    }

    protected void detachView() {
        mvpDelegate.detachView();
    }

    protected P initContractPresenter() {
        P presenter = null;
        // 通过注解设置 Presenter
        Presenter annotation = getClass().getAnnotation(Presenter.class);
        if (annotation != null) {
            try {
                Class<? extends Contract.Presenter> value = annotation.value();
                presenter = (P) ClassUtils.newInstance(value, true);
                LshLog.d(TAG, "initialize presenter from annotation: " + annotation.value());
            } catch (Exception e) {
                throw new RuntimeException("initialize presenter from annotation failed: " + annotation.value(), e);
            }
        }
        if (presenter != null) return presenter;
        throw new RuntimeException("请通过重写 initContractPresenter() 或注解设置 Presenter");
    }

    protected Contract.View initContractView() {
        return this;
    }

    @Override
    public P getPresenter() {
        return mvpDelegate.getPresenter();
    }

    @Override
    public Context getContext() {
        return context;
    }
}
