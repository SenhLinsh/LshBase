package com.linsh.base.net.http;

import java.io.IOException;

import okhttp3.Call;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/11
 *    desc   : 适配器回调
 *
 *              该回调可以指定回调类型, 适配器会根据指定的类型进行自动转换
 * </pre>
 */
public interface AdapterCallback<T> {

    void onFailure(Call call, IOException e);

    void onResponse(Call call, T response) throws IOException;
}