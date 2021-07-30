package com.linsh.base.mvp;

import android.app.Service;
import android.content.Context;

import com.linsh.base.LshLog;
import com.linsh.utilseverywhere.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
    private ArrayList<MvpDelegate<Contract.Presenter, Contract.View>> minorMvpDelegates;

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
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            Presenter annotation = field.getAnnotation(Presenter.class);
            if (annotation != null) {
                try {
                    Class<? extends Contract.Presenter> value = annotation.value();
                    Contract.Presenter presenter = (Contract.Presenter) ClassUtils.newInstance(value, true);
                    MvpDelegate<Contract.Presenter, Contract.View> mvpDelegate = new MvpDelegate<>(presenter, (Contract.View) this);
                    ClassUtils.setField(this, field.getName(), mvpDelegate.getPresenter(), true);
                    if (minorMvpDelegates == null) {
                        minorMvpDelegates = new ArrayList<>();
                    }
                    minorMvpDelegates.add(mvpDelegate);
                    LshLog.d(TAG, "initialize presenter from field annotation: " + annotation.value());
                } catch (Exception e) {
                    throw new RuntimeException("initialize presenter from field annotation failed: " + annotation.value(), e);
                }
            }
        }
    }

    /**
     * 设置 Main Presenter, 将替换原有的 Main Presenter
     *
     * @param presenter Contract.Presenter 对象
     */
    protected void setContractPresenter(P presenter) {
        mvpDelegate.setOriginPresenter(presenter);
    }

    /**
     * 设置 Main View, 将替换原有的 Main View
     *
     * @param view Contract.View 对象
     */
    protected void setContractView(Contract.View view) {
        mvpDelegate.setOriginView(view);
    }

    @Override
    public void attachPresenter(Contract.Presenter presenter) {
        LshLog.v(TAG, "attachPresenter: " + presenter);
    }

    @Override
    public void detachPresenter() {
        LshLog.v(TAG, "detachPresenter");
    }

    protected P getPresenter() {
        return mvpDelegate.getPresenter();
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
            for (MvpDelegate<Contract.Presenter, Contract.View> minorMvpDelegate : minorMvpDelegates) {
                minorMvpDelegate.detach();
            }
            minorMvpDelegates = null;
        }
    }

    protected void addMvpCallAdapter(MvpCallAdapter callAdapter) {
        mvpDelegate.addCallAdapter(callAdapter);
        if (minorMvpDelegates != null) {
            for (MvpDelegate<Contract.Presenter, Contract.View> minorMvpDelegate : minorMvpDelegates) {
                minorMvpDelegate.addCallAdapter(callAdapter);
            }
        }
    }
}
