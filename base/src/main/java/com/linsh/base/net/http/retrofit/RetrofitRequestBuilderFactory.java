package com.linsh.base.net.http.retrofit;

import com.linsh.base.net.http.GetBuilder;
import com.linsh.base.net.http.PostBuilder;
import com.linsh.base.net.http.RequestBuilderFactory;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/11
 *    desc   :
 * </pre>
 */
class RetrofitRequestBuilderFactory implements RequestBuilderFactory {

    private RetrofitManager manager;

    public RetrofitRequestBuilderFactory(RetrofitManager manager) {
        this.manager = manager;
    }

    @Override
    public GetBuilder get(String url) {
        return new RetrofitGetBuilder(url, manager);
    }

    @Override
    public PostBuilder post(String url) {
        return new RetrofitPostBuilder(url, manager);
    }
}
