package com.linsh.base.download;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
public interface DownloadListener {

    /**
     * 开始
     */
    void onStart();

    /**
     * Task 开始下载
     *
     * @param task 当前下载任务
     */
    void onTaskStart(DownloadTask task);

    /**
     * Task 下载结束
     *
     * @param task      当前下载任务
     * @param cause     结束原因
     * @param exception 如果是下载失败, 将会附带 Exception
     */
    void onTaskEnd(DownloadTask task, EndCause cause, Exception exception);

    /**
     * 全部 Task 执行结束
     */
    void onEnd();
}
