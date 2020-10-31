package com.linsh.base;

import com.linsh.base.app.IAppManager;
import com.linsh.base.app.ITextAppManager;
import com.linsh.base.app.impl.DefaultAppManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   :
 * </pre>
 */
public class LshApp {

    private static final IAppManager APP_MANAGER = new DefaultAppManager();

    public static ITextAppManager text() {
        return APP_MANAGER.text();
    }
}
