package com.linsh.base;

import android.app.Activity;
import android.os.Environment;

import com.linsh.base.file.FileBuilder;
import com.linsh.base.config.FileConfig;
import com.linsh.base.file.FileManager;
import com.linsh.base.file.PermissionCallback;
import com.linsh.base.file.impl.LshFileManager;
import com.linsh.utilseverywhere.ContextUtils;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/11/23
 *    desc   :
 * </pre>
 */
public class LshFile {

    private static FileManager manager;
    private static final File APP_DIR_IN_TEXT;

    static {
        FileConfig config = LshConfig.get(FileConfig.class);
        manager = new LshFileManager(config);
        APP_DIR_IN_TEXT = new File(Environment.getExternalStorageDirectory(),
                "linsh/text/开发/linsh/data/" + ContextUtils.getPackageName());
    }

    public static FileBuilder file(File file) {
        return manager.file(file);
    }

    public static FileBuilder path(String path) {
        return manager.path(path);
    }

    public static FileBuilder sd(String filename) {
        return manager.sd(filename);
    }

    public static FileBuilder app(String filename) {
        return manager.app(filename);
    }

    public static FileBuilder dataForText(String filename) {
        return manager.file(new File(APP_DIR_IN_TEXT, filename));
    }

    public static FileBuilder data(String filename) {
        return manager.data(filename);
    }

    public static FileBuilder cache(String filename) {
        return manager.cache(filename);
    }

    public static boolean checkPermission() {
        return manager.checkPermission();
    }

    public static void checkOrRequestPermission(Activity activity) {
        manager.checkOrRequestPermission(activity);
    }

    public static void checkOrRequestPermission(Activity activity, PermissionCallback callback) {
        manager.checkOrRequestPermission(activity, callback);
    }

    public static void requestPermission(Activity activity) {
        manager.requestPermission(activity);
    }
}
