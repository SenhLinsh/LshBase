package com.linsh.base.file.impl;

import android.content.Context;
import android.os.Build;

import com.linsh.base.LshLog;
import com.linsh.base.common.Result;
import com.linsh.base.common.Task;
import com.linsh.base.common.impl.ResultImpl;
import com.linsh.base.common.impl.TaskImpl;
import com.linsh.base.file.FileBuilder;
import com.linsh.base.file.FileReader;
import com.linsh.base.file.FileWriter;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.FileUtils;
import com.linsh.utilseverywhere.UEPermission;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/16
 *    desc   :
 * </pre>
 */
class FileBuilderImpl implements FileBuilder {

    static final int TYPE_PATH = 0;
    static final int TYPE_DATA = 3;
    static final int TYPE_CACHE = 4;

    private String filename;
    private final int dirType;
    private boolean external;
    private boolean force;
    private File tempFile;

    public FileBuilderImpl(String path) {
        this.filename = path;
        this.dirType = 0;
    }

    FileBuilderImpl(int dirType, String filename) {
        this.filename = filename;
        this.dirType = dirType;
    }

    @Override
    public FileBuilder parent(String name) {
        if (dirType == TYPE_PATH) {
            int index = filename.lastIndexOf("/");
            if (index > 0) {
                filename = filename.substring(0, index) + "/" + name + filename.substring(index);
            } else {
                LshLog.e("无法给路径(" + filename + ")添加文件夹 " + name);
            }
        } else {
            int index = filename.lastIndexOf("/");
            if (index > 0) {
                filename = filename.substring(0, index) + "/" + name + filename.substring(index);
            } else {
                filename = name + "/" + filename;
            }
        }
        return this;
    }

    @Override
    public FileBuilder child(String name) {
        tempFile = null;
        if (filename.endsWith("/")) {
            filename += name;
        } else {
            filename += "/" + name;
        }
        return this;
    }

    @Override
    public FileBuilder external(boolean external) {
        if (this.external != external) {
            this.external = external;
            tempFile = null;
        }
        return this;
    }

    @Override
    public FileBuilder external(boolean external, boolean force) {
        if (this.external != external || this.force != force) {
            this.external = external;
            this.force = force;
            tempFile = null;
        }
        return this;
    }

    @Override
    public FileBuilder makeDirs() {
        FileUtils.makeDirs(file());
        return this;
    }

    @Override
    public FileBuilder makeParentDirs() {
        FileUtils.makeParentDirs(file());
        return this;
    }

    @Override
    public File file() {
        if (tempFile != null)
            return tempFile;
        switch (dirType) {
            case TYPE_PATH:
                tempFile = new File(filename);
                break;
            case TYPE_DATA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tempFile = new File(ContextUtils.get().getDataDir(), filename);
                } else {
                    tempFile = new File(ContextUtils.get().getDir("data", Context.MODE_PRIVATE), filename);
                }
                break;
            case TYPE_CACHE:
                if ((force && external) || (!force && UEPermission.Storage.check())) {
                    tempFile = new File(ContextUtils.getExternalCacheDir(), filename);
                } else {
                    tempFile = new File(ContextUtils.getCacheDir(), filename);
                }
                break;
        }
        return tempFile;
    }

    @Override
    public File[] listFiles() {
        return file().listFiles();
    }

    @Override
    public FileReader read() {
        return new FileReaderImpl(file());
    }

    @Override
    public FileWriter write() {
        return new FileWriterImpl(file());
    }

    @Override
    public boolean delete() {
        return FileUtils.deleteFile(file());
    }

    @Override
    public Task<Result> deleteAll() {
        return new TaskImpl<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                boolean b = FileUtils.deleteFile(file());
                if (b) return new ResultImpl();
                return new ResultImpl(1, "删除失败");
            }
        });
    }
}
