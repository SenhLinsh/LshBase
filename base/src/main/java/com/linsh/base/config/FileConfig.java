package com.linsh.base.config;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   :
 * </pre>
 */
public class FileConfig implements Config {

    private final String appDirName;

    private FileConfig(String appDirName) {
        this.appDirName = appDirName;
    }

    public String appDirName() {
        return appDirName;
    }

    public static class Builder {

        private String appDirName;

        public Builder() {
        }

        public Builder appDirName(String appDirName) {
            this.appDirName = appDirName;
            return this;
        }

        public FileConfig build() {
            return new FileConfig(appDirName);
        }
    }
}