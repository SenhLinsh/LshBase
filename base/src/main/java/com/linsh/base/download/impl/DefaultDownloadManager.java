package com.linsh.base.download.impl;

import com.linsh.base.LshHttp;
import com.linsh.base.LshThread;
import com.linsh.base.common.Constants;
import com.linsh.base.common.Consumer;
import com.linsh.base.config.HttpConfig;
import com.linsh.base.download.DownloadListener;
import com.linsh.base.download.DownloadManager;
import com.linsh.base.download.DownloadTask;
import com.linsh.base.download.EndCause;
import com.linsh.base.net.http.HttpManager;
import com.linsh.utilseverywhere.NetworkUtils;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
public class DefaultDownloadManager implements DownloadManager {

    private final DownloadListener listener;
    private final boolean passIfAlreadyCompleted;
    private final boolean wifiRequired;
    private final boolean autoCallbackToUIThread;
    private final int retryTime;

    private final HttpManager httpManager;

    private Queue<DownloadTaskImpl> pendingTasks = new LinkedList<>();
    private Set<DownloadTaskImpl> downloadingTasks = new HashSet<>();
    private Queue<DownloadTaskImpl> finishedTasks = new LinkedList<>();
    private Queue<DownloadTaskImpl> errorTasks = new LinkedList<>();

    private DefaultDownloadManager(DownloadListener listener, boolean passIfAlreadyCompleted, boolean wifiRequired, boolean autoCallbackToUIThread, int retryTime) {
        this.listener = listener;
        this.passIfAlreadyCompleted = passIfAlreadyCompleted;
        this.wifiRequired = wifiRequired;
        this.autoCallbackToUIThread = autoCallbackToUIThread;
        this.retryTime = retryTime;
        this.httpManager = LshHttp.getInstance(
                new HttpConfig.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .connectTimeout(30 * 1000)
                        .readTimeout(30 * 1000)
                        .build());
    }

    @Override
    public DownloadTask addTask(Runnable runnable) {
        RunnableDownloadTask task = new RunnableDownloadTask(runnable);
        return addTask(task);
    }

    @Override
    public DownloadTask addTask(Call call, File file) {
        FileCallDownloadTask task = new FileCallDownloadTask(call, file, passIfAlreadyCompleted);
        return addTask(task);
    }

    @Override
    public DownloadTask addTask(Call call, Consumer<Response> consumer) {
        ConsumerCallDownloadTask task = new ConsumerCallDownloadTask(call, consumer);
        return addTask(task);
    }

    @Override
    public DownloadTask addTask(String url, File file) {
        FileCallDownloadTask task = new FileCallDownloadTask(httpManager.get(url).buildCall(), file, passIfAlreadyCompleted);
        return addTask(task);
    }

    @Override
    public DownloadTask addTask(String url, Consumer<Response> consumer) {
        ConsumerCallDownloadTask task = new ConsumerCallDownloadTask(httpManager.get(url).buildCall(), consumer);
        return addTask(task);
    }

    private DownloadTask addTask(DownloadTaskImpl task) {
        task.setStatus(DownloadTask.STATUS_PENDING);
        synchronized (DefaultDownloadManager.this) {
            pendingTasks.add(task);
        }
        downloadNext();
        return task;
    }

