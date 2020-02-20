package com.linsh.base.activity.base;

import android.app.Activity;
import android.os.Bundle;

import com.linsh.base.activity.impl.DelegateActivity;

import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/13
 *    desc   :
 * </pre>
 */
public abstract class BaseActivity extends DelegateActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Activity getActivity() {
        return this;
    }
}
