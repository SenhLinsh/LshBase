package com.linsh.base.app.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.linsh.base.app.ITextAppManager;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.IntentUtils;
import com.linsh.utilseverywhere.ToastUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   :
 * </pre>
 */
public class DefaultTextAppManager implements ITextAppManager {

    private static final String PACKAGE_NAME = "com.linsh.text";
    private static final String ACTIVITY_NAME_TEXT_EDIT = "com.linsh.text.page.text.TextEditActivity";

    @Override
    public void launch() {
        Intent intent = IntentUtils.getLaunchAppIntent(PACKAGE_NAME);
        if (intent != null && intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            ContextUtils.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void launch(Context context) {
        Intent intent = IntentUtils.getLaunchAppIntent(PACKAGE_NAME);
        if (intent != null && intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppManager.EXTRA_TEXT_EDIT_PATH, filePath);
            ContextUtils.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, Context context) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppManager.EXTRA_TEXT_EDIT_PATH, filePath);
            context.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, Activity activity, int requestCode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppManager.EXTRA_TEXT_EDIT_PATH, filePath);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditText(String text) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppManager.EXTRA_TEXT_EDIT_TEXT, text);
            ContextUtils.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditText(String text, Context context) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppManager.EXTRA_TEXT_EDIT_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditText(String text, Activity activity, int requestCode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppManager.EXTRA_TEXT_EDIT_TEXT, text);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }
}
