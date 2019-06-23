package cc.zkteam.juediqiusheng.view.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.view.progress.ProgressWheel;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderFooter;
import cc.zkteam.juediqiusheng.view.recyclerview.utils.TextUtil;
import cc.zkteam.juediqiusheng.view.recyclerview.utils.ViewUtil;

/**
 * 加载更多组件
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 10:49 PM
 */
public class ZKRvLoadMoreView implements ZKRvItemViewHolderFooter.ILoadMorer {

    private View mContentView;
    private TextView mTvLoadText;
    private ProgressWheel mPwLoading;
    private TextView mTvNoDataText;
    private boolean mNoDataTipMode;
    private String mFailedText;

    public ZKRvLoadMoreView(Context context) {

        mContentView = LayoutInflater.from(context).inflate(R.layout.zk_rv_footer_load_more, null);
        mTvLoadText = mContentView.findViewById(R.id.tvLoadText);
        mPwLoading = mContentView.findViewById(R.id.pwLoading);
        mPwLoading.setBarColor(Color.RED);
        mTvNoDataText = mContentView.findViewById(R.id.tvNoDataText);
        mFailedText = "加载失败, 点击重试...";
        switchDisable();
    }

    public TextView getLoadTextView() {

        return mTvLoadText;
    }

    public ProgressWheel getProgressView() {

        return mPwLoading;
    }

    public TextView getNoDataTextView() {

        return mTvNoDataText;
    }

    public void setFailedText(String text) {

        mFailedText = text;
    }

    public void setNoDataAttr(String text, int drawableLeft, int drawableTop, int drawableRight, int drawableBtm) {

        mTvNoDataText.setText(text);
        ViewUtil.setCompoundDrawable(mTvNoDataText, drawableLeft, drawableTop, drawableRight, drawableBtm);
        mNoDataTipMode = true;
    }

    @Override
    public View getContentView() {

        return mContentView;
    }

    @Override
    public void switchLoading() {

        mTvLoadText.setText("正在加载...");
        ViewUtil.showView(mPwLoading);
        ViewUtil.showView(mContentView);
        ViewUtil.hideView(mTvNoDataText);
    }

    @Override
    public void switchStop() {

        mTvLoadText.setText(TextUtil.TEXT_EMPTY);
        ViewUtil.hideView(mPwLoading);
        ViewUtil.hideView(mTvNoDataText);
        ViewUtil.showView(mContentView);
    }

    @Override
    public void switchFailed() {

        mTvLoadText.setText(mFailedText);
        ViewUtil.hideView(mPwLoading);
        ViewUtil.hideView(mTvNoDataText);
        ViewUtil.showView(mContentView);
    }

    @Override
    public void switchDisable() {

        switchStop();
        if (mNoDataTipMode) {

            ViewUtil.showView(mTvNoDataText);
            ViewUtil.showView(mContentView);
        } else {

            ViewUtil.goneView(mContentView);
        }
    }
}
