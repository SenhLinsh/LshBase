package com.linsh.base.mvp;

import android.content.Context;
import android.os.Bundle;

import com.linsh.base.LshActivity;
import com.linsh.base.LshLog;
import com.linsh.base.activity.base.BaseActivity;
import com.linsh.utilseverywhere.ClassUtils;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   :
 * </pre>
 */
public abstract class BaseMvpActivity<P extends Contract.Presenter> extends BaseActivity implements Contract.View<P> {

    private static final String TAG = "BaseMvpActivity";
    private TransThreadMvpDelegate<P, Contract.View> mvpDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpDelegate = new TransThreadMvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attachView();
    }

    protected P initContractPresenter() {
        P presenter = null;
        // 通过 Intent 传递设置 Presenter
        Class<? extends Contract.Presenter> extra = LshActivity.intent(getIntent()).getPresenter();
        if (extra != null) {
            try {
                presenter = (P) ClassUtils.newInstance(extra);
                LshLog.i(TAG, "initialize presenter from intent: " + extra);
            } catch (Exception e) {
                LshLog.w(TAG, "initialize presenter from intent failed: " + extra);
            }
        }
        if (presenter != null) return presenter;
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
        throw new RuntimeException("请通过 Intent 或注解设置 Presenter");
    }

    protected Contract.View initContractView() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpDelegate.detachView();
    }

    @Override
    public P getPresenter() {
        return mvpDelegate.getPresenter();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
