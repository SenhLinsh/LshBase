package com.linsh.base;

import com.linsh.base.download.DownloadManager;
import com.linsh.base.download.impl.DefaultDownloadManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
public class LshDownload {

    /**
     * 为 DownloadManager 构建一个 Builder
     */
    public static DownloadManager.Builder builder() {
        return new DefaultDownloadManager.Builder();
    }
}
