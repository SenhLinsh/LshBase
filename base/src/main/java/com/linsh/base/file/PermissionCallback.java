package com.linsh.base.file;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/04
 *    desc   :
 * </pre>
 */
public interface PermissionCallback {

    void onGranted(String permission);

    void onDenied(String permission, boolean isNeverAsked);
}
