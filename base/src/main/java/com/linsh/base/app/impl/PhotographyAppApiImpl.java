package com.linsh.base.app.impl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;

import com.linsh.base.app.IAppConnection;
import com.linsh.base.app.IPhotographyAppApi;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.ToastUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/01/21
 *    desc   :
 * </pre>
 */
public class PhotographyAppApiImpl implements IPhotographyAppApi {

    @Override
    public void gotoPhotoList(Activity activity, String path, String[] filters) {
        Intent intent = new Intent()
                .setClassName(PACKAGE_NAME, ACTIVITY_NAME_MAIN)
                .putExtra(EXTRA_PATH, path)
                .putExtra(EXTRA_FILTERS, filters)
                .putExtra(EXTRA_TYPE, EXTRA_TYPE_DEFAULT)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public void gotoPhotoSelector(Activity activity, String path, String[] filters, int requestCode) {
        Intent intent = new Intent()
                .setClassName(PACKAGE_NAME, ACTIVITY_NAME_MAIN)
                .putExtra(EXTRA_PATH, path)
                .putExtra(EXTRA_FILTERS, filters)
                .putExtra(EXTRA_TYPE, EXTRA_TYPE_SELECTOR);
        if (intent.resolveActivity(ContextUtils.getPackageManager()) != null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("无法找到该页面");
        }
    }

    @Override
    public <T extends IInterface> void connectService(IAppConnection<T> connection) {
        // 根据 action 打开并连接服务
        Intent intent = new Intent(SERVICE_ACTION);
        intent.setPackage(PACKAGE_NAME);
        ContextUtils.startService(intent);
        ContextUtils.get().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                connection.onServiceConnected(name.getPackageName(), (T) service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                connection.onServiceDisconnected(name.getPackageName());
            }
        }, 0);
    }
}
