package com.linsh.base.net.http.retrofit;

import com.linsh.utilseverywhere.ExceptionUtils;

import retrofit2.Response;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/17
 *    desc   :
 * </pre>
 */
public class HttpException extends RuntimeException {

    public HttpException(Response<?> response) {
        super(getMessage(response));
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    private static String getMessage(Response<?> response) {
        ExceptionUtils.checkNotNull(response, "response == null");
        return "HTTP " + response.code() + " " + response.message();
    }
}
