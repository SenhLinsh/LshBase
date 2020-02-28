package com.linsh.base.mvp;

import android.content.Context;
import android.os.Bundle;

import com.linsh.base.LshActivity;
import com.linsh.base.LshLog;
import com.linsh.base.activity.base.BaseActivity;
import com.linsh.utilseverywhere.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/06
 *    desc   :
 * </pre>
 */
public abstract class BaseMvpActivity<P extends Contract.Presenter> extends BaseActivity implements Contract.View {

    private static final String TAG = "BaseMvpActivity";
    private TransThreadMvpDelegate<P, Contract.View> mvpDelegate;
    private HashMap<Class, TransThreadMvpDelegate> minorMvpDelegates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpDelegate = new TransThreadMvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attachView();
        initMinorMvpDelegates();
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

    private void initMinorMvpDelegates() {
        // 通过注解设置 MinorPresenter
        MinorPresenter annotation = getClass().getAnnotation(MinorPresenter.class);
        if (annotation != null) {
            try {
                minorMvpDelegates = new HashMap<>();
                Class<? extends Contract.Presenter>[] presenters = annotation.value();
                for (int i = 0; i < presenters.length; i += 2) {
                    Contract.Presenter presenter = (Contract.Presenter) ClassUtils.newInstance(presenters[i + 1], true);
                    TransThreadMvpDelegate delegate = new TransThreadMvpDelegate<>(presenter, this);
                    minorMvpDelegates.put(presenters[i], delegate);
                    delegate.attachView();
                }
                LshLog.d(TAG, "initialize minor presenters from annotation: " + Arrays.toString(annotation.value()));
            } catch (Exception e) {
                throw new RuntimeException("initialize minor presenters from annotation failed: " + Arrays.toString(annotation.value()), e);
            }
        }
    }

    protected Contract.View initContractView() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpDelegate.detachView();
        if (minorMvpDelegates != null) {
            for (Map.Entry<Class, TransThreadMvpDelegate> entry : minorMvpDelegates.entrySet()) {
                entry.getValue().detachView();
            }
        }
    }

    protected P getPresenter() {
        return mvpDelegate.getPresenter();
    }

    protected <T extends Contract.Presenter> T getMinorPresenter(Class<T> classOfPresenter) {
        if (minorMvpDelegates == null) {
            throw new RuntimeException("请使用 @MinorPresenter 注解初始化 MinorPresenter, clazzOfPresenter: " + classOfPresenter);
        }
        TransThreadMvpDelegate mvpDelegate = minorMvpDelegates.get(classOfPresenter);
        if (mvpDelegate == null) {
            throw new RuntimeException("无法找到该 presenter 的实例, 请确认是否已正确初始化. clazzOfPresenter: " + classOfPresenter);
        }
        return (T) mvpDelegate.getPresenter();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
