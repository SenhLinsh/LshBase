package com.linsh.base.activity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/12/05
 *    desc   :
 * </pre>
 */
public interface IDelegateActivity {

    void delegateActivity(ActivityDelegate delegate);

    ActivityDelegate getActivityDelegate();
}
