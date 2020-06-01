package com.linsh.base.download.impl;

import com.linsh.base.download.DownloadTask;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
abstract class DownloadTaskImpl implements DownloadTask {

    private int retryTime;
    private int status;
    private ProgressListener listener;

    @Override
    public void setProgressListener(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void cancel() {
        status = STATUS_CANCELED;
    }

    boolean isCancel() {
        return status == STATUS_CANCELED;
    }

    ProgressListener getListener() {
        return listener;
    }

    void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public boolean isActive() {
        return status == STATUS_PENDING || status == STATUS_DOWNLOADING;
    }

    abstract void execute() throws Exception;

    int getRetryTime() {
        return retryTime;
    }

    void increaseRetryTime() {
        retryTime++;
    }
}
