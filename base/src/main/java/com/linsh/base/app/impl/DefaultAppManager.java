package com.linsh.base.app.impl;

import com.linsh.base.app.IAppManager;
import com.linsh.base.app.ITextAppManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   :
 * </pre>
 */
public class DefaultAppManager implements IAppManager {

    @Override
    public ITextAppManager text() {
        return new DefaultTextAppManager();
    }
}
