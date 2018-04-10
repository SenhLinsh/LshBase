package com.linsh.base.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.linsh.base.helper.interf.ActivityHelperInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    date   : 2018/02/04
 *    desc   :
 * </pre>
 */
public abstract class BaseHelperActivity extends AppCompatActivity {

    private List<ActivityHelperInterface> mHelpers;

    public void addHelper(@NonNull ActivityHelperInterface helper) {
        if (mHelpers == null) mHelpers = new ArrayList<>();
        mHelpers.add(helper);
    }

    public void addHelper(@NonNull ActivityHelperInterface... helpers) {
        if (mHelpers == null) mHelpers = new ArrayList<>();
        mHelpers.addAll(Arrays.asList(helpers));
    }

    public void removeHelper(@NonNull ActivityHelperInterface helper) {
        if (mHelpers != null) {
            mHelpers.remove(helper);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onCreate(savedInstanceState);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onStart();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onResume();
            }
        }
    }

    protected void onPause() {
        super.onPause();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onPause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onStop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onDestroy();
            }
            mHelpers.clear();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onSaveInstanceState(outState);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onRestoreInstanceState(savedInstanceState);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onAttachedToWindow();
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onDetachedFromWindow();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onNewIntent(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = false;
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                result |= helper.onCreateOptionsMenu(menu);
            }
        }
        return super.onCreateOptionsMenu(menu) || result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                result |= helper.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item) || result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onBackPressed();
            }
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (mHelpers != null) {
            for (ActivityHelperInterface helper : mHelpers) {
                helper.onTrimMemory(level);
            }
        }
    }
}
