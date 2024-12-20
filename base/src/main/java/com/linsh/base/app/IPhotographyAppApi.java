package com.linsh.base.app;

import android.app.Activity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/01/21
 *    desc   : 朝花夕逝应用接口
 * </pre>
 */
public interface IPhotographyAppApi {

    String PACKAGE_NAME = "com.linsh.photography";
    String APP_NAME = "朝花夕逝";
    String APP_NAME_EN = "photography";
    String PATH_PHOTOGRAPHY = IBaseAppApi.PATH_LINSH + "photography/";
    String PATH_PHOTOS = PATH_PHOTOGRAPHY + "图片/";

    String SERVICE_ACTION = "com.linsh.photography.PhotographyService";
    String ACTIVITY_NAME_MAIN = "com.linsh.photography.page.main.MainActivity";
    String EXTRA_PATH = "path";
    String EXTRA_FILTERS = "filters";
    // 浏览类型
    String EXTRA_TYPE = "type";
    // 浏览类型：默认（跳转当前页面进行进行搜索）
    int EXTRA_TYPE_DEFAULT = 0;
    // 浏览类型：选择（点击图片后返回图片MD5和路径）
    int EXTRA_TYPE_SELECTOR = 1;
    // 浏览类型：浏览（返回直接退出页面）
    int EXTRA_TYPE_BROWSE = 2;
    String RESULT_PATH = "path";
    String RESULT_MD5 = "md5";
    String QUERY_TYPE_FILENAME = "文件名";
    String QUERY_TYPE_DESC = "内容";
    String QUERY_TYPE_PERSON = "友人帐";
    String QUERY_TYPE_TAG = "标签";

    /**
     * 跳转图片列表页
     *
     * @param activity Activity 用于跳转
     * @param path     图片所在路径，如果是目录，请以 “/” 结尾
     * @param filters  过滤条件
     */
    void gotoPhotoList(Activity activity, String path, String[] filters);

    /**
     * 跳转图片浏览页
     *
     * @param activity Activity 用于跳转
     * @param path     图片所在路径，如果是目录，请以 “/” 结尾
     * @param filters  过滤条件
     */
    void gotoPhotoBrowser(Activity activity, String path, String[] filters);

    /**
     * 跳转图片列表页，且需要选择图片并返回
     *
     * @param activity    Activity 用于跳转
     * @param path        图片所在路径，如果是目录，请以 “/” 结尾
     * @param filters     过滤条件
     * @param requestCode 请求码
     */
    void gotoPhotoSelector(Activity activity, String path, String[] filters, int requestCode);

    /**
     * 连接AIDL服务
     */
    void connectService(IAppApiConnection<IPhotographyAidlApi> connection);
}
