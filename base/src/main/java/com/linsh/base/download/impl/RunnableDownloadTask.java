package com.linsh.base.download.impl;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/01
 *    desc   :
 * </pre>
 */
class RunnableDownloadTask extends DownloadTaskImpl {

    private final Runnable task;

    RunnableDownloadTask(Runnable task) {
        this.task = task;
    }

    @Override
    void execute() throws Exception {
        task.run();
    }
}
