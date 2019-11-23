package com.linsh.base.config;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   :
 * </pre>
 */
public class FileConfig implements Config {

    private final File appDir;

    private FileConfig(File appDir) {
        this.appDir = appDir;
    }

    public File appDir() {
        return appDir;
    }

    public static class Builder {

        private File appDir;

        public Builder() {
        }

        public Builder appDir(File appDir) {
            this.appDir = appDir;
            return this;
        }

        public FileConfig build() {
            return new FileConfig(appDir);
        }
    }
}