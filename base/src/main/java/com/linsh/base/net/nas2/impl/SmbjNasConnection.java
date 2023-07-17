package com.linsh.base.net.nas2.impl;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.FileTime;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.msfscc.fileinformation.FileAllInformation;
import com.hierynomus.msfscc.fileinformation.FileBasicInformation;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.linsh.base.net.nas2.INasConnection;
import com.linsh.base.net.nas2.INasFileInfo;
import com.linsh.utilseverywhere.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/13
 *    desc   : Smbj 实现
 *
 *            目前存在问题：
 *            1. 删除文件或文件后，重新 list 可能会保留之前的缓存。如需要严谨，可在 list 之后，操作文件之前，
 *               使用 fileExists 来判断文件是否存在。
 * </pre>
 */
class SmbjNasConnection implements INasConnection {

    private final SMBClient client;
    private final Connection connection;
    private final Session session;
    private final DiskShare share;

    SmbjNasConnection(SMBClient client, Connection connection, Session session, DiskShare share) {
        this.client = client;
        this.connection = connection;
        this.session = session;
        this.share = share;
    }

    @Override
    public INasFileInfo getFileInfo(String path) throws Exception {
        return new SmbjFileInfo(share.getFileInformation(path, FileAllInformation.class));
    }

    @Override
    public boolean fileExists(String path) throws Exception {
        return share.fileExists(path);
    }

    @Override
    public boolean folderExists(String path) throws Exception {
        return share.folderExists(path);
    }

    // list 查找的内容可能被缓存，即被删除后继续查找还会存在，需要通过 fileExists 来判断文件是否存在，再继续相关操作
    @Override
    public List<INasFileInfo> list(String path) throws Exception {
        List<FileIdBothDirectoryInformation> informations = share.list(path);
        List<INasFileInfo> fileInfos = new ArrayList<>();
        for (FileIdBothDirectoryInformation information : informations) {
            if (information.getFileName().equals(".") || information.getFileName().equals(".."))
                continue;
            fileInfos.add(new SmbjFileInfo(information));
        }
        return fileInfos;
    }

    @Override
    public void mkdir(String path) throws Exception {
        share.mkdir(path);
    }

    @Override
    public InputStream pull(String path) throws Exception {
        com.hierynomus.smbj.share.File file = share.openFile(path,
                new HashSet<>(Collections.singletonList(AccessMask.GENERIC_ALL)),
                new HashSet<>(Collections.singletonList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OPEN,
                new HashSet<>(Collections.singletonList(SMB2CreateOptions.FILE_DIRECTORY_FILE)));
        return file.getInputStream();
    }

    @Override
    public void download(String path, File dest) throws Exception {
        InputStream inputStream = pull(path);
        StreamUtils.write(inputStream, dest);
    }

    @Override
    public void push(String path, InputStream in) throws Exception {
        com.hierynomus.smbj.share.File file = share.openFile(path,
                new HashSet<>(Collections.singletonList(AccessMask.GENERIC_ALL)),
                new HashSet<>(Collections.singletonList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_CREATE,
                new HashSet<>(Collections.singletonList(SMB2CreateOptions.FILE_DIRECTORY_FILE)));
        OutputStream outputStream = file.getOutputStream();
        StreamUtils.write(in, outputStream);
    }

    @Override
    public void upload(String path, File src) throws Exception {
        push(path, new FileInputStream(src));
    }

    @Override
    public void delete(String path) throws Exception {
        share.rm(path);
    }

    // 注：需要先删除文件夹下面的文件，才能删除当前文件夹
    @Override
    public void deleteDir(String path) throws Exception {
        share.rmdir(path, true);
    }

    @Override
    public void move(String srcPath, String destPath) throws Exception {
        com.hierynomus.smbj.share.File file = share.openFile(srcPath,
                new HashSet<>(Collections.singletonList(AccessMask.GENERIC_ALL)),
                new HashSet<>(Collections.singletonList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OPEN,
                new HashSet<>(Collections.singletonList(SMB2CreateOptions.FILE_DIRECTORY_FILE)));
        file.rename(destPath);
        file.close();
    }

    @Override
    public void setLastModified(String path, long time) throws Exception {
        com.hierynomus.smbj.share.File file = share.openFile(path,
                new HashSet<>(Collections.singletonList(AccessMask.GENERIC_ALL)),
                new HashSet<>(Collections.singletonList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
                SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OPEN,
                new HashSet<>(Collections.singletonList(SMB2CreateOptions.FILE_DIRECTORY_FILE)));
        FileBasicInformation information = file.getFileInformation(FileBasicInformation.class);
        FileTime fileTime = FileTime.ofEpochMillis(time);
        file.setFileInformation(new FileBasicInformation(fileTime,
                fileTime, fileTime, fileTime, information.getFileAttributes()));
        file.close();
    }

    @Override
    public void close() throws IOException {
        share.close();
        session.close();
        connection.close();
        client.close();
    }
}
