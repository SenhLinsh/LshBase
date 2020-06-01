package com.linsh.base.download;

import com.linsh.base.common.Consumer;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   : 下载管理器
 *
 *             管理多个下载项
 * </pre>
 */
public interface DownloadManager {

    /**
     * 添加 Runnable 任务
     * <p>
     * 可通过 Runnable 自定义下载, 或模拟下载
     */
    DownloadTask addTask(Runnable runnable);

    /**
     * 添加 Call 任务
     * <p>
     * DownloadManager 将会执行下载, 并将 ResponseBody 写入文件
     */
    DownloadTask addTask(Call call, File file);

    /**
     * 添加 Call 任务
     * <p>
     * DownloadManager 将会执行下载, 并将 Response 回调交给 Consumer 进行处理
     */
    DownloadTask addTask(Call call, Consumer<Response> consumer);

    /**
     * 添加 Url 任务
     * <p>
     * DownloadManager 自动构建 Call, 并下载到文件
     */
    DownloadTask addTask(String url, File file);

    /**
     * 添加 Call 任务
     * <p>
     * DownloadManager 自动构建 Call, 并执行下载, 将 Response 回调交给 Consumer 进行处理
     */
    DownloadTask addTask(String url, Consumer<Response> consumer);

    /**
     * 获取所有任务的数量
     */
    int getTaskCount();

    /**
     * 获取活跃任务的数量
     * <p>
     * 活跃任务包括: 等待, 下载中
     */
    int getActiveTaskCount();

    /**
     * 获取失败任务的数量
     */
    int getFailedTaskCount();

    /**
     * 获取已完成任务的数量
     */
    int getFinishedTaskCount();

    /**
     * 清除所有任务
     */
    void cancelAndClear();

    /**
     * 清除已完成任务
     */
    void cancelActiveTasks();

    /**
     * 清除已完成任务
     */
    void clearFinishedTasks();

    /**
     * 清除不再活跃的任务, 包括: 已完成, 失败
     */
    void clearInactiveTasks();

    /**
     * 重试失败的下载任务
     */
    void retryFailedTasks();

    /**
     * DownloadManager 的构造器
     */
    interface Builder {

        /**
         * 设置下载回调
         */
        Builder downloadListener(DownloadListener listener);

        /**
         * 如果重复已完成的任务, 是否直接通过 (标记完成)
         * <p>
         * 判断重复的标准: 1. 下载文件存在; 2. 已完成任务中存在相同的下载地址
         * <p>
         * 默认为 false
         */
        Builder passIfAlreadyCompleted(boolean passIfAlreadyCompleted);

        /**
         * 是否指定 wifi 环境下下载
         * <p>
         * 默认为 false
         */
        Builder wifiRequired(boolean wifiRequired);

        /**
         * 对于 {@link DownloadListener} 的回调, 是否自动转换到 UI 线程
         * <p>
         * 默认为 true
         */
        Builder autoCallbackToUIThread(boolean autoCallbackToUIThread);

        /**
         * 重试次数, 默认为 0
         */
        Builder retryTime(int retryTime);

        /**
         * 创建一个包含相同参数的新构造器
         */
        Builder newBuild();

        /**
         * 构建返回 DownloadManager
         */
        DownloadManager build();
    }
}
