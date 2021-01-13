package com.linsh.base.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.linsh.base.LshActivity;
import com.linsh.base.LshLog;
import com.linsh.base.activity.base.BaseActivity;
import com.linsh.utilseverywhere.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private MvpDelegate<P, Contract.View> mvpDelegate;
    private HashMap<Class, MvpDelegate> minorMvpDelegates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpDelegate = new MvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attachView();
        initMinorMvpDelegates();
    }

    protected P initContractPresenter() {
        P presenter = null;
        // 通过 Intent 传递设置 Presenter
        Class<? extends Contract.Presenter> extra = LshActivity.intent(getIntent()).getPresenter();
        if (extra != null) {
            try {
                presenter = (P) ClassUtils.newInstance(extra, true);
                LshLog.i(TAG, "initialize presenter from intent: " + extra);
            } catch (Exception e) {
                throw new IllegalArgumentException("initialize presenter from intent failed: " + extra, e);
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

    private void initMinorMvpDelegates() {
        // 通过注解设置 MinorPresenter
        MinorPresenter annotation = getClass().getAnnotation(MinorPresenter.class);
        if (annotation != null) {
            try {
                minorMvpDelegates = new HashMap<>();
                Class<? extends Contract.Presenter>[] presenters = annotation.value();
                for (int i = 0; i < presenters.length; i += 2) {
                    Contract.Presenter presenter = (Contract.Presenter) ClassUtils.newInstance(presenters[i + 1], true);
                    MvpDelegate delegate = new MvpDelegate<>(presenter, this);
                    minorMvpDelegates.put(presenters[i], delegate);
                    delegate.attachView();
                }
                LshLog.d(TAG, "initialize minor presenters from annotation: " + Arrays.toString(annotation.value()));
            } catch (Exception e) {
                throw new RuntimeException("initialize minor presenters from annotation failed: " + Arrays.toString(annotation.value()), e);
            }
        }
    }

    protected void setContractPresenter(P presenter) {
        mvpDelegate.setOriginPresenter(presenter);
    }

    protected void setContractView(Contract.View view) {
        mvpDelegate.setOriginView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpDelegate.detachView();
        if (minorMvpDelegates != null) {
            for (Map.Entry<Class, MvpDelegate> entry : minorMvpDelegates.entrySet()) {
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
        MvpDelegate mvpDelegate = minorMvpDelegates.get(classOfPresenter);
        if (mvpDelegate == null) {
            throw new RuntimeException("无法找到该 presenter 的实例, 请确认是否已正确初始化. clazzOfPresenter: " + classOfPresenter);
        }
        return (T) mvpDelegate.getPresenter();
    }

    protected void addMvpCallAdapter(MvpCallAdapter callAdapter) {
        mvpDelegate.addCallAdapter(callAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
