package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.AdapterCallback;
import com.linsh.base.net.http.RequestCall;
import com.linsh.utilseverywhere.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Timeout;
import retrofit2.Converter;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   :
 * </pre>
 */
class RetrofitCall implements RequestCall {

    private Call actual;
    private RetrofitManager manager;

    public RetrofitCall(Call call, RetrofitManager manager) {
        this.actual = call;
        this.manager = manager;
    }

    @Override
    public Request request() {
        return actual.request();
    }

    @Override
    public Response execute() throws IOException {
        return actual.execute();
    }

    @Override
    public void enqueue(Callback responseCallback) {
        actual.enqueue(responseCallback);
    }

    @Override
    public void cancel() {
        actual.cancel();
    }

    @Override
    public boolean isExecuted() {
        return actual.isExecuted();
    }

    @Override
    public boolean isCanceled() {
        return actual.isCanceled();
    }

    @Override
    public Timeout timeout() {
        return actual.timeout();
    }

    @Override
    public RetrofitCall clone() {
        return new RetrofitCall(actual.clone(), manager);
    }

    @Override
    public <T> T execute(Class<T> classOfT) throws IOException {
        Response response = actual.execute();
        Converter<ResponseBody, T> converter = manager.nextResponseBodyConverter(classOfT);
        T convert = converter.convert(response.body());
        response.close();
        return convert;
    }

    @Override
    public <T> void enqueue(final AdapterCallback<T> callback) {
        actual.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null)
                    callback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback == null) return;
                Type type = ClassUtils.getGenericType(callback.getClass(), AdapterCallback.class);
                if (type == null)
                    throw new IllegalArgumentException("获取转换类型失败");
                Converter<ResponseBody, T> converter = manager.nextResponseBodyConverter(type);
                T convert = converter.convert(response.body());
                response.close();
                callback.onResponse(call, convert);
            }
        });
    }
}