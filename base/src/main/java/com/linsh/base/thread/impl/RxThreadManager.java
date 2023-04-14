package com.linsh.base.thread.impl;


import android.os.Handler;
import android.os.HandlerThread;

import com.linsh.base.thread.ThreadManager;
import com.linsh.utilseverywhere.HandlerUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/04
 *    desc   :
 * </pre>
 */
public class RxThreadManager implements ThreadManager {

    static class Holder {
        /**
         * 使用 ThreadPoolExecutor 实现, 默认 8 个核心线程, 且核心线程可被销毁(保证线程回收)
         */
        static final ThreadPoolExecutor POOL_THREAD = new ThreadPoolExecutor(8, 8,
                1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());

        static {
            // 核心线程可超时销毁
            POOL_THREAD.allowCoreThreadTimeOut(true);
        }
    }

    static class PresenterThreadHolder {
        static final Handler MVP_PRESENTER_THREAD = getHandlerThread();

        private static Handler getHandlerThread() {
            HandlerThread handlerThread = new HandlerThread("MvpPresenterThread");
            handlerThread.start();
            return new Handler(handlerThread.getLooper());
        }
    }

    @Override
    public void ui(Runnable task) {
        HandlerUtils.postRunnable(task);
    }

    @Override
    public void single(Runnable task) {
        Schedulers.single().scheduleDirect(task);
    }

    @Override
    public void pool(Runnable task) {
        Holder.POOL_THREAD.execute(task);
    }

    @Override
    public void io(Runnable task) {
        Schedulers.io().scheduleDirect(task);
    }

    @Override
    public void newThread(Runnable task) {
        Schedulers.newThread().scheduleDirect(task);
    }

    @Override
    public void presenter(Runnable task) {
        PresenterThreadHolder.MVP_PRESENTER_THREAD.post(task);
    }
}
