package com.linsh.base;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.PermissionUtils;

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
    public static boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(ContextUtils.get(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请权限, 在这里申请权限使用了静默申请, 不会弹出提示窗口
     *
     * @param activity   activity
     * @param permission 用户权限
     */
    public static void requestPermission(Activity activity, String permission) {
        PermissionUtils.requestPermission(activity, permission, null);
    }

    /**
     * 检查并申请权限, 如果检查权限时发现没有获取到权限, 则尝试申请
     * <p>
     * 在这里申请权限使用了静默申请, 不会弹出提示窗口
     *
     * @param permission
     * @return
     */
    public static boolean checkAndRequestPermission(Activity activity, String permission) {
        if (!checkPermission(permission)) {
            requestPermission(activity, permission);
            return false;
        }
        return true;
    }
}
