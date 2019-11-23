package com.linsh.base.net.http.retrofit;


import com.linsh.base.net.http.BodyBuilder;
import com.linsh.base.net.http.FormBuilder;
import com.linsh.base.net.http.MultipartBuilder;
import com.linsh.base.net.http.PostBuilder;

import java.io.File;
import java.util.Map;

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
class RetrofitPostBuilder extends RetrofitRequestBuilder<PostBuilder> implements PostBuilder {

    public RetrofitPostBuilder(String url, RetrofitManager manager) {
        super("POST", url, manager);
    }

    @Override
    public BodyBuilder body(RequestBody body) {
        this.body = body;
        return new RetrofitBodyBuilder(this);
    }

    @Override
    public BodyBuilder body(String body) {
        this.body = RequestBody.create(null, body);
        return new RetrofitBodyBuilder(this);
    }

    @Override
    public BodyBuilder file(File file) {
        this.body = RequestBody.create(null, file);
        return new RetrofitBodyBuilder(this);
    }

    @Override
    public BodyBuilder file(MediaType mediaType, File file) {
        this.body = RequestBody.create(mediaType, file);
        return new RetrofitBodyBuilder(this);
    }

    @Override
    public FormBuilder addFormData(String name, String value) {
        return new RetrofitFormBuilder(this).addFormData(name, value);
    }

    @Override
    public FormBuilder addFormData(Map<String, String> params) {
        return new RetrofitFormBuilder(this).addFormData(params);
    }

    @Override
    public MultipartBuilder addPart(File file) {
        return new RetrofitMultipartBuilder(this).addPart(file);
    }

    @Override
    public MultipartBuilder addPart(MediaType mediaType, File file) {
        return new RetrofitMultipartBuilder(this).addPart(mediaType, file);
    }

    @Override
    public MultipartBuilder addPart(RequestBody body) {
        return new RetrofitMultipartBuilder(this).addPart(body);
    }

    @Override
    public MultipartBuilder addPart(MultipartBody.Part part) {
        return new RetrofitMultipartBuilder(this).addPart(part);
    }

    @Override
    public MultipartBuilder addFormDataPart(String name, String value) {
        return new RetrofitMultipartBuilder(this).addFormDataPart(name, value);
    }

    @Override
    public MultipartBuilder addFormDataPart(String name, String filename, File file) {
        return new RetrofitMultipartBuilder(this).addFormDataPart(name, filename, file);
    }

    @Override
    public MultipartBuilder addFormDataPart(String name, String filename, RequestBody body) {
        return new RetrofitMultipartBuilder(this).addFormDataPart(name, filename, body);
    }

    @Override
    protected void fixUrlAndBody() {
    }
}
