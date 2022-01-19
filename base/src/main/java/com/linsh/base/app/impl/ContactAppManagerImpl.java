package com.linsh.base.app.impl;

import android.app.Activity;
import android.content.Intent;

import com.linsh.base.app.IContactAppManager;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.ToastUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/01/19
 *    desc   :
 * </pre>
 */
public class ContactAppManagerImpl implements IContactAppManager {

    @Override
    public void gotoSearch(Activity activity, int requestCode) {
        Intent intent = new Intent(ACTION_SEARCH)
                .putExtra(EXTRA_SEARCH_TYPE, EXTRA_SEARCH_TYPE_PERSON)
                .setPackage(PACKAGE_NAME);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }
}
