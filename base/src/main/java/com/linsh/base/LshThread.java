package com.linsh.base;


import com.linsh.base.thread.ThreadManager;
import com.linsh.base.thread.ThreadPolicy;
import com.linsh.base.thread.impl.RxThreadManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/03/29
 *    desc   :
 * </pre>
 */
public class LshThread {

    private static class Holder {
        private static final ThreadManager delegate = new RxThreadManager();
    }

    private LshThread() {
    }

    /**
     * 主线程
     */
    public static void ui(Runnable task) {
        Holder.delegate.ui(task);
    }

    /**
     * 单线程
     */
    public static void single(Runnable task) {
        Holder.delegate.single(task);
    }

    /**
     * 碎片线程, 用于碎片化操作(操作频繁, 耗时短, 不希望被阻塞), 如 Log 日志读写等
     * <p>
     * 注: 默认为单线程, 请勿执行长时间耗时操作而影响其他操作的效率
     */
    public static void piece(Runnable task) {
        Holder.delegate.piece(task);
    }

    /**
     * io 线程, 多线程, 用于 IO 操作
     */
    public static void io(Runnable task) {
        Holder.delegate.io(task);
    }

    /**
     * 新线程
     */
    public static void newThread(Runnable task) {
        Holder.delegate.newThread(task);
    }

    /**
     * 用于 MVP 模式下 Presenter 的默认线程
     */
    public static void presenter(Runnable task) {
        Holder.delegate.presenter(task);
    }

    /**
     * 传入 ThreadPolicy 来选择线程
     */
    public static void run(ThreadPolicy policy, Runnable task) {
        if (policy == null) {
            task.run();
            return;
        }
        switch (policy) {
            case UI:
                ui(task);
                break;
            case SINGLE:
                single(task);
                break;
            case PIECE:
                piece(task);
                break;
            case IO:
                io(task);
                break;
            case NEW:
                newThread(task);
                break;
            case PRESENTER:
                presenter(task);
                break;
        }
    }
}
