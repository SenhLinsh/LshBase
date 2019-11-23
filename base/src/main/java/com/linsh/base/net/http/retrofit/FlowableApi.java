package com.linsh.base.net.http.retrofit;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/11
 *    desc   : 提供用于创建 Flowable 事件流的简单 API 接口
 * </pre>
 */
interface FlowableApi {

    @GET
    Flowable<ResponseBody> get(@Url String url,
                               @HeaderMap Map<String, String> heads,
                               @QueryMap Map<String, String> params);

    @POST
    Flowable<ResponseBody> post(@Url String url,
                                @HeaderMap Map<String, String> heads,
                                @Body RequestBody body);
}
