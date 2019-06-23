package cc.zkteam.juediqiusheng.view.recyclerview.holderfooter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import cc.zkteam.juediqiusheng.view.recyclerview.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.recyclerview.listener.IStaggerFullSpan;
import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderBase;
import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderUtil;

/**
 * StaggedGridLayoutManager header footer full span 处理
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:09 PM
 */
public class ZKRvHfStaggeredAttacher implements RecyclerView.OnChildAttachStateChangeListener {

    private ZKRecyclerView mRv;

    public ZKRvHfStaggeredAttacher(ZKRecyclerView rv) {

        mRv = rv;
    }

    @Override
    public void onChildViewAttachedToWindow(View view) {

        ZKRvItemViewHolderBase holder = mRv.getChildViewHolder(view);
        if (ZKRvItemViewHolderUtil.isHeaderOrFooter(holder) || holder instanceof IStaggerFullSpan) {

            StaggeredGridLayoutManager.LayoutParams sglm = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            sglm.setFullSpan(true);
        }
    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {

    }
}
