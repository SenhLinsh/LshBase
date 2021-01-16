package com.linsh.base.mvp;

import android.app.Service;
import android.content.Context;

import com.linsh.base.LshLog;
import com.linsh.utilseverywhere.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/05/18
 *    desc   :
 * </pre>
 */
public abstract class BaseMvpService<P extends Contract.Presenter> extends Service implements Contract.View {

    private static final String TAG = "BaseMvpActivity";
    private MvpDelegate<P, Contract.View> mvpDelegate;
    private P delegatedPresenter;
    private HashMap<Class, MvpDelegate<P, Contract.View>> minorMvpDelegates;

    @Override
    public void onCreate() {
        super.onCreate();
        mvpDelegate = new MvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attach();
        initMinorMvpDelegates();
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
                    P presenter = (P) ClassUtils.newInstance(presenters[i + 1], true);
                    MvpDelegate<P, Contract.View> delegate = new MvpDelegate<>(presenter, (Contract.View) this);
                    minorMvpDelegates.put(presenters[i], delegate);
                    delegate.attach();
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
    public void attachPresenter(Contract.Presenter presenter) {
        delegatedPresenter = (P) presenter;
    }

    @Override
    public void detachPresenter() {
    }

    protected P getPresenter() {
        return delegatedPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvpDelegate.detach();
        if (minorMvpDelegates != null) {
            for (Map.Entry<Class, MvpDelegate<P, Contract.View>> entry : minorMvpDelegates.entrySet()) {
                entry.getValue().detach();
            }
        }
    }

    protected <T extends Contract.Presenter> T getMinorPresenter(Class<T> classOfPresenter) {
        if (minorMvpDelegates == null) {
            throw new RuntimeException("请使用 @MinorPresenter 注解初始化 MinorPresenter, clazzOfPresenter: " + classOfPresenter);
        }
        MvpDelegate<P, Contract.View> mvpDelegate = minorMvpDelegates.get(classOfPresenter);
        if (mvpDelegate == null) {
            throw new RuntimeException("无法找到该 presenter 的实例, 请确认是否已正确初始化. clazzOfPresenter: " + classOfPresenter);
        }
        return (T) mvpDelegate.getPresenter();
    }

    protected void addMvpCallAdapter(MvpCallAdapter callAdapter) {
        mvpDelegate.addCallAdapter(callAdapter);
    }
}
