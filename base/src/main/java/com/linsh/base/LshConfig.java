package com.linsh.base;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linsh.base.config.Config;
import com.linsh.base.config.PublicConfig;
import com.linsh.utilseverywhere.AppUtils;
import com.linsh.utilseverywhere.ContextUtils;
import com.linsh.utilseverywhere.DeviceUtils;
import com.linsh.utilseverywhere.FileUtils;
import com.linsh.utilseverywhere.ObjectUtils;
import com.linsh.utilseverywhere.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2019/05/07
 *    desc   : Config 配置文件管理及校验
 *
 *              在某些情况下, 我们需要在不修改代码重新编译的情况下, 对配置进行修改, 如更改 api 环境及有后台根据
 *              实际情况来修改配置.
 *
 *              该模块提供了可以动态修改配置的方式和规范.
 *
 *              首先, 你需要定义一个用于获取配置的类, 该类的设计必须符合 json 的转化标准, 同时实现 {@link Config}
 *              接口作为约束.
 *
 *              通过调用 {@link LshConfig#get(Class)} 方法, 来获取设置好的配置.
 *
 *
 *              配置定义和修改的地方:
 *              1. 项目代码中, 在 assets/config 文件夹下, 定义与类同名的文件, 将该类的默认配置转成 json 字符串写入.
 *                  建议以这种方式定义为项目默认配置
 *              2. sdcard 中, 在 sdcard/imprexion/config/<package name> 文件夹下 (Release 版本),
 *                            或 sdcard/imprexion/config@debug/<package name> 文件夹下 (Debug 版本),
 *              定义与类同名的文件, 将该类的默认配置转成 json 字符串写入.
 *                  这种方式定义为可修改的配置, 如线下调试或后台配置变化时进行修改.
 *
 *
 *              配置文件的读取方式和优先级:
 *                  先读 assets 资源配置, 再读 sdcard 配置进行覆盖. 因此优先级: sdcard > assets > 类初始化
 *
 *              注: 1. 如果 sdcard 没有配置文件, 默认会将 assets 文件的内容复制到 sdcard 指定的目录, 便于修改.
 *                  2. sdcard 文件被修改, 会覆盖 assets 文件的配置. 如果想把配置初始化为默认的, 直接删除该文件即可.
 *
 *
 *              PublicConfig 配置文件管理及校验
 *
 *                PublicConfig 用于全局应用共享的配置项, 如 HttpConfig 等, 各应用默认不应使用配置项
 * </pre>
 */
public class LshConfig {

    //    private static final String config_dir_path = Environment.getExternalStorageDirectory() + "/linsh/config";
    private static final String config_dir_path_from_text = Environment.getExternalStorageDirectory() + "/linsh/text/开发/linsh/config/";
    //    private static final String public_config_dir_path = Environment.getExternalStorageDirectory()
    //            + "/linsh/config/com.linsh.base/";
    private static final String public_config_dir_path_from_text = Environment.getExternalStorageDirectory()
            + "/linsh/text/开发/linsh/config/com.linsh.base/";

    private static final Map<Class<? extends Config>, Config> defaultConfigs = new HashMap<>();


    private LshConfig() {
    }

    /**
     * 获取配置文件, 文件名必须与类名相同
     *
     * @param configClass 定义配置文件的类, 配置类需实现 {@link Config} 接口
     */
    public static <T extends Config> T get(Class<T> configClass) {
        // 1. 默认配置
        if (defaultConfigs.size() > 0) {
            Config config = defaultConfigs.get(configClass);
            if (config != null)
                return (T) config;
        }
        // 2. 配置文件
        Gson gson = new Gson();
        Map<String, Object> configMap = null;
        String filename = configClass.getSimpleName();
        // 2.1 asset/config
        String json = ResourceUtils.getTextFromAssets("config/" + filename);
        if (json == null) {
            throw new IllegalArgumentException("获取配置 <" + filename + "> 时, 需求 assets/config 目录下预先保存默认配置");
        }
        try {
            configMap = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            throw new IllegalArgumentException("assets 配置文件 <" + filename + "> 解析出错, 请检查文本格式", e);
        }
        // 2.2 sdcard
        File file = getConfigFile(configClass);
        json = FileUtils.readAsString(file);
        if (json != null) {
            try {
                Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
                }.getType());
                if (configMap == null) configMap = map;
                else configMap.putAll(map);
            } catch (Exception e) {
                Log.e(BuildConfig.TAG, "sdcard 配置文件 <" + filename + "> 解析出错, 请检查文本格式", e);
            }
        }

        // 序列化, 再反序列化成对象
        json = gson.toJson(configMap);
        T t = gson.fromJson(json, configClass);
        ObjectUtils.checkNotNull(t, filename + " 读取失败或解析失败, 请根据 LshConfig 相关规范进行配置");
        // 如果 sdcard 文件不存在, 则将设置结果写入到 sdcard
        if (!file.exists()) {
            FileUtils.writeString(file, json);
        }
        return t;
    }

    /**
     * 获取 SD 卡配置文件目录
     *
     * @param configClass 定义配置文件的类, 配置类需实现 {@link Config} 接口
     */
    public static File getConfigFile(Class<? extends Config> configClass) {
        File dir = new File(config_dir_path_from_text, ContextUtils.getPackageName());
        if (!dir.exists()) {
            return null;
        }
        String name = configClass.getSimpleName();
        String debugPostFix = AppUtils.isAppDebug() ? "@debug" : "";
        String serial = "@" + DeviceUtils.getMobileModel().replaceAll(" ", "");
        File file = new File(dir, name + debugPostFix + serial);
        if (file.exists()) {
            return file;
        }
        file = new File(dir, name + debugPostFix);
        if (file.exists()) {
            return file;
        }
        file = new File(dir, name + serial);
        if (file.exists()) {
            return file;
        }
        return new File(dir, name);
    }

    /**
     * 获取公共配置文件
     *
     * @param configClass 定义配置文件的类, 配置类需实现 {@link PublicConfig} 接口
     */
    public static <T extends PublicConfig> T getPublic(Class<T> configClass) {
        Gson gson = new Gson();
        String filename = configClass.getSimpleName();
        // 最后读 sdcard 的
        File file = getPublicConfigFile(configClass);
        if (!file.exists()) {
            ResourceUtils.copyAssetsFileToStorage("config/public/" + configClass.getSimpleName(), file);
        }
        String json = FileUtils.readAsString(file);
        if (json == null) {
            throw new RuntimeException("无法获取公共配置参数, 请检查是否已根据规范进行配置.");
        }
        T t = gson.fromJson(json, configClass);
        ObjectUtils.checkNotNull(t, filename + " 解析失败, 请检查配置内容");
        return t;
    }

    /**
     * 获取 SD 卡配置文件目录
     *
     * @param configClass 定义配置文件的类, 配置类需实现 {@link PublicConfig} 接口
     */
    public static File getPublicConfigFile(Class<? extends PublicConfig> configClass) {
        File dir = new File(public_config_dir_path_from_text, ContextUtils.getPackageName());
        if (!dir.exists()) {
            return null;
        }
        String name = configClass.getSimpleName();
        String debugPostFix = AppUtils.isAppDebug() ? "@debug" : "";
        String serial = "@" + DeviceUtils.getMobileModel().replaceAll(" ", "");
        File file = new File(dir, name + debugPostFix + serial);
        if (file.exists()) {
            return file;
        }
        file = new File(dir, name + debugPostFix);
        if (file.exists()) {
            return file;
        }
        file = new File(dir, name + serial);
        if (file.exists()) {
            return file;
        }
        return new File(dir, name);
    }

    /**
     * 设置默认配置, 一旦默认配置设置之后, 将不再从配置文件中查找, 每次直接使用默认配置
     *
     * @param configClass 定义配置文件的类, 配置类需实现 {@link Config} 接口
     * @param config      默认配置对象
     */
    public static <T extends Config> void setDefaultConfig(Class<T> configClass, T config) {
        defaultConfigs.put(configClass, config);
    }
}
