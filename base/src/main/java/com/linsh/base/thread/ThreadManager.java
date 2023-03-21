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
     * 线程池, 用于短时间内大量工作, 但又不至于创建过多线程的场景
     * <p>
     * 注: {@link ThreadManager#io(Runnable)} 的方法创建的线程数默认不作限制, 短时间内大量调用可能
     * 会创建较多线程并行处理
     */
    void pool(Runnable task);

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
