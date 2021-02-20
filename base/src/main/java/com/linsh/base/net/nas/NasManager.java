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
     * 构建 NasFile 对象
     *
     * @param path 文件路径
     */
    NasFile file(String path) throws Exception;

    /**
     * 构建 NasFile 对象
     *
     * @param parent 父文件夹
     * @param name   文件名
     */
    NasFile file(NasFile parent, String name) throws Exception;

    /**
     * 列出指定文件夹下的文件
     *
     * @param path 文件夹路径
     */
    String[] list(String path) throws Exception;

    /**
     * 列出指定文件夹下的文件
     *
     * @param nasFile 文件
     */
    String[] list(NasFile nasFile) throws Exception;

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    void mkdir(String path) throws Exception;

    /**
     * 创建文件夹
     *
     * @param nasFile 文件
     */
    void mkdir(NasFile nasFile) throws Exception;

    /**
     * 拉流
     *
     * @param path 文件路径
     */
    InputStream pull(String path) throws Exception;

    /**
     * 拉流
     *
     * @param nasFile 文件
     */
    InputStream pull(NasFile nasFile) throws Exception;

    /**
     * 读取文本
     *
     * @param path 文件路径
     */
    String read(String path) throws Exception;

    /**
     * 读取文本
     *
     * @param nasFile 文件
     */
    String read(NasFile nasFile) throws Exception;

    /**
     * 下载文件
     *
     * @param path 源文件路径
     * @param dest 目标文件
     */
    void download(String path, File dest) throws Exception;

    /**
     * 下载文件
     *
     * @param nasFile 文件
     * @param dest    目标文件
     */
    void download(NasFile nasFile, File dest) throws Exception;

    /**
     * 推流
     *
     * @param path 文件路径
     * @param in   流
     */
    void push(String path, InputStream in) throws Exception;

    /**
     * 推流
     *
     * @param nasFile 文件
     * @param in      流
     */
    void push(NasFile nasFile, InputStream in) throws Exception;

    /**
     * 写入文本
     *
     * @param path    文件路径
     * @param content 文本内容
     */
    void write(String path, String content) throws Exception;

    /**
     * 写入文本
     *
     * @param nasFile 文件
     * @param content 文本内容
     */
    void write(NasFile nasFile, String content) throws Exception;

    /**
     * 上传文件
     *
     * @param path 目标路径
     * @param src  源文件路径
     */
    void upload(String path, File src) throws Exception;

    /**
     * 上传文件
     *
     * @param nasFile 目标文件
     * @param src     源文件路径
     */
    void upload(NasFile nasFile, File src) throws Exception;

    /**
     * 删除文件
     *
     * @param path 目标路径
     */
    void delete(String path) throws Exception;

    /**
     * 删除文件
     *
     * @param nasFile 目标文件
     */
    void delete(NasFile nasFile) throws Exception;

    /**
     * 移动文件
     *
     * @param srcPath  源文件路径
     * @param destPath 目标路径
     */
    void move(String srcPath, String destPath) throws Exception;

    /**
     * 移动文件
     *
     * @param nasFile  源文件
     * @param destPath 目标路径
     */
    void move(NasFile nasFile, String destPath) throws Exception;


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
