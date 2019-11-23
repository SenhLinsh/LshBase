package com.linsh.base.file;

import android.app.Activity;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/16
 *    desc   :
 * </pre>
 */
public interface FileManager {

    FileBuilder file(File file);

    FileBuilder path(String path);

    FileBuilder sd(String filename);

    FileBuilder app(String filename);

    FileBuilder data(String filename);

    FileBuilder cache(String filename);

    boolean checkPermission();

    void checkOrRequestPermission(Activity activity);

    void checkOrRequestPermission(Activity activity, PermissionCallback callback);

    void requestPermission(Activity activity);
}
