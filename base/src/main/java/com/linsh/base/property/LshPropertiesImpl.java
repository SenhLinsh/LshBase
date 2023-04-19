package com.linsh.base.property;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linsh.base.app.ITextAppApi;
import com.linsh.register.InterfaceRegister;
import com.linsh.utilseverywhere.ContextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2022/05/30
 *    desc   :
 * </pre>
 */
@InterfaceRegister(IProperties.class)
class LshPropertiesImpl implements IProperties {

    private static final String BASE_PROPERTIES_PATH = ITextAppApi.PATH_GIT_CONFIG + "properties/base.properties";
    private static final String APP_PROPERTIES_PATH = ITextAppApi.PATH_GIT_CONFIG + "properties/" + ContextUtils.getPackageName() + ".properties";
    private final Properties properties = new Properties();

    public LshPropertiesImpl() {
        try {
            properties.load(new FileInputStream(new File(BASE_PROPERTIES_PATH)));
            properties.load(new FileInputStream(new File(APP_PROPERTIES_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(@NonNull String key) {
        return properties.getProperty(key);
    }

    @Override
    public String get(@NonNull String key, @Nullable String def) {
        return properties.getProperty(key, def);
    }

    @Override
    public int getInt(@NonNull String key, int def) {
        String property = properties.getProperty(key);
        if (property != null) {
            return Integer.parseInt(property);
        }
        return def;
    }

    @Override
    public long getLong(@NonNull String key, long def) {
        String property = properties.getProperty(key);
        if (property != null) {
            return Long.parseLong(property);
        }
        return def;
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean def) {
        String property = properties.getProperty(key);
        if (property != null) {
            return Boolean.parseBoolean(property);
        }
        return def;
    }

    @Override
    public void set(@NonNull String key, @Nullable String val) {
        properties.setProperty(key, val);
    }
}
