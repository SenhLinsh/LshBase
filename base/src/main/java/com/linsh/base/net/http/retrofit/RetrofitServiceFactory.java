package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.ServiceFactory;

import retrofit2.Retrofit;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   :
 * </pre>
 */
class RetrofitServiceFactory implements ServiceFactory {

    private Retrofit retrofit;

    public RetrofitServiceFactory(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
