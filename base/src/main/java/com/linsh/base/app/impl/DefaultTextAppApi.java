package com.linsh.base.app.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.linsh.base.app.ITextAppApi;
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
public class DefaultTextAppApi implements ITextAppApi {

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
    public void gotoEditFile(String filePath, boolean isEditMode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_PATH, filePath);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            ContextUtils.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, boolean isEditMode, Context context) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_PATH, filePath);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            context.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, boolean isEditMode, Activity activity, int requestCode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_PATH, filePath);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, String text, boolean isEditMode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_PATH, filePath);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_TEXT, text);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            ContextUtils.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, String text, boolean isEditMode, Context context) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_PATH, filePath);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_TEXT, text);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            context.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditFile(String filePath, String text, boolean isEditMode, Activity activity, int requestCode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_PATH, filePath);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_TEXT, text);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    public void gotoEditText(String text, boolean isEditMode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_TEXT, text);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            ContextUtils.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditText(String text, boolean isEditMode, Context context) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_TEXT, text);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            context.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoEditText(String text, boolean isEditMode, Activity activity, int requestCode) {
        Intent intent = new Intent().setClassName(PACKAGE_NAME, ACTIVITY_NAME_TEXT_EDIT);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_TEXT, text);
            intent.putExtra(ITextAppApi.EXTRA_TEXT_EDIT_EDIT, isEditMode);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }
}
