package com.linsh.base.activity;

import android.app.Activity;
import android.content.Intent;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/06
 *    desc   :
 * </pre>
 */
public interface ActivityManager {

    ActivityDelegate delegate(Activity activity);

    IntentDelegate intent();

    IntentDelegate intent(Class<? extends Activity> target);

    IntentDelegate intent(Intent intent);
}
