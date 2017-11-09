package cc.zkteam.juediqiusheng;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * JDQSApplication
 * Created by WangQing on 2017/10/23.
 */

public class JDQSApplication extends Application {
    Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        ZKBase.init(this);
    }
}
