package com.linsh.base.activity.impl;

import android.app.Activity;

import com.linsh.base.activity.ActivityDelegate;
import com.linsh.base.activity.ActivitySubscribe;
import com.linsh.base.activity.IObservableActivity;
import com.linsh.base.activity.IntentDelegate;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
class ActivityDelegateImpl implements ActivityDelegate {

    private final IObservableActivity observableActivity;

    ActivityDelegateImpl(Activity activity) {
        if (!(activity instanceof IObservableActivity))
            throw new IllegalArgumentException("无法使用未实现 " + IObservableActivity.class.getName() + " 的 Activity");
        observableActivity = (IObservableActivity) activity;
    }

    @Override
    public ActivityDelegate subscribe(Class<? extends ActivitySubscribe> subscriber) {
        observableActivity.subscribe(subscriber);
        return this;
    }

    @Override
    public ActivityDelegate subscribe(ActivitySubscribe subscriber) {
        observableActivity.subscribe(subscriber);
        return this;
    }

    @Override
    public ActivityDelegate unsubscribe(Class<? extends ActivitySubscribe> subscriber) {
        observableActivity.unsubscribe(subscriber);
        return this;
    }

    @Override
    public ActivityDelegate unsubscribe(ActivitySubscribe subscriber) {
        observableActivity.unsubscribe(subscriber);
        return this;
    }

    @Override
    public <T extends ActivitySubscribe> T getSubscriber(Class<T> subscriber) {
        return observableActivity.subscribe(subscriber);
    }

    @Override
    public <T extends ActivitySubscribe> T useSubscriber(Class<T> subscriber) {
        return observableActivity.subscribe(subscriber);
    }

    @Override
    public <T extends ActivitySubscribe> T useSubscriber(T subscriber) {
        return observableActivity.subscribe(subscriber);
    }

    @Override
    public IntentDelegate intent() {
        return new IntentDelegateImpl().context((Activity) observableActivity);
    }
}
