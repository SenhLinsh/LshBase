package com.linsh.base.net.http.retrofit;


import android.os.Build;

import androidx.annotation.NonNull;

import com.linsh.base.config.HttpConfig;
import com.linsh.base.net.http.GetBuilder;
import com.linsh.base.net.http.HttpManager;
import com.linsh.base.net.http.PostBuilder;
import com.linsh.base.net.http.RequestBuilderFactory;
import com.linsh.base.net.http.ServiceFactory;
import com.linsh.base.net.http.retrofit.adapter.DefaultCallAdapterFactory;
import com.linsh.base.net.http.retrofit.converter.StringConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : 使用 Retrofit 进行网络请求的管理器
 * </pre>
 */
public class RetrofitManager implements HttpManager {

    final Retrofit retrofit;
    final Call.Factory callFactory;
    private final ServiceFactory serviceFactory;
    private final RequestBuilderFactory requestBuilderFactory;
    private final List<Converter.Factory> converterFactories;

    public RetrofitManager(@NonNull HttpConfig config) {
        this(build(config));
    }

    public RetrofitManager(@NonNull Retrofit retrofit) {
        this.retrofit = retrofit;
        this.callFactory = retrofit.callFactory();
        this.serviceFactory = new RetrofitServiceFactory(retrofit);
        this.requestBuilderFactory = new RetrofitRequestBuilderFactory(this);
        this.converterFactories = retrofit.converterFactories();
    }

    private static Retrofit build(final HttpConfig config) {
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder.baseUrl(config.baseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(DefaultCallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(new OkHttpClient.Builder()
                        // 添加 Http Header
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder newBuilder = chain.request().newBuilder();
                                return chain.proceed(addHeader(newBuilder, config.getHeaders()).build());
                            }
                        })
                    .build())
                .build();
    }


    private static Request.Builder addHeader(Request.Builder builder, HashMap<String, String> headers) {
        builder.addHeader("deviceId", Build.SERIAL);
        if (headers == null) {
            return builder;
        }
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey() == null || headers.get(entry.getKey()) == null) {
                continue;
            }
            builder.addHeader(entry.getKey(), headers.get(entry.getKey()));
        }

        return builder;
    }

    @Override
    public <T> T service(Class<T> clazz) {
        return serviceFactory.create(clazz);
    }

    @Override
    public GetBuilder get(String url) {
        return requestBuilderFactory.get(url);
    }

    @Override
    public PostBuilder post(String url) {
        return requestBuilderFactory.post(url);
    }

    <T> Converter<ResponseBody, T> nextResponseBodyConverter(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (converterFactories == null)
            throw new NullPointerException("converterFactories == null");
        if (converterFactories.size() == 0)
            throw new NullPointerException("converterFactories can not be empty");

        for (Converter.Factory converterFactory : converterFactories) {
            Converter<ResponseBody, ?> converter =
                    converterFactory.responseBodyConverter(type, null, null);
            if (converter != null)
                return (Converter<ResponseBody, T>) converter;
        }
        throw new IllegalArgumentException("Could not locate ResponseBody converter for " + type);
    }
}
