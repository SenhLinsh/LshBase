package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.RequestBuilder;
import com.linsh.base.net.http.RequestCall;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   :
 * </pre>
 */
abstract class RetrofitRequestBuilder<T extends RequestBuilder> implements RequestBuilder<T> {

    protected String url;
    protected final String method;
    protected final Map<String, String> params;
    protected final Map<String, String> headers;
    protected RequestBody body;
    protected final RetrofitManager manager;

    public RetrofitRequestBuilder(RetrofitRequestBuilder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.params = builder.params;
        this.headers = builder.headers;
        this.body = builder.body;
        this.manager = builder.manager;
    }

    public RetrofitRequestBuilder(String method, String url, RetrofitManager manager) {
        this.method = method;
        this.url = url;
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
        this.manager = manager;
    }

    @Override
    public T addHeader(String name, String value) {
        headers.put(name, value);
        return (T) this;
    }

    @Override
    public T removeHeader(String name) {
        headers.remove(name);
        return (T) this;
    }

    @Override
    public final RequestCall buildCall() {
        fixUrlAndBody();
        Request.Builder builder = new Request.Builder();
        builder.method(method, body)
                .url(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.build();
        return new RetrofitCall(manager.callFactory.newCall(request), manager);
    }

    protected void fixUrlAndBody() {
    }

    @Override
    public final <R> Flowable<R> buildFlowable(final Class<R> classOfR) {
        if ("GET".equals(method)) {
            return manager.retrofit.create(FlowableApi.class)
                    .get(url, headers, params)
                    .map(new Function<ResponseBody, R>() {
                        @Override
                        public R apply(ResponseBody responseBody) throws Exception {
                            Converter<ResponseBody, R> converter = manager.nextResponseBodyConverter(classOfR);
                            return converter.convert(responseBody);
                        }
                    });
        }
        if ("POST".equals(method)) {
            return manager.retrofit.create(FlowableApi.class)
                    .post(url, headers, body)
                    .map(new Function<ResponseBody, R>() {
                        @Override
                        public R apply(ResponseBody responseBody) throws Exception {
                            Converter<ResponseBody, R> converter = manager.nextResponseBodyConverter(classOfR);
                            return converter.convert(responseBody);
                        }
                    });
        }
        throw new IllegalArgumentException("暂不支持该 Http 方法 -- " + method);
    }

    @Override
    public final <R> Observable<R> buildObservable(Class<R> clazz) {
        return buildFlowable(clazz).toObservable();
    }
}
