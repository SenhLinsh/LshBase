package com.linsh.base.file.impl;

import android.app.Activity;
import android.os.Build;

import com.linsh.base.LshActivity;
import com.linsh.base.activity.ActivitySubscribe;
import com.linsh.base.file.FileBuilder;
import com.linsh.base.config.FileConfig;
import com.linsh.base.file.FileManager;
import com.linsh.base.file.PermissionCallback;
import com.linsh.lshutils.utils.IdUtilsEx;
import com.linsh.utilseverywhere.SDCardUtils;
import com.linsh.utilseverywhere.UEPermission;

import java.io.File;

import androidx.annotation.NonNull;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/16
 *    desc   :
 * </pre>
 */
public class LshFileManager implements FileManager {

    private final FileConfig config;

    public LshFileManager(FileConfig config) {
        this.config = config;
    }

    @Override
    public FileBuilder file(File file) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH, file.getAbsolutePath());
    }

    @Override
    public FileBuilder path(String path) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH, path);
    }

    @Override
    public FileBuilder sd(String filename) {
        File root = SDCardUtils.getRootDirectory();
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH,
                (root != null ? root.getAbsolutePath() : "sdcard/") + filename);
    }

    @Override
    public FileBuilder app(String filename) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_PATH,
                config.appDir() + filename);
    }

    @Override
    public FileBuilder data(String filename) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_DATA, filename);
    }

    @Override
    public FileBuilder cache(String filename) {
        return new FileBuilderImpl(FileBuilderImpl.TYPE_CACHE, filename);
    }

    @Override
    public boolean checkPermission() {
        return UEPermission.Storage.check();
    }

    @Override
    public void checkOrRequestPermission(Activity activity) {
        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                UEPermission.Storage.request(activity, IdUtilsEx.generateId());
            }
        }
    }

    @Override
    public void checkOrRequestPermission(final Activity activity, final PermissionCallback callback) {
        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final int id = IdUtilsEx.generateId();
                LshActivity.delegate(activity).subscribe(new ActivitySubscribe.OnRequestPermissionsResult() {
                    @Override
                    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                        if (id == requestCode) {
                            UEPermission.Storage.checkResult(permissions, grantResults);
                            LshActivity.delegate(activity).unsubscribe(this);
                        }
                    }

                    @Override
                    public void attach(Activity activity) {

                    }
                });
                UEPermission.Storage.request(activity, id);
            }
        }
    }

    @Override
    public void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            UEPermission.Storage.request(activity, IdUtilsEx.generateId());
        }
    }
}
