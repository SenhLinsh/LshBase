package com.linsh.base.log;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/17
 *    desc   :
 * </pre>
 */
public interface ILogFileManager {

    File getLogFile(long time);

    File getLogErrorFile(long time);
}
