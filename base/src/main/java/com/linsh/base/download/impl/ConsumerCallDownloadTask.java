package com.linsh.base.download.impl;

import com.linsh.base.common.Consumer;

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
class ConsumerCallDownloadTask extends DownloadTaskImpl {

    private final Call call;
    private final Consumer<Response> consumer;

    ConsumerCallDownloadTask(Call call, Consumer<Response> consumer) {
        this.call = call;
        this.consumer = consumer;
    }

    @Override
    void execute() throws Exception {
        consumer.accept(call.execute());
    }
}
