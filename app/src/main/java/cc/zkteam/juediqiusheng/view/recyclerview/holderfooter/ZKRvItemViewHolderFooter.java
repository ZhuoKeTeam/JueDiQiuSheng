package cc.zkteam.juediqiusheng.view.recyclerview.holderfooter;

import android.content.Context;
import android.view.View;

import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderLinearBase;

/**
 * footer
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:13 PM
 */
public class ZKRvItemViewHolderFooter extends ZKRvItemViewHolderLinearBase {

    private final static int STATE_LOAD_MORE_NONE = 0;
    private final static int STATE_LOAD_MORE_START = 1;
    private final static int STATE_LOAD_MORE_STOP = 2;
    private final static int STATE_LOAD_MORE_FAIL = 3;
    private final static int STATE_LOAD_MORE_DISABLE = 4;

    private int mState = STATE_LOAD_MORE_NONE;

    private boolean mLoadMoreEnable = false;
    private boolean mAttachWindowRetry;
    private ILoadMorer mLoadMoreView;
    private ILoadMoreListener mLisn;

    public ZKRvItemViewHolderFooter(Context context, int orientation) {

        super(context, orientation);
    }

    @Override
    public void onAdapterViewBind() {

        if (mLoadMoreEnable)
            callbackLoadMoreLisn(false);
        else
            switchLoadMoreDisableState();
    }

    @Override
    public void onAdapterViewAttachedToWindow() {

        if (mAttachWindowRetry) {

            if (mLoadMoreEnable)
                callbackLoadMoreLisn(false);
            else
                switchLoadMoreDisableState();
        }
    }

    @Override
    public void onAdapterViewDetachedFromWindow() {

        //nothing
    }

    /**
     * 覆盖父类对外回调的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        //super.onClick(v);不让回调适配器的点击事件

        if (mLoadMoreEnable && isState(STATE_LOAD_MORE_FAIL))
            callbackLoadMoreLisn(true);
    }

    public void setLoadMorer(ILoadMorer loadMorer, ILoadMoreListener lisn) {

        mLisn = lisn;

        if (loadMorer != null) {

            if (mLoadMoreView != null)
                getConvertView().removeView(loadMorer.getContentView());

            View view = loadMorer.getContentView();
            view.setOnClickListener(this);
            addView(view, 0);
            mLoadMoreView = loadMorer;
        }
    }

    public ILoadMorer getLoadMorer() {

        return mLoadMoreView;
    }

    /**
     * 仅标记状态，onAdapterViewAttachedToWindow 回调再刷新状态
     *
     * @param enable
     */
    public void setEnable(boolean enable) {

        mLoadMoreEnable = enable;
    }

    public void switchLoadMoreStartState() {

        if (mLoadMoreEnable && checkState(STATE_LOAD_MORE_START)) {

            if (mLoadMoreView != null)
                mLoadMoreView.switchLoading();

            setState(STATE_LOAD_MORE_START);
        }
    }

    public void switchLoadMoreStopState() {

        if (mLoadMoreEnable && checkState(STATE_LOAD_MORE_STOP)) {

            if (mLoadMoreView != null)
                mLoadMoreView.switchStop();

            setState(STATE_LOAD_MORE_STOP);
        }
    }

    public void switchLoadMoreFailState() {

        if (mLoadMoreEnable && checkState(STATE_LOAD_MORE_FAIL)) {

            if (mLoadMoreView != null)
                mLoadMoreView.switchFailed();

            setState(STATE_LOAD_MORE_FAIL);
        }
    }

    public void setAttachedToWindowRetryLoadMore(boolean retry) {

        mAttachWindowRetry = retry;
    }

    private void switchLoadMoreDisableState() {

        if (checkState(STATE_LOAD_MORE_DISABLE)) {

            setState(STATE_LOAD_MORE_DISABLE);
        }

        if (mLoadMoreView != null)
            mLoadMoreView.switchDisable();
    }

    private void callbackLoadMoreLisn(boolean fromM) {

        if (mLisn != null && !isState(STATE_LOAD_MORE_START)) {

            boolean success = mLisn.onLoadMore(fromM);
            if (success)
                switchLoadMoreStartState();
            else
                switchLoadMoreFailState();
        }
    }

    private void setState(int state) {

        mState = state;
    }

    private boolean checkState(int state) {

        return mState != state;
    }

    private boolean isState(int state) {

        return mState == state;
    }

    public boolean isLoadMoreEnable() {

        return mLoadMoreEnable;
    }

    public static interface ILoadMorer {

        View getContentView();

        void switchLoading();

        void switchStop();

        void switchFailed();

        void switchDisable();
    }

    public static interface ILoadMoreListener {

        boolean onLoadMore(boolean fromMan);
    }
}
