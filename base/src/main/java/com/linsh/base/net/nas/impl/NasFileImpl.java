package com.linsh.base.net.nas.impl;

import com.linsh.base.net.nas.NasFile;

import jcifs.smb.SmbFile;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/12/09
 *    desc   :
 * </pre>
 */
class NasFileImpl implements NasFile {

    private final SmbFile file;

    public NasFileImpl(SmbFile file) {
        this.file = file;
    }

    public SmbFile getFile() {
        return file;
    }

    @Override
    public String getParent() {
        return file.getParent();
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public boolean exists() throws Exception {
        return file.exists();
    }

    @Override
    public boolean isDirectory() throws Exception {
        return file.isDirectory();
    }

    @Override
    public boolean isFile() throws Exception {
        return file.isFile();
    }

    @Override
    public long length() throws Exception {
        return file.length();
    }

    @Override
    public void mkdir() throws Exception {
        file.mkdir();
    }

    @Override
    public void mkdirs() throws Exception {
        file.mkdirs();
    }

    @Override
    public String[] list() throws Exception {
        return file.list();
    }

    @Override
    public long lastModified() throws Exception {
        return file.lastModified();
    }
}
