package com.linsh.base.thread;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/04
 *    desc   :
 * </pre>
 */
public interface ThreadManager {

    /**
     * 主线程
     */
    void ui(Runnable task);

    /**
     * 单线程
     */
    void single(Runnable task);

    /**
     * 碎片线程, 用于碎片化操作(操作频繁, 耗时短, 不希望被阻塞), 如 Log 日志读写等
     * <p>
     * 注: 默认为单线程, 请勿执行长时间耗时操作而影响其他操作的效率
     */
    void piece(Runnable task);

    /**
     * io 线程, 多线程, 用于 IO 操作
     */
    void io(Runnable task);

    /**
     * 新线程
     */
    void newThread(Runnable task);

    /**
     * 用于 MVP 模式下 Presenter 的默认线程
     */
    void presenter(Runnable task);
}
