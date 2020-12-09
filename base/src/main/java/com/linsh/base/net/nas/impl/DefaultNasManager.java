package com.linsh.base.net.nas.impl;

import com.linsh.base.net.nas.NasFile;
import com.linsh.base.net.nas.NasManager;
import com.linsh.utilseverywhere.StreamUtils;
import com.linsh.utilseverywhere.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/06/05
 *    desc   :
 * </pre>
 */
public class DefaultNasManager implements NasManager {

    private final String ip;
    private final String dir;
    private final String name;
    private final String password;

    private DefaultNasManager(String ip, String dir, String name, String password) {
        this.ip = ip;
        this.dir = dir;
        this.name = name;
        this.password = password;
    }

    @Override
    public NasFile file(String path) throws Exception {
        return new NasFileImpl(buildSmbFile(path));
    }

    @Override
    public String[] list(String path) throws Exception {
        return buildSmbFile(path).list();
    }

    @Override
    public void mkdir(String path) throws Exception {
        buildSmbFile(path).mkdir();
    }

    @Override
    public InputStream pull(String path) throws Exception {
        return buildSmbFile(path).getInputStream();
    }

    @Override
    public String read(String path) throws Exception {
        InputStream inputStream = pull(path);
        return StreamUtils.readAsString(inputStream);
    }

    @Override
    public void download(String path, File dest) throws Exception {
        InputStream inputStream = pull(path);
        StreamUtils.write(inputStream, dest);
    }

    @Override
    public void push(String path, InputStream in) throws Exception {
        OutputStream outputStream = buildSmbFile(path).getOutputStream();
        StreamUtils.write(in, outputStream);
    }

    @Override
    public void write(String path, String content) throws Exception {
        OutputStream outputStream = buildSmbFile(path).getOutputStream();
        outputStream.write(content.getBytes());
    }

    @Override
    public void upload(String path, File src) throws Exception {
        OutputStream outputStream = buildSmbFile(path).getOutputStream();
        FileInputStream inputStream = new FileInputStream(src);
        StreamUtils.write(inputStream, outputStream);
    }

    @Override
    public void move(String srcPath, String destPath) throws Exception {
        buildSmbFile(srcPath).renameTo(buildSmbFile(destPath));
    }

    private SmbFile buildSmbFile(String path) throws Exception {
        if (name != null && password != null) {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip + ";" + name + ":" + password);
            return new SmbFile("smb://" + ip + "/" + dir + "/" + path, auth);
        }
        return new SmbFile("smb://" + ip + "/" + dir + "/" + path);
    }

    public static class Builder implements NasManager.Builder {

        private String ip;
        private String dir;
        private String name;
        private String password;

        @Override
        public NasManager.Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        @Override
        public NasManager.Builder dir(String dir) {
            this.dir = dir;
            return this;
        }

        @Override
        public NasManager.Builder auth(String name, String password) {
            this.name = name;
            this.password = password;
            return this;
        }

        @Override
        public NasManager build() {
            if (StringUtils.isEmpty(ip))
                throw new IllegalArgumentException("ip is required");
            if (StringUtils.isEmpty(dir))
                throw new IllegalArgumentException("dir is required");
            return new DefaultNasManager(ip, dir, name, password);
        }
    }
}
