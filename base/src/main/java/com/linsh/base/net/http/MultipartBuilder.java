package com.linsh.base.net.http;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : Multipart 请求构造器
 * </pre>
 */
public interface MultipartBuilder extends RequestBuilder<MultipartBuilder> {
    /**
     * 添加文件到 Multipart 中
     *
     * @param file 文件对象
     */
    MultipartBuilder addPart(File file);

    /**
     * 添加文件到 Multipart 中
     *
     * @param mediaType 指定媒体类型
     * @param file      文件对象
     */
    MultipartBuilder addPart(MediaType mediaType, File file);

    /**
     * 添加请求体到 Multipart 中
     *
     * @param body 请求体
     */
    MultipartBuilder addPart(RequestBody body);

    /**
     * 添加 Part 到 Multipart 中
     *
     * @param part
     */
    MultipartBuilder addPart(MultipartBody.Part part);

    /**
     * 添加表单数据到 Multipart 中
     *
     * @param name  键
     * @param value 值
     */
    MultipartBuilder addFormDataPart(String name, String value);

    /**
     * 添加表单文件到 Multipart 中
     *
     * @param name     键
     * @param filename 文件名
     * @param file     文件对象
     */
    MultipartBuilder addFormDataPart(String name, String filename, File file);

    /**
     * 添加表单请求体到 Multipart 中
     *
     * @param name     键
     * @param filename 文件名
     * @param body     请求体
     */
    MultipartBuilder addFormDataPart(String name, String filename, RequestBody body);
}
