package com.linsh.base.net.nas2;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/13
 *    desc   :
 * </pre>
 */
public interface INasConnection extends Closeable {

    INasFileInfo getFileInfo(String path) throws Exception;

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     */
    boolean fileExists(String path) throws Exception;

    /**
     * 判断文件夹是否存在
     *
     * @param path 文件夹路径
     */
    boolean folderExists(String path) throws Exception;

    /**
     * 列出指定文件夹下的文件
     *
     * @param path 文件夹路径
     */
    List<INasFileInfo> list(String path) throws Exception;

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    void mkdir(String path) throws Exception;

    /**
     * 拉流
     *
     * @param path 文件路径
     */
    InputStream pull(String path) throws Exception;

    /**
     * 下载文件
     *
     * @param path 源文件路径
     * @param dest 目标文件
     */
    void download(String path, File dest) throws Exception;

    /**
     * 推流
     *
     * @param path 文件路径
     * @param in   流
     */
    void push(String path, InputStream in) throws Exception;

    /**
     * 上传文件
     *
     * @param path 目标路径
     * @param src  源文件路径
     */
    void upload(String path, File src) throws Exception;

    /**
     * 删除文件
     *
     * @param path 目标路径
     */
    void delete(String path) throws Exception;

    /**
     * 删除文件夹
     *
     * @param path 目标路径
     */
    void deleteDir(String path) throws Exception;

    /**
     * 移动文件
     *
     * @param srcPath  源文件路径
     * @param destPath 目标路径
     */
    void move(String srcPath, String destPath) throws Exception;

    /**
     * 设置文件修改时间
     *
     * @param path 文件路径
     * @param time 修改时间
     */
    void setLastModified(String path, long time) throws Exception;
}