package com.linsh.base.config;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/04
 *    desc   : Logger 相关配置
 * </pre>
 */
public class LogConfig implements Config {
    public String defaultTag = null;
    public int printToLogcatLevel = 0;
    public int printToLogFileLevel = 2;
    public int printToErrorFileLevel = 3;
    public long maxFileSize = 500 * 1024L;
}
