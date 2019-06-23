package cc.zkteam.juediqiusheng.view.recyclerview.attacher;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cc.zkteam.juediqiusheng.view.recyclerview.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderFooter;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderHeader;
import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderBase;
import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderUtil;

public class ZKRvOnChildAttacher implements RecyclerView.OnChildAttachStateChangeListener {

    private ZKRecyclerView mErv;

    public ZKRvOnChildAttacher(ZKRecyclerView erv) {

        mErv = erv;
    }

    protected ZKRecyclerView getRecyclerView() {

        return mErv;
    }

    @Override
    public void onChildViewAttachedToWindow(View view) {

        ZKRvItemViewHolderBase holder = mErv.getChildViewHolder(view);
        if (ZKRvItemViewHolderUtil.isHeader(holder))
            onExRvHeaderChildViewAttachedToWindow(mErv, (ZKRvItemViewHolderHeader) holder);
        else if (ZKRvItemViewHolderUtil.isFooter(holder))
            onExRvFooterChildViewAttachedToWindow(mErv, (ZKRvItemViewHolderFooter) holder);
        else
            onExRvDataChildViewAttachedToWindow(mErv, holder);
    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {

        ZKRvItemViewHolderBase holder = mErv.getChildViewHolder(view);
        if (ZKRvItemViewHolderUtil.isHeader(holder))
            onExRvHeaderChildViewDetachedToWindow(mErv, (ZKRvItemViewHolderHeader) holder);
        else if (ZKRvItemViewHolderUtil.isFooter(holder))
            onExRvFooterChildViewDetachedToWindow(mErv, (ZKRvItemViewHolderFooter) holder);
        else
            onExRvDataChildViewDetachedToWindow(mErv, holder);
    }


    public void onExRvHeaderChildViewAttachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderHeader header) {

    }

    public void onExRvHeaderChildViewDetachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderHeader header) {

    }


    public void onExRvDataChildViewAttachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderBase viewHolder) {

    }

    public void onExRvDataChildViewDetachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderBase viewHolder) {

    }


    public void onExRvFooterChildViewAttachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderFooter footer) {

    }

    public void onExRvFooterChildViewDetachedToWindow(ZKRecyclerView erv, ZKRvItemViewHolderFooter footer) {

    }

}
