package com.linsh.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.linsh.base.mvp.Contract;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/05
 *    desc   :
 * </pre>
 */
public interface IntentDelegate {

    IntentDelegate context(Context context);

    IntentDelegate target(Class<? extends Activity> activity);

    IntentDelegate target(String className);

    IntentDelegate targetInPath(String path);

    IntentDelegate putExtra(String value);

    IntentDelegate putExtras(String... values);

    String getStringExtra();

    String getStringExtra(int index);

    IntentDelegate putExtra(int value);

    IntentDelegate putExtras(int... values);

    int getIntExtra();

    int getIntExtra(int index);

    IntentDelegate putExtra(long value);

    IntentDelegate putExtras(long... values);

    long getLongExtra();

    long getLongExtra(int index);

    IntentDelegate putExtra(float value);

    IntentDelegate putExtras(float... values);

    float getFloatExtra();

    float getFloatExtra(int index);

    IntentDelegate putExtra(double value);

    IntentDelegate putExtras(double... values);

    double getDoubleExtra();

    double getDoubleExtra(int index);

    IntentDelegate putExtra(boolean value);

    IntentDelegate putExtras(boolean... values);

    boolean getBooleanExtra();

    boolean getBooleanExtra(int index);

    IntentDelegate putExtra(Serializable value);

    IntentDelegate putExtras(Serializable... values);

    Serializable getSerializableExtra();

    Serializable getSerializableExtra(int index);

    IntentDelegate putExtra(Parcelable value);

    IntentDelegate putExtras(Parcelable... values);

    Parcelable getParcelableExtra();

    Parcelable getParcelableExtra(int index);

    IntentDelegate putJsonExtra(Object value);

    IntentDelegate putJsonExtras(Object... values);

    <T> T getJsonExtra(Class<T> classOfT);

    IntentDelegate putExtra(String key, Object value);

    IntentDelegate setData(Uri data);

    Uri getData();

    IntentDelegate setAction(String action);

    IntentDelegate setType(String type);

    IntentDelegate setDataAndType(Uri data, String type);

    IntentDelegate addFlags(int flags);

    IntentDelegate addCategory(String category);

    IntentDelegate newTask();

    Intent getIntent();

    void start();

    void start(Context context);

    void startForResult(Activity activity, int requestCode);

    void startForResult(Activity activity, int requestCode, ActivitySubscribe.OnActivityResult subscriber);

    IntentDelegate subscribe(Class<? extends ActivitySubscribe> subscriber);

    List<Class<? extends ActivitySubscribe>> getSubscribers();

    IntentDelegate presenter(Class<? extends Contract.Presenter> presenter);

    Class<? extends Contract.Presenter> getPresenter();
}
