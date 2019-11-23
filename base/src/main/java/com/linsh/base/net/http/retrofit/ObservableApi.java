package com.linsh.base.net.http.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/11
 *    desc   : 提供用于创建 Observable 事件流的简单 API 接口
 * </pre>
 */
interface ObservableApi {

    @GET
    Observable<ResponseBody> get(@Url String url);

    @POST
    Observable<ResponseBody> post(@Url String url);
}
