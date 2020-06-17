package com.linsh.base.net.http.retrofit.adapter;

import com.linsh.base.net.http.retrofit.HttpException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/12/16
 *    desc   : 直接在当前线程执行, 并返回结果的 CallAdapter
 * </pre>
 */
public final class DefaultCallAdapterFactory extends CallAdapter.Factory {

    private DefaultCallAdapterFactory() {
    }

    public static DefaultCallAdapterFactory create() {
        return new DefaultCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new DefaultCallAdapter(retrofit, returnType);
    }

    private static class DefaultCallAdapter<R> implements CallAdapter<R, Object> {

        private final Retrofit retrofit;
        private final Type returnType;

        public DefaultCallAdapter(Retrofit retrofit, Type returnType) {
            this.retrofit = retrofit;
            this.returnType = returnType;
        }

        @Override
        public Type responseType() {
            return returnType;
        }

        @Override
        public Object adapt(Call<R> call) {
            Response<R> response;
            try {
                response = call.execute();
            } catch (IOException e) {
                throw new HttpException(e);
            }
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new HttpException(response);
            }
        }
    }
}