package com.linsh.base.config;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   : Http 配置项
 * </pre>
 */
public class HttpConfig implements Config {

    private final String baseUrl;
    private long connectTimeout;
    private long readTimeout;
    private final HashMap<String, String> headers;

    private HttpConfig(String baseUrl, long connectTimeout, long readTimeout, HashMap<String, String> headers) {
        this.baseUrl = baseUrl;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.headers = headers;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public long connectTimeout() {
        return connectTimeout;
    }

    public long readTimeout() {
        return readTimeout;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private String baseUrl;
        private long connectTimeout;
        private long readTimeout;
        private final HashMap<String, String> headers = new HashMap<>();

        public Builder() {
        }

        public Builder(HttpConfig config) {
            baseUrl = config.baseUrl;
            headers.putAll(config.headers);
        }

        /**
         * 设置 base url, 必填项
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 添加 Header
         */
        public Builder addHeader(String key, String value) {
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                return this;
            }
            headers.put(key, value);
            return this;
        }

        /**
         * 添加 Header
         */
        public Builder addHeader(HashMap<String, String> headers) {
            if (headers == null) {
                return this;
            }
            this.headers.putAll(headers);
            return this;
        }

        /**
         * 移除 Header
         */
        public Builder removeHeader(String key) {
            headers.remove(key);
            return this;
        }

        /**
         * 设置连接超时时间, 单位: ms
         */
        public Builder connectTimeout(long timeout) {
            connectTimeout = timeout;
            return this;
        }

        /**
         * 设置读流超时时间, 单位: ms
         */
        public Builder readTimeout(long timeout) {
            readTimeout = timeout;
            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(baseUrl, connectTimeout, readTimeout, headers);
        }
    }

}
