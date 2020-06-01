package com.linsh.base.download;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
public interface DownloadTask {

    int STATUS_PENDING = 0;
    int STATUS_DOWNLOADING = 1;
    int STATUS_FINISHED = 2;
    int STATUS_ERROR = 3;
    int STATUS_CANCELED = 4;

    void setProgressListener(ProgressListener listener);

    void cancel();

    int getStatus();

    boolean isActive();

    interface ProgressListener {
        /**
         * 进度回调
         *
         * @param progress 进度, [0 ~ 1]
         */
        void onProgress(float progress);
    }
}
