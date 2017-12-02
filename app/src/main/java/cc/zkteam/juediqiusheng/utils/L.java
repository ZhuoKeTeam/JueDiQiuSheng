package cc.zkteam.juediqiusheng.utils;

import com.orhanobut.logger.Logger;

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
            Logger.i(TAG, msg);
        }
    }

    public static void d(Object... msg) {
        if (isDebug) {
            Logger.d(TAG, msg);
        }
    }

    public static void w(Object... msg) {
        if (isDebug) {
            Logger.w(TAG, msg);
        }
    }

    public static void e(Object... msg) {
        if (isDebug) {
            Logger.e(TAG, msg);
        }
    }

    public static void m(Object... msg) {
        if (isDebug) {
            Logger.d(TAG, msg);
        }
    }


}
