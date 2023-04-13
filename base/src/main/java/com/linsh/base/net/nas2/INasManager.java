package com.linsh.base.net.nas2;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/13
 *    desc   :
 * </pre>
 */
public interface INasManager {

    INasConnection connect() throws Exception;

    interface Builder {

        /**
         * @param ip 服务器 IP 地址
         */
        INasManager.Builder ip(String ip);

        /**
         * @param dir 共享文件夹名称
         */
        INasManager.Builder dir(String dir);

        /**
         * 指定登录所需的用户名密码
         *
         * @param name     用户名
         * @param password 密码
         */
        INasManager.Builder auth(String name, String password);

        /**
         * 构建 NasManager
         */
        INasManager build();
    }
}