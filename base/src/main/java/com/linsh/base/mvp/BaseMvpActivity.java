package com.linsh.base.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.linsh.base.LshActivity;
import com.linsh.base.LshLog;
import com.linsh.base.activity.base.BaseActivity;
import com.linsh.utilseverywhere.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
    private ArrayList<MvpDelegate<Contract.Presenter, Contract.View>> minorMvpDelegates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化类中声明的 MVP Contract (主 MVP)
        mvpDelegate = new MvpDelegate<>(initContractPresenter(), initContractView());
        mvpDelegate.attach();
        // 初始化字段中声明的 MVP Contract (辅 MVP)
        initMinorMvpDelegates();
    }

    /**
     * 初始化 Contract.Presenter, 默认通过 Class 的注解 @Presenter 来设置, 也可以通过该方法来进行覆盖
     */
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

    /**
     * 初始化 Contract.View, 默认为当前 Activity, 也可以通过该方法来进行覆盖
     */
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
                    Contract.Presenter presenter = (P) ClassUtils.newInstance(value, true);
                    MvpDelegate<Contract.Presenter, Contract.View> mvpDelegate = new MvpDelegate<>(presenter, (Contract.View) this);
                    ClassUtils.setField(this, field.getName(), mvpDelegate.getPresenter(), true);
                    if (minorMvpDelegates == null) {
                        minorMvpDelegates = new ArrayList<>();
                    }
                    minorMvpDelegates.add(mvpDelegate);
                    mvpDelegate.attach();
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
    protected void onDestroy() {
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