    private void downloadNext() {
        if (wifiRequired && !NetworkUtils.isWifi()) {
            return;
        }
        final DownloadTaskImpl task;
        synchronized (DefaultDownloadManager.this) {
            if (pendingTasks.size() == 0 || downloadingTasks.size() >= 5) {
                return;
            }
            task = pendingTasks.remove();
            task.setStatus(DownloadTask.STATUS_DOWNLOADING);
            downloadingTasks.add(task);
        }
        LshThread.io(new Runnable() {
            @Override
            public void run() {
                try {
                    task.execute();
                    if (!task.isCancel()) {
                        task.setStatus(DownloadTask.STATUS_FINISHED);
                        synchronized (DefaultDownloadManager.this) {
                            downloadingTasks.remove(task);
                            finishedTasks.add(task);
                        }
                    } else {
                        synchronized (DefaultDownloadManager.this) {
                            downloadingTasks.remove(task);
                        }
                    }

                    if (listener != null) {
                        if (autoCallbackToUIThread) {
                            LshThread.ui(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onTaskEnd(task, task.isCancel() ? EndCause.CANCELED : EndCause.FINISHED, null);
                                }
                            });
                        } else {
                            listener.onTaskEnd(task, task.isCancel() ? EndCause.CANCELED : EndCause.FINISHED, null);
                        }
                    }
                } catch (final Exception e) {
                    if (task.getRetryTime() > retryTime) {
                        task.setStatus(DownloadTask.STATUS_ERROR);
                        synchronized (DefaultDownloadManager.this) {
                            downloadingTasks.remove(task);
                            finishedTasks.add(task);
                        }
                        if (listener != null) {
                            if (autoCallbackToUIThread) {
                                LshThread.ui(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onTaskEnd(task, EndCause.ERROR, e);
                                    }
                                });
                            } else {
                                listener.onTaskEnd(task, EndCause.ERROR, e);
                            }
                        }
                    } else {
                        task.setStatus(DownloadTask.STATUS_PENDING);
                        synchronized (DefaultDownloadManager.this) {
                            downloadingTasks.remove(task);
                            pendingTasks.add(task);
                        }
                    }
                }
                downloadNext();
            }
        });
    }

    @Override
    public int getTaskCount() {
        return pendingTasks.size() + downloadingTasks.size() + finishedTasks.size() + errorTasks.size();
    }

    @Override
    public int getActiveTaskCount() {
        return pendingTasks.size() + downloadingTasks.size();
    }

    @Override
    public int getFailedTaskCount() {
        return errorTasks.size();
    }

    @Override
    public int getFinishedTaskCount() {
        return finishedTasks.size();
    }

    @Override
    public void cancelAndClear() {
        cancelActiveTasks();
        clearInactiveTasks();
    }

    @Override
    public void cancelActiveTasks() {
        while (pendingTasks.size() > 0) {
            pendingTasks.remove()
                    .cancel();
        }
        for (DownloadTaskImpl task : downloadingTasks) {
            task.cancel();
        }
    }

    @Override
    public void clearFinishedTasks() {
        finishedTasks.clear();
    }

    @Override
    public void clearInactiveTasks() {
        finishedTasks.clear();
        errorTasks.size();
    }

    @Override
    public void retryFailedTasks() {
        while (errorTasks.size() > 0) {
            addTask(errorTasks.remove());
        }
    }

    public static class Builder implements DownloadManager.Builder {

        private DownloadListener listener;
        private boolean passIfAlreadyCompleted = false;
        private boolean wifiRequired = false;
        private boolean autoCallbackToUIThread = true;
        private int retryTime;

        @Override
        public DownloadManager.Builder downloadListener(DownloadListener listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public DownloadManager.Builder passIfAlreadyCompleted(boolean passIfAlreadyCompleted) {
            this.passIfAlreadyCompleted = passIfAlreadyCompleted;
            return this;
        }

        @Override
        public DownloadManager.Builder wifiRequired(boolean wifiRequired) {
            this.wifiRequired = wifiRequired;
            return this;
        }

        @Override
        public DownloadManager.Builder autoCallbackToUIThread(boolean autoCallbackToUIThread) {
            this.autoCallbackToUIThread = autoCallbackToUIThread;
            return this;
        }

        @Override
        public DownloadManager.Builder retryTime(int retryTime) {
            this.retryTime = retryTime;
            return this;
        }

        @Override
        public DownloadManager.Builder newBuild() {
            Builder builder = new Builder();
            builder.listener = this.listener;
            builder.passIfAlreadyCompleted = this.passIfAlreadyCompleted;
            builder.wifiRequired = this.wifiRequired;
            builder.autoCallbackToUIThread = this.autoCallbackToUIThread;
            builder.retryTime = this.retryTime;
            return builder;
        }

        @Override
        public DownloadManager build() {
            return new DefaultDownloadManager(listener, passIfAlreadyCompleted, wifiRequired, autoCallbackToUIThread, retryTime);
        }
    }
}
