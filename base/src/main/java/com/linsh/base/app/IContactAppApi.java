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
    String APP_NAME = "友人帐";
    String APP_NAME_EN = "contact";
    String PATH_CONTACT = IBaseAppApi.PATH_LINSH + APP_NAME_EN + "/";

    String ACTIVITY_NAME_SEARCH = "com.linsh.contact.page.search.SearchActivity";
    String ACTIVITY_NAME_PERSON = "com.linsh.contact.page.person_detail.PersonDetailActivity";
    String EXTRA_SEARCH_TYPE = "type";
    String EXTRA_SEARCH_TYPE_PERSON = "person";
    String EXTRA_PERSON_ID = "person.id";
    String EXTRA_PERSON_NAME = "person.name";
    String RESULT_GROUP_NAME = "group.name";
    String RESULT_PERSON_NAME = "person.name";
    String RESULT_PERSON_ID = "person.id";

    void gotoSearch(Activity activity, int requestCode);

    void gotoPerson(Activity activity, int requestCode, String personName);
}
