package cc.zkteam.juediqiusheng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import cc.zkteam.juediqiusheng.exception.ZKBaseNullPointerException;

/**
 * JMBase 初始化相关
 * Created by WangQing on 2017/5/12.
 */
public final class ZKBase {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static boolean isDebug = false;

    private ZKBase() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context Context
     */
    public static void init(@NonNull Context context) {
        ZKBase.context = context.getApplicationContext();
    }

    public static void init(@NonNull Context context, boolean debug) {
        ZKBase.context = context.getApplicationContext();
        ZKBase.isDebug = debug;
    }

    @CheckResult
    public static Context getContext() {
        if (context != null)
            return context;
        throw new ZKBaseNullPointerException("context is null!");
    }

    /**
     * 当前是否是  debug 的状态
     * @return boolean
     */
    @CheckResult
    public static boolean isDebug() {
        return isDebug;
    }
}
