package com.linsh.base.file.impl;

import android.graphics.Bitmap;

import com.linsh.base.LshThread;
import com.linsh.base.common.Callback;
import com.linsh.base.common.Consumer;
import com.linsh.base.file.FileWriter;
import com.linsh.utilseverywhere.BitmapUtils;
import com.linsh.utilseverywhere.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/16
 *    desc   :
 * </pre>
 */
class FileTaskImpl implements FileWriter.FileTask {

    private final File file;
    private List<Consumer<BufferedWriter>> consumers;
    private Callable<Boolean> callable;

    FileTaskImpl(File file) {
        this.file = file;
        this.consumers = new LinkedList<>();
    }

    @Override
    public FileWriter.FileTask write(final String content) {
        return write(content, false);
    }

    @Override
    public FileWriter.FileTask write(final String content, final boolean append) {
        if (!append) consumers.clear();
        consumers.add(new Consumer<BufferedWriter>() {
            @Override
            public void accept(BufferedWriter bufferedWriter) throws Exception {
                bufferedWriter.append(content);
            }
        });
        return this;
    }

    @Override
    public FileWriter.FileTask write(List<String> contents) {
        return write(contents, false);
    }

    @Override
    public FileWriter.FileTask write(final List<String> contents, boolean append) {
        if (!append) consumers.clear();
        consumers.add(new Consumer<BufferedWriter>() {
            @Override
            public void accept(BufferedWriter bufferedWriter) throws Exception {
                for (String content : contents) {
                    bufferedWriter.append(content);
                }
            }
        });
        return this;
    }

    @Override
    public FileWriter.FileTask writeLine() {
        return writeLines(1);
    }

    @Override
    public FileWriter.FileTask writeLines(final int lines) {
        consumers.add(new Consumer<BufferedWriter>() {
            @Override
            public void accept(BufferedWriter bufferedWriter) throws Exception {
                for (int i = 0; i < lines; i++) {
                    bufferedWriter.newLine();
                }
            }
        });
        return this;
    }

    @Override
    public FileWriter.FileTask write(final Bitmap bitmap) {
        callable = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return BitmapUtils.saveBitmap(bitmap, file);
            }
        };
        return this;
    }

    @Override
    public FileWriter.FileTask write(final byte[] bytes) {
        consumers.clear();
        callable = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return FileUtils.writeBytes(file, bytes);
            }
        };
        return this;
    }

    @Override
    public FileWriter.FileTask write(final InputStream stream) {
        consumers.clear();
        callable = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return FileUtils.writeStream(file, stream);
            }
        };
        return this;
    }

    @Override
    public Boolean sync() {
        if (consumers.size() > 0) {
            try {
                BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file, true));
                for (Consumer<BufferedWriter> consumer : consumers) {
                    consumer.accept(writer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        if (callable != null) {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void async() {
        LshThread.io(new Runnable() {
            @Override
            public void run() {
                sync();
            }
        });
    }

    @Override
    public void async(final Callback<Boolean> callback) {
        LshThread.io(new Runnable() {
            @Override
            public void run() {
                if (consumers.size() > 0) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file, true));
                        for (Consumer<BufferedWriter> consumer : consumers) {
                            consumer.accept(writer);
                        }
                    } catch (final Exception e) {
                        LshThread.ui(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailed(e);
                            }
                        });
                        return;
                    }
                    LshThread.ui(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(true);
                        }
                    });
                    return;
                }
                if (callable != null) {
                    try {
                        callable.call();
                    } catch (final Exception e) {
                        LshThread.ui(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailed(e);
                            }
                        });
                        return;
                    }
                }
                LshThread.ui(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(true);
                    }
                });
            }
        });
    }

    @Override
    public Flowable<Boolean> flowable() {
        if (consumers.size() > 0) {
            return Flowable.fromCallable(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file, true));
                    for (Consumer<BufferedWriter> consumer : consumers) {
                        consumer.accept(writer);
                    }
                    return true;
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        if (callable != null) {
            return Flowable.fromCallable(callable);
        }
        return Flowable.just(true);
    }
}
