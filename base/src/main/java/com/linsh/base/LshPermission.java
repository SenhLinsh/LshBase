package com.linsh.base;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.linsh.base.activity.ActivitySubscribe;
import com.linsh.lshutils.utils.IdUtilsEx;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.PermissionUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/03/29
 *    desc   :
 * </pre>
 */
public class LshPermission {

    private LshPermission() {
    }

    /**
     * 检查权限
     *
     * @param permission 用户权限
     * @return
     */
    public static boolean checkPermission(final String permission) {
        return ContextCompat.checkSelfPermission(ContextUtils.get(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请权限, 在这里申请权限使用了静默申请, 不会弹出提示窗口
     *
     * @param activity   activity
     * @param permission 用户权限
     */
    public static void requestPermission(final Activity activity, final String permission, final PermissionListener listener) {
        final int code = IdUtilsEx.generateId();
        if (listener != null) {
            LshActivity.delegate(activity)
                    .subscribe(new ActivitySubscribe.OnRequestPermissionsResult() {
                        @Override
                        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                            if (requestCode == code && permissions[0].equals(permission)) {
                                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                    listener.onGranted(permission);
                                } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                                    listener.onDenied(permission, false);
                                } else {
                                    listener.onDenied(permission, true);
                                }
                                LshActivity.delegate(activity).unsubscribe(this);
                            }
                        }

                        @Override
                        public void attach(Activity activity) {
                        }
                    });
        }
        PermissionUtils.requestPermission(activity, permission, code);
    }

    /**
     * 检查并申请权限, 如果检查权限时发现没有获取到权限, 则尝试申请
     * <p>
     * 在这里申请权限使用了静默申请, 不会弹出提示窗口
     *
     * @param permission
     * @return
     */
    public static boolean checkAndRequestPermission(Activity activity, String permission, PermissionListener listener) {
        if (!checkPermission(permission)) {
            requestPermission(activity, permission, listener);
            return false;
        }
        return true;
    }

    public interface PermissionListener {

        void onGranted(String permission);

        void onDenied(String permission, boolean isNeverAsked);
    }
}
