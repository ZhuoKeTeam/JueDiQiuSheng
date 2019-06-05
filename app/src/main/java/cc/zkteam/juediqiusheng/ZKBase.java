package cc.zkteam.juediqiusheng;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import cc.zkteam.juediqiusheng.exception.ZKBaseNullPointerException;
public final class ZKBase {
  @SuppressLint("StaticFieldLeak")
  private static Context context;
  public static boolean isDebug = BuildConfig.DEBUG;
  private ZKBase() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }
  public static void init(@NonNull Application app) {
    if (isDebug()) {
      ARouter.openLog();
      ARouter.openDebug();
    }
    ARouter.init(app);
    Fresco.initialize(app.getApplicationContext());
    ZKBase.context = app.getApplicationContext();
  }
  public static void init(@NonNull Context context, boolean debug) {
    ZKBase.context = context.getApplicationContext();
    ZKBase.isDebug = debug;
  }
  @CheckResult
  public static Context getContext() {
    if (context != null) {
      return context;
    }
    throw new ZKBaseNullPointerException("context is null!");
  }
  @CheckResult
  public static boolean isDebug() {
    return isDebug;
  }
}
