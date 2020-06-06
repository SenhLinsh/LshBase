package com.linsh.base.net.nas;

import java.io.File;
import java.io.InputStream;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/05
 *    desc   : Nas 服务器管理
 * </pre>
 */
public interface NasManager {

    /**
     * 列出指定文件夹下的文件
     *
     * @param path 文件夹路径
     */
    String[] list(String path) throws Exception;

    /**
     * 拉取输入流
     *
     * @param path 文件路径
     */
    InputStream fetch(String path) throws Exception;

    /**
     * 下载文件
     *
     * @param path 源文件路径
     * @param dest 目标文件
     */
    void download(String path, File dest) throws Exception;

    interface Builder {

        /**
         * @param ip 服务器 IP 地址
         */
        Builder ip(String ip);

        /**
         * @param dir 共享文件夹名称
         */
        Builder dir(String dir);

        /**
         * 指定登录所需的用户名密码
         *
         * @param name     用户名
         * @param password 密码
         */
        Builder auth(String name, String password);

        /**
         * 构建 NasManager
         */
        NasManager build();
    }
}
