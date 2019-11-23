package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.MultipartBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/10
 *    desc   :
 * </pre>
 */
class RetrofitMultipartBuilder extends RetrofitRequestBuilder<MultipartBuilder>
        implements MultipartBuilder {

    private MultipartBody.Builder bodyBuilder;

    public RetrofitMultipartBuilder(RetrofitRequestBuilder builder) {
        super(builder);
        bodyBuilder = new MultipartBody.Builder();
    }

    @Override
    public MultipartBuilder addPart(File file) {
        bodyBuilder.addPart(RequestBody.create(null, file));
        return this;
    }

    @Override
    public MultipartBuilder addPart(MediaType mediaType, File file) {
        bodyBuilder.addPart(RequestBody.create(mediaType, file));
        return this;
    }

    @Override
    public MultipartBuilder addPart(RequestBody body) {
        bodyBuilder.addPart(body);
        return this;
    }

    @Override
    public MultipartBuilder addPart(MultipartBody.Part part) {
        bodyBuilder.addPart(part);
        return this;
    }

    @Override
    public MultipartBuilder addFormDataPart(String name, String value) {
        bodyBuilder.addFormDataPart(name, value);
        return this;
    }

    @Override
    public MultipartBuilder addFormDataPart(String name, String filename, File file) {
        bodyBuilder.addFormDataPart(name, filename, RequestBody.create(null, file));
        return this;
    }

    @Override
    public MultipartBuilder addFormDataPart(String name, String filename, RequestBody body) {
        bodyBuilder.addFormDataPart(name, filename, body);
        return this;
    }

    @Override
    protected void fixUrlAndBody() {
        this.body = bodyBuilder.build();
        super.fixUrlAndBody();
    }
}
