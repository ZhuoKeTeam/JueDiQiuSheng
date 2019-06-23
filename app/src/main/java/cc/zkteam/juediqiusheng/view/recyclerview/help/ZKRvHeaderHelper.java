package cc.zkteam.juediqiusheng.view.recyclerview.help;

import android.support.v7.widget.RecyclerView;

import cc.zkteam.juediqiusheng.view.recyclerview.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.recyclerview.attacher.ZKRvOnChildAttacher;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderHeader;

/**
 * ExRecyclerView header辅助类
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:10 PM
 */
public class ZKRvHeaderHelper {

    private boolean mHeaderAttached;
    private ZKRvItemViewHolderHeader mHeaderViewHolder;
    private ExRvHeaderListener mLisn;
    private int mHeaderBtm;

    public ZKRvHeaderHelper(ZKRecyclerView erv) {

        erv.addOnChildAttachStateChangeListener(getExRvOnChildAttacher(erv));
        erv.addOnScrollListener(getScrollListener());
    }

    public void setListener(ExRvHeaderListener lisn) {

        mLisn = lisn;
    }

    public boolean isAttached() {

        return mHeaderAttached;
    }

    private ZKRvOnChildAttacher getExRvOnChildAttacher(ZKRecyclerView rv) {

        return new ZKRvOnChildAttacher(rv) {
            @Override
            public void onExRvHeaderChildViewAttachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderHeader header) {

                mHeaderAttached = true;
                mHeaderViewHolder = header;

                if (mLisn != null)
                    mLisn.onExRvHeaderAttachChanged(true);
            }

            @Override
            public void onExRvHeaderChildViewDetachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderHeader header) {

                mHeaderAttached = false;
                mHeaderBtm = Integer.MIN_VALUE;
                if (mLisn != null)
                    mLisn.onExRvHeaderAttachChanged(false);
            }
        };
    }

    /**
     * item高度发生变化，也会回调onScroll
     *
     * @return
     */
    private RecyclerView.OnScrollListener getScrollListener() {

        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (mHeaderAttached) {

                    mHeaderBtm = mHeaderViewHolder.getConvertView().getBottom();
                    if (mLisn != null)
                        mLisn.onExRvHeaderShowHeight(mHeaderBtm);
                } else {

                    if (mHeaderBtm == Integer.MIN_VALUE) {

                        if (mLisn != null)
                            mLisn.onExRvHeaderShowHeight(0);
                        mHeaderBtm = 0;
                    }
                }
            }
        };
    }

    public static interface ExRvHeaderListener {

        void onExRvHeaderAttachChanged(boolean attached);

        void onExRvHeaderShowHeight(int showHeight);
    }
}
