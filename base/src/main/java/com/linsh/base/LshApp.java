package com.linsh.base;

import com.linsh.base.app.IContactAppManager;
import com.linsh.base.app.ITextAppManager;
import com.linsh.base.app.impl.ContactAppManagerImpl;
import com.linsh.base.app.impl.DefaultTextAppManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   :
 * </pre>
 */
public class LshApp {

    public static ITextAppManager text() {
        return new DefaultTextAppManager();
    }

    public static IContactAppManager contact() {
        return new ContactAppManagerImpl();
    }
}
