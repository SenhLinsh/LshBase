package com.linsh.base.app;

import android.content.ServiceConnection;

import androidx.annotation.NonNull;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2023/11/22
 *    desc   :
 * </pre>
 */
public interface IAppApiConnection<T extends android.os.IInterface> {
    void onServiceConnected(@NonNull String packageName, @NonNull ServiceConnection connection, @NonNull T aidlApi);

    void onServiceDisconnected(@NonNull String packageName);
}