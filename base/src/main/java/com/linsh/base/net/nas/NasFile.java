package com.linsh.base.net.nas;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/12/09
 *    desc   :
 * </pre>
 */
public interface NasFile {

    String getParent();

    String getPath();

    String getName();

    boolean exists() throws Exception;

    boolean isDirectory() throws Exception;

    boolean isFile() throws Exception;

    long length() throws Exception;

    void mkdir() throws Exception;

    void mkdirs() throws Exception;

    String[] list() throws Exception;

    long lastModified() throws Exception;

    void setLastModified(long time) throws Exception;
}
