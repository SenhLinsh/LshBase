package com.linsh.base.net.http;

import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : 表单请求构造器, 该构造器可以为请求提添加表单数据
 * </pre>
 */
public interface FormBuilder extends RequestBuilder<FormBuilder> {

    /**
     * 添加表单数据
     */
    FormBuilder addFormData(String name, String value);

    /**
     * 添加表单数据
     */
    FormBuilder addFormData(Map<String, String> params);
}
