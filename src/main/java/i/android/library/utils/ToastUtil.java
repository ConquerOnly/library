package i.android.library.utils;

/**
 * Created by root on 1/28/18.
 */

import android.content.Context;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;

public class ToastUtil {

    public static Context sContext;
    private static Toast toast;

    public static void register(Context context) {
        sContext = context.getApplicationContext();
    }


    private static void check() {
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtil.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }


    public static void showShort(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }


    public static void showLongX2(String message) {
        showLong(message);
        showLong(message);
    }


    public static void showLongX2(int resId) {
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(int resId) {
        showLong(resId);
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(String message) {
        showLong(message);
        showLong(message);
        showLong(message);
    }

    public static void showSingle(String message) {
        check();
        if(toast!=null)toast.cancel();
        toast.makeText(sContext,message,Toast.LENGTH_SHORT).show();
    }

    public static void showBmob(BmobException e) {
        showSingle(e.getMessage());
    }

    public static void showE(String s, BmobException e) {
        showSingle(s+e.getMessage());
    }
}