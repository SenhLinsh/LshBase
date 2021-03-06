package com.linsh.base.activity.impl;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.linsh.base.activity.ActivityDelegate;
import com.linsh.base.activity.IDelegateActivity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/02
 *    desc   :
 * </pre>
 */
public abstract class DelegateActivity extends ObservableActivity implements IDelegateActivity {

    private ActivityDelegate delegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void delegateActivity(ActivityDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public ActivityDelegate getActivityDelegate() {
        return delegate;
    }
}
