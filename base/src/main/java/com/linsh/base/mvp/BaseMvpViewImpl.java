package com.linsh.base.mvp;

import android.content.Context;

import com.linsh.base.LshLog;
import com.linsh.utilseverywhere.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/02/25
 *    desc   :
 * </pre>
 */
public abstract class BaseMvpViewImpl<P extends Contract.Presenter> implements Contract.View {

    private static final String TAG = "BaseMvpViewImpl";
    private MvpDelegate<P, Contract.View> mvpDelegate;
    private HashMap<Class, MvpDelegate> minorMvpDelegates;
    private Context context;

    protected void attachView(Context context) {
        this.context = context;
        mvpDelegate = new MvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attachView();
        initMinorMvpDelegates();
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

    protected P getPresenter() {
        return mvpDelegate.getPresenter();
    }

    protected <T extends Contract.Presenter> T getMinorPresenter(Class<T> clazzOfPresenter) {
        if (minorMvpDelegates != null) {
            return (T) minorMvpDelegates.get(clazzOfPresenter).getPresenter();
        }
        return null;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
