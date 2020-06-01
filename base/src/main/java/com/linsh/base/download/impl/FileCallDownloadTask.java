package com.linsh.base.download.impl;

import com.linsh.base.LshLog;
import com.linsh.utilseverywhere.FileUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
class FileCallDownloadTask extends DownloadTaskImpl {

    private static final String TAG = "CallDownloadTask";
    private final Call call;
    private final File file;
    private final boolean passIfAlreadyCompleted;

    FileCallDownloadTask(Call call, File file, boolean passIfAlreadyCompleted) {
        this.call = call;
        this.file = file;
        this.passIfAlreadyCompleted = passIfAlreadyCompleted;
    }

    @Override
    void execute() throws Exception {
        LshLog.d(TAG, "execute, file: " + file.getPath());
        if (file.exists() && passIfAlreadyCompleted) {
            LshLog.d(TAG, "file exists");
            return;
        }
        Response response = call.execute();
        ResponseBody body = response.body();
        if (body == null) {
            throw new IOException("no repose body");
        }
        boolean success = FileUtils.writeStream(file, body.byteStream());
        if (!success) {
            throw new IOException("write response by to file failed");
        }
    }
}
