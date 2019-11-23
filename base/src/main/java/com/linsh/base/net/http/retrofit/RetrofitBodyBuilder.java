package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.BodyBuilder;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   :
 * </pre>
 */
class RetrofitBodyBuilder extends RetrofitRequestBuilder<BodyBuilder> implements BodyBuilder {

    public RetrofitBodyBuilder(RetrofitRequestBuilder builder) {
        super(builder);
    }
}
