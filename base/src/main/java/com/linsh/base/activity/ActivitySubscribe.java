package com.linsh.base.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/07/19
 *    desc   : Activity 订阅相关接口
 *             实现继承于 ActivitySubscribe 并注册于 Register 的子接口, 在启动 Activity 之前或
 *             启动时, 进行注册, 即可获取该 Activity 相关的回调.
 * </pre>
 */
public interface ActivitySubscribe {

    /**
     * 绑定 Activity
     *
     * @param activity Activity
     */
    void attach(Activity activity);

    /**
     * 为 Activity 回调相关的订阅事件接口进行注册, 只有注册过的订阅接口才能获得订阅回调的能力.
     * 擅自继承 ActivitySubscribe 的接口由于无法进行注册, 所以无法获得订阅回调的能力.
     */
    class Register {

        /**
         * 已注册的订阅回调时间接口的集合
         */
        static final HashSet<Class<? extends ActivitySubscribe>> DATA;

        static {
            DATA = new HashSet<>();
            DATA.add(OnCreate.class);
            DATA.add(OnStart.class);
            DATA.add(OnResume.class);
            DATA.add(OnPause.class);
            DATA.add(OnStop.class);
            DATA.add(OnDestroy.class);

            DATA.add(OnCreateV2.class);
            DATA.add(OnPostCreate.class);
            DATA.add(OnPostCreateV2.class);
            DATA.add(OnRestart.class);
            DATA.add(OnPostResume.class);
            DATA.add(OnNewIntent.class);

            DATA.add(OnSaveInstanceState.class);
            DATA.add(OnSaveInstanceStateV2.class);
            DATA.add(OnRestoreInstanceState.class);
            DATA.add(OnRestoreInstanceStateV2.class);

            DATA.add(OnRequestPermissionsResult.class);
            DATA.add(OnActivityResult.class);

            DATA.add(OnCreateOptionsMenu.class);
            DATA.add(OnPrepareOptionsMenu.class);
            DATA.add(OnOptionsItemSelected.class);
            DATA.add(OnOptionsMenuClosed.class);

            DATA.add(OnKeyDown.class);
            DATA.add(OnKeyLongPress.class);
            DATA.add(OnKeyMultiple.class);
            DATA.add(OnKeyShortcut.class);
            DATA.add(OnKeyUp.class);
            DATA.add(OnBackPressed.class);
            DATA.add(OnTouchEvent.class);

            DATA.add(OnPanelClosed.class);
            DATA.add(OnCreatePanelMenu.class);
            DATA.add(OnPreparePanel.class);
            DATA.add(OnCreatePanelView.class);

            DATA.add(OnDetachedFromWindow.class);
            DATA.add(OnAttachedToWindow.class);
            DATA.add(OnUserLeaveHint.class);
            DATA.add(OnUserInteraction.class);

            DATA.add(OnContextMenuClosed.class);
            DATA.add(OnCreateContextMenu.class);
            DATA.add(OnContextItemSelected.class);

            DATA.add(OnLowMemory.class);
            DATA.add(OnTrimMemory.class);

            DATA.add(OnApplyThemeResource.class);
            DATA.add(OnMenuOpened.class);
            DATA.add(OnAttachFragment.class);
            DATA.add(OnEnterAnimationComplete.class);
        }

        public static void register(Class<? extends ActivitySubscribe> clazz) {
            DATA.add(clazz);
        }

        public static boolean contains(Class<? extends ActivitySubscribe> clazz) {
            return DATA.contains(clazz);
        }
    }

    //========================================= Lifecycle =========================================//
    interface OnCreate extends ActivitySubscribe {
        void onCreate(@Nullable Bundle savedInstanceState);
    }

    interface OnStart extends ActivitySubscribe {
        void onStart();
    }

    interface OnResume extends ActivitySubscribe {
        void onResume();
    }

    interface OnPause extends ActivitySubscribe {
        void onPause();
    }

    interface OnStop extends ActivitySubscribe {
        void onStop();
    }

    interface OnDestroy extends ActivitySubscribe {
        void onDestroy();
    }

