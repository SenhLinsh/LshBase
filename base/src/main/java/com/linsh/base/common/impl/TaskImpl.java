package com.linsh.base.common.impl;

import com.linsh.base.common.Callback;
import com.linsh.base.common.Task;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/12
 *    desc   :
 * </pre>
 */
public class TaskImpl<T> implements Task<T> {

    private final Callable<T> callable;
    private final Scheduler asyncScheduler;
    private final Scheduler callbackScheduler;

    public TaskImpl(@NonNull Callable<T> callable) {
        this(callable, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    public TaskImpl(@NonNull Callable<T> callable, Scheduler asyncScheduler) {
        this(callable, asyncScheduler, AndroidSchedulers.mainThread());
    }

    public TaskImpl(@NonNull Callable<T> callable, Scheduler asyncScheduler, Scheduler callbackScheduler) {
        this.callable = callable;
        this.asyncScheduler = asyncScheduler;
        this.callbackScheduler = callbackScheduler;
    }

    @Override
    public T sync() {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void async() {
        if (asyncScheduler == null) {
            throw new IllegalArgumentException("没有指定后台运行线程 asyncScheduler");
        }
        asyncScheduler.scheduleDirect(new Runnable() {
            @Override
            public void run() {
                sync();
            }
        });
    }

    @Override
    public void async(final Callback<T> callback) {
        if (asyncScheduler == null) {
            throw new IllegalArgumentException("没有指定后台运行线程 asyncScheduler");
        }
        asyncScheduler.scheduleDirect(new Runnable() {
            @Override
            public void run() {
                try {
                    final T call = callable.call();
                    if (callback != null) {
                        if (callbackScheduler != null) {
                            callbackScheduler.scheduleDirect(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(call);
                                }
                            });
                        } else {
                            throw new IllegalArgumentException("没有指定回调线程 callbackScheduler");
                        }
                    }
                } catch (final Exception e) {
                    if (callback != null) {
                        if (callbackScheduler != null) {
                            callbackScheduler.scheduleDirect(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailed(e);
                                }
                            });
                        } else {
                            throw new IllegalArgumentException("没有指定回调线程 callbackScheduler");
                        }
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public Flowable<T> flowable() {
        Flowable<T> flowable = Flowable.fromCallable(callable);
        if (asyncScheduler != null) {
            flowable = flowable.subscribeOn(asyncScheduler);
        }
        if (callbackScheduler != null) {
            flowable = flowable.observeOn(callbackScheduler);
        }
        return flowable;
    }
}
