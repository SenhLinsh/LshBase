package com.linsh.base.net.http;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : Post 请求构造器的接口
 *
 *             该构造器会根据添加不同类型的请求体, 而重新创建指定的请求构造器
 * </pre>
 */
public interface PostBuilder extends RequestBuilder<PostBuilder> {

    /**
     * 添加请求体
     *
     * @param body 请求体
     * @return 普通请求构造器
     */
    BodyBuilder body(RequestBody body);

    /**
     * 添加请求体
     *
     * @param body 将字符串置入请求体中
     * @return 普通请求构造器
     */
    BodyBuilder body(String body);

    /**
     * 添加文件作为请求体
     *
     * @param file 文件对象
     * @return 普通请求构造器
     */
    BodyBuilder file(File file);

    /**
     * 添加文件作为请求体
     *
     * @param mediaType 指定媒体类型
     * @param file      文件对象
     * @return 普通请求构造器
     */
    BodyBuilder file(MediaType mediaType, File file);

    /**
     * 添加表单数据
     *
     * @param name  键
     * @param value 值
     * @return 表单请求构造器
     */
    FormBuilder addFormData(String name, String value);

    /**
     * 添加表单数据
     *
     * @param params 表单键值对
     * @return 表单请求构造器
     */
    FormBuilder addFormData(Map<String, String> params);

    /**
     * 添加文件到 Multipart 中
     *
     * @param file 文件对象
     * @return Multipart 请求构造器
     */
    MultipartBuilder addPart(File file);

    /**
     * 添加文件到 Multipart 中
     *
     * @param mediaType 指定媒体类型
     * @param file      文件对象
     * @return Multipart 请求构造器
     */
    MultipartBuilder addPart(MediaType mediaType, File file);

    /**
     * 添加请求体到 Multipart 中
     *
     * @param body 请求体
     * @return Multipart 请求构造器
     */
    MultipartBuilder addPart(RequestBody body);

    /**
     * 添加 Part 到 Multipart 中
     *
     * @param part
     * @return Multipart 请求构造器
     */
    MultipartBuilder addPart(MultipartBody.Part part);

    /**
     * 添加表单数据到 Multipart 中
     *
     * @param name  键
     * @param value 值
     * @return Multipart 请求构造器
     */
    MultipartBuilder addFormDataPart(String name, String value);

    /**
     * 添加表单文件到 Multipart 中
     *
     * @param name     键
     * @param filename 文件名
     * @param file     文件对象
     * @return Multipart 请求构造器
     */
    MultipartBuilder addFormDataPart(String name, String filename, File file);

    /**
     * 添加表单请求体到 Multipart 中
     *
     * @param name     键
     * @param filename 文件名
     * @param body     请求体
     * @return Multipart 请求构造器
     */
    MultipartBuilder addFormDataPart(String name, String filename, RequestBody body);
}
