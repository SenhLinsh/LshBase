package com.linsh.base.app;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/11/22
 *    desc   :
 * </pre>
 */
public interface IAppApiConnection<T extends android.os.IInterface> {

    void onServiceConnected(String packageName, T aidlApi);

    void onServiceDisconnected(String packageName);
}