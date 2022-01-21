package com.linsh.base;

import com.linsh.base.app.IContactAppApi;
import com.linsh.base.app.IPhotographyAppApi;
import com.linsh.base.app.ITextAppApi;
import com.linsh.base.app.impl.ContactAppApiImpl;
import com.linsh.base.app.impl.DefaultTextAppApi;
import com.linsh.base.app.impl.PhotographyAppApiImpl;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/10/31
 *    desc   :
 * </pre>
 */
public class LshApp {

    /**
     * 字字珠玑应用接口
     */
    public static ITextAppApi text() {
        return new DefaultTextAppApi();
    }

    /**
     * 友人帐应用接口
     */
    public static IContactAppApi contact() {
        return new ContactAppApiImpl();
    }

    /**
     * 朝花夕逝应用接口
     */
    public static IPhotographyAppApi photography() {
        return new PhotographyAppApiImpl();
    }
}
