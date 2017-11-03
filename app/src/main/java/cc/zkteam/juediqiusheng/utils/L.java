package cc.zkteam.juediqiusheng.utils;

import android.util.Log;

import java.util.Arrays;

import cc.zkteam.juediqiusheng.ZKBase;

/**
 * Log 管理类
 * Created by WangQing on 2016/11/27.
 */
public class L {

    static {
        isDebug = ZKBase.isDebug;
    }

    private static boolean isDebug = ZKBase.isDebug();
    private static final String TAG = "JDQS_TAG";

    public static void i(Object... msg) {
        if (isDebug) {
            String msgInfo = Arrays.toString(msg);
            Log.i(TAG, msgInfo);
        }

    }

    public static void d(Object... msg) {
        if (isDebug) {
            String msgInfo = Arrays.toString(msg);
            Log.d(TAG, msgInfo);
        }

    }

    public static void w(Object... msg) {
        if (isDebug) {
            String msgInfo = Arrays.toString(msg);
            Log.w(TAG, msgInfo);
        }

    }

    public static void e(Object... msg) {
        if (isDebug) {
            String msgInfo = Arrays.toString(msg);
            Log.e(TAG, msgInfo);
        }

    }

    public static void m(Object... msg) {
        if (isDebug) {
            String msgInfo = Arrays.toString(msg);
            Log.d(TAG, msgInfo);
        }

    }


}
