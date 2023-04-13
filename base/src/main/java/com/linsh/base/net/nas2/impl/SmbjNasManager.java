package com.linsh.base.net.nas2.impl;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.linsh.base.net.nas2.INasConnection;
import com.linsh.base.net.nas2.INasManager;
import com.linsh.utilseverywhere.StringUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/04/13
 *    desc   :
 * </pre>
 */
public class SmbjNasManager implements INasManager {

    private final String ip;
    private final String dir;
    private final String name;
    private final String password;

    private SmbjNasManager(String ip, String dir, String name, String password) {
        this.ip = ip;
        this.dir = dir;
        this.name = name;
        this.password = password;
    }

    @Override
    public INasConnection connect() throws Exception {
        SMBClient client = new SMBClient();
        Connection connection = client.connect(ip);
        Session session = connection.authenticate(new AuthenticationContext(name, password.toCharArray(), "linsh"));
        DiskShare share = (DiskShare) session
                .connectShare(dir);
        return new SmbjNasConnection(client, connection, session, share);
    }

    public static class Builder implements INasManager.Builder {

        private String ip;
        private String dir;
        private String name;
        private String password;

        @Override
        public INasManager.Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        @Override
        public INasManager.Builder dir(String dir) {
            this.dir = dir;
            return this;
        }

        @Override
        public INasManager.Builder auth(String name, String password) {
            this.name = name;
            this.password = password;
            return this;
        }

        @Override
        public INasManager build() {
            if (StringUtils.isEmpty(ip))
                throw new IllegalArgumentException("ip is required");
            if (StringUtils.isEmpty(dir))
                throw new IllegalArgumentException("dir is required");
            return new SmbjNasManager(ip, dir, name, password);
        }
    }
}