    //========================================= LifecycleEx =========================================//
    interface OnCreateV2 extends ActivitySubscribe {
        void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState);
    }

    interface OnPostCreate extends ActivitySubscribe {
        void onPostCreate(@Nullable Bundle savedInstanceState);
    }

    interface OnPostCreateV2 extends ActivitySubscribe {
        void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState);
    }

    interface OnRestart extends ActivitySubscribe {
        void onRestart();
    }

    interface OnPostResume extends ActivitySubscribe {
        void onPostResume();
    }

    interface OnNewIntent extends ActivitySubscribe {
        void onNewIntent(Intent intent);
    }

    //========================================= InstanceState =========================================//
    interface OnSaveInstanceState extends ActivitySubscribe {
        void onSaveInstanceState(Bundle outState);
    }

    interface OnSaveInstanceStateV2 extends ActivitySubscribe {
        void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState);
    }

    interface OnRestoreInstanceState extends ActivitySubscribe {
        void onRestoreInstanceState(Bundle outState);
    }

    interface OnRestoreInstanceStateV2 extends ActivitySubscribe {
        void onRestoreInstanceState(Bundle outState, PersistableBundle outPersistentState);
    }

    //========================================= Result =========================================//
    interface OnRequestPermissionsResult extends ActivitySubscribe {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }

    interface OnActivityResult extends ActivitySubscribe {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    //========================================= OptionsMenu =========================================//
    interface OnCreateOptionsMenu extends ActivitySubscribe {
        boolean onCreateOptionsMenu(Menu menu);
    }

    interface OnPrepareOptionsMenu extends ActivitySubscribe {
        boolean onPrepareOptionsMenu(Menu menu);
    }

    interface OnOptionsItemSelected extends ActivitySubscribe {
        boolean onOptionsItemSelected(MenuItem item);
    }

    interface OnOptionsMenuClosed extends ActivitySubscribe {
        void onOptionsMenuClosed(Menu menu);
    }

    //========================================= Event =========================================//
    interface OnKeyDown extends ActivitySubscribe {
        boolean onKeyDown(int keyCode, KeyEvent event);
    }

    interface OnKeyLongPress extends ActivitySubscribe {
        boolean onKeyLongPress(int keyCode, KeyEvent event);
    }

    interface OnKeyMultiple extends ActivitySubscribe {
        boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event);
    }

    interface OnKeyShortcut extends ActivitySubscribe {
        boolean onKeyShortcut(int keyCode, KeyEvent event);
    }

    interface OnKeyUp extends ActivitySubscribe {
        boolean onKeyUp(int keyCode, KeyEvent event);
    }

    interface OnBackPressed extends ActivitySubscribe {
        void onBackPressed();
    }

    interface OnTouchEvent extends ActivitySubscribe {
        boolean onTouchEvent(MotionEvent event);
    }

    //========================================= Panel =========================================//
    interface OnPanelClosed extends ActivitySubscribe {
        void onPanelClosed(int featureId, Menu menu);
    }

    interface OnCreatePanelMenu extends ActivitySubscribe {
        boolean onCreatePanelMenu(int featureId, Menu menu);
    }

    interface OnPreparePanel extends ActivitySubscribe {
        boolean onPreparePanel(int featureId, View view, Menu menu);
    }

    interface OnCreatePanelView extends ActivitySubscribe {
        View onCreatePanelView(int featureId);
    }

    //========================================= Window =========================================//
    interface OnDetachedFromWindow extends ActivitySubscribe {
        void onDetachedFromWindow();
    }

    interface OnAttachedToWindow extends ActivitySubscribe {
        void onAttachedToWindow();
    }

    interface OnUserLeaveHint extends ActivitySubscribe {
        void onUserLeaveHint();
    }

    interface OnUserInteraction extends ActivitySubscribe {
        void onUserInteraction();
    }

    //========================================= ContextMenu =========================================//
    interface OnContextMenuClosed extends ActivitySubscribe {
        void onContextMenuClosed(Menu menu);
    }

    interface OnCreateContextMenu extends ActivitySubscribe {
        void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo);
    }

    interface OnContextItemSelected extends ActivitySubscribe {
        boolean onContextItemSelected(MenuItem item);
    }

    //========================================= Memory =========================================//

    interface OnLowMemory extends ActivitySubscribe {
        void onLowMemory();
    }

    interface OnTrimMemory extends ActivitySubscribe {
        void onTrimMemory(int level);
    }

    //========================================= Other =========================================//
    interface OnApplyThemeResource extends ActivitySubscribe {
        void onApplyThemeResource(Resources.Theme theme, int resid, boolean first);
    }

    interface OnMenuOpened extends ActivitySubscribe {
        boolean onMenuOpened(int featureId, Menu menu);
    }

    interface OnAttachFragment extends ActivitySubscribe {
        void onAttachFragment(Fragment fragment);
    }

    interface OnEnterAnimationComplete extends ActivitySubscribe {
        void onEnterAnimationComplete();
    }
}
