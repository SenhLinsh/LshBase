package com.linsh.base.activity.impl;

import android.app.Activity;
import android.content.Intent;

import com.linsh.base.activity.ActivityDelegate;
import com.linsh.base.activity.ActivityManager;
import com.linsh.base.activity.IDelegateActivity;
import com.linsh.base.activity.IntentDelegate;


/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/21
 *    desc   :
 * </pre>
 */
public class ActivityManagerImpl implements ActivityManager {

    @Override
    public ActivityDelegate delegate(Activity activity) {
        if (!(activity instanceof IDelegateActivity))
            throw new IllegalArgumentException("无法使用未实现 " + IDelegateActivity.class.getName() + " 的 Activity");
        ActivityDelegate delegate = ((IDelegateActivity) activity).getActivityDelegate();
        if (delegate == null) {
            delegate = new ActivityDelegateImpl(activity);
            ((IDelegateActivity) activity).delegateActivity(delegate);
        }
        return delegate;
    }

    @Override
    public IntentDelegate intent() {
        return new IntentDelegateImpl();
    }

    @Override
    public IntentDelegate intent(Class<? extends Activity> target) {
        return new IntentDelegateImpl(target);
    }

    @Override
    public IntentDelegate intent(Intent intent) {
        return new IntentDelegateImpl(intent);
    }
}
