package cc.zkteam.juediqiusheng.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * 自定义 ZKRefreshLayout， 增加动画效果
 *
 * Created by WangQing on 2017/11/5.
 */

public class ZKRefreshLayout extends WaveSwipeRefreshLayout {
    public ZKRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public ZKRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
