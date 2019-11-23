package com.linsh.base;

import android.app.Activity;
import android.content.Intent;

import com.linsh.base.activity.ActivityDelegate;
import com.linsh.base.activity.ActivityManager;
import com.linsh.base.activity.IntentDelegate;
import com.linsh.base.activity.impl.LshActivityManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/11/23
 *    desc   :
 * </pre>
 */
public class LshActivity {

    private static ActivityManager manager = new LshActivityManager();

    public static ActivityDelegate delegate(Activity activity) {
        return manager.delegate(activity);
    }

    public static IntentDelegate intent() {
        return manager.intent();
    }

    public static IntentDelegate intent(Class<? extends Activity> target) {
        return manager.intent(target);
    }

    public static IntentDelegate intent(Intent intent) {
        return manager.intent(intent);
    }
}
