package cc.zkteam.juediqiusheng.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.cjj.MaterialRefreshLayout;

/**
 * 自定义 ZKRefreshLayout， 增加动画效果
 *  https://github.com/gdky005/Android-MaterialRefreshLayout
 * Created by WangQing on 2017/11/5.
 */

public class ZKRefreshLayout extends MaterialRefreshLayout {
    public ZKRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public ZKRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
