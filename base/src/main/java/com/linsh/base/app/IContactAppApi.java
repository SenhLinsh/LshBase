package com.linsh.base.app;

import android.app.Activity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/01/19
 *    desc   : 友人帐应用接口
 * </pre>
 */
public interface IContactAppApi {

    String PACKAGE_NAME = "com.linsh.contact";
    String ACTIVITY_NAME_SEARCH = "com.linsh.contact.page.search.SearchActivity";
    String EXTRA_SEARCH_TYPE = "type";
    String EXTRA_SEARCH_TYPE_PERSON = "person";
    String RESULT_GROUP_NAME = "group.name";
    String RESULT_PERSON_NAME = "person.name";
    String RESULT_PERSON_ID = "person.id";

    void gotoSearch(Activity activity, int requestCode);
}
