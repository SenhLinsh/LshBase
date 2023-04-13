package com.linsh.base.net.nas2;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/13
 *    desc   :
 * </pre>
 */
public interface INasFileInfo {

    boolean isDirectory();

    String getFileName();

    long lastModified();

    long length();
}