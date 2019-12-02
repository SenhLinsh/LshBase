package com.linsh.base.base;

import com.linsh.base.activity.ActivityDelegate;
import com.linsh.base.activity.IDelegateActivity;
import com.linsh.base.activity.impl.ObservableActivity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/02
 *    desc   :
 * </pre>
 */
public class BaseActivity extends ObservableActivity implements IDelegateActivity {


    private ActivityDelegate delegate;

    @Override
    public void delegateActivity(ActivityDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public ActivityDelegate getActivityDelegate() {
        return delegate;
    }
}
