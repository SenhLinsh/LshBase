package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.FormBuilder;

import java.util.Map;

import okhttp3.FormBody;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   :
 * </pre>
 */
class RetrofitFormBuilder extends RetrofitRequestBuilder<FormBuilder> implements FormBuilder {

    public RetrofitFormBuilder(RetrofitRequestBuilder builder) {
        super(builder);
    }

    @Override
    public FormBuilder addFormData(String name, String value) {
        params.put(name, value);
        return this;
    }

    @Override
    public FormBuilder addFormData(Map<String, String> params) {
        params.putAll(params);
        return this;
    }

    @Override
    protected void fixUrlAndBody() {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        this.body = builder.build();
        super.fixUrlAndBody();
    }
}
