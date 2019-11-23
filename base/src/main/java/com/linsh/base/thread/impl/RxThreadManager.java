package com.linsh.base.thread.impl;


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
        static final ThreadPoolExecutor PIECE_THREAD = new ThreadPoolExecutor(0, 1,
                10, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>());
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
    public void piece(Runnable task) {
        Holder.PIECE_THREAD.execute(task);
    }

    @Override
    public void io(Runnable task) {
        Schedulers.io().scheduleDirect(task);
    }

    @Override
    public void newThread(Runnable task) {
        Schedulers.newThread().scheduleDirect(task);
    }
}
