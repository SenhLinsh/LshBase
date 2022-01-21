package com.linsh.base.app.impl;

import android.app.Activity;
import android.content.Intent;

import com.linsh.base.app.IContactAppApi;
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
public class ContactAppApiImpl implements IContactAppApi {

    @Override
    public void gotoSearch(Activity activity, int requestCode) {
        Intent intent = new Intent()
                .setClassName(PACKAGE_NAME, ACTIVITY_NAME_SEARCH)
                .putExtra(EXTRA_SEARCH_TYPE, EXTRA_SEARCH_TYPE_PERSON);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }
}
