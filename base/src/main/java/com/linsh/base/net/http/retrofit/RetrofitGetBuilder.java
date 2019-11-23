package com.linsh.base.net.http.retrofit;

import android.net.Uri;

import com.linsh.base.net.http.GetBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   :
 * </pre>
 */
class RetrofitGetBuilder extends RetrofitRequestBuilder<GetBuilder> implements GetBuilder {


    public RetrofitGetBuilder(String url, RetrofitManager manager) {
        super("GET", url, manager);
    }

    @Override
    public GetBuilder addParam(String name, String value) {
        this.params.put(name, value);
        return this;
    }

    @Override
    public GetBuilder addParams(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public GetBuilder removeParam(String name) {
        this.params.remove(name);
        return this;
    }

    @Override
    protected void fixUrlAndBody() {
        this.url = appendParams(url, params);
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
}
