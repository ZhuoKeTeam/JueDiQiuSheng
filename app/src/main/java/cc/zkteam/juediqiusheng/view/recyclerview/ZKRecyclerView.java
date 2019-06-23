package cc.zkteam.juediqiusheng.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cc.zkteam.juediqiusheng.view.recyclerview.adapter.ZKRvAdapterBase;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvHfStaggeredAttacher;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderFooter;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderHeader;
import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderBase;

import static cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderConstant.ITEM_VIEW_TYPE_FOOTER;
import static cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderConstant.ITEM_VIEW_TYPE_HEADER;


/**
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:07 PM
 */
public class ZKRecyclerView extends RecyclerView {

    private ZKRvItemViewHolderFooter.ILoadMorer mTempLoadMorer;
    private ZKRvItemViewHolderFooter.ILoadMoreListener mTempLoadMoreLisn;
    private ZKGridSpanSizeLookUp mGridSpanSizeLookUp;

    /**
     * 级联滑动时，是否需要拦截Touch事件
     */
    private boolean mInterceptTouchEventIfNeeded = false;

    public ZKRecyclerView(Context context) {

        super(context);
    }

    public ZKRecyclerView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public ZKRecyclerView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {

        super.setLayoutManager(layout);
        setHeaderFooterSpan(layout);
    }

    private void setHeaderFooterSpan(final LayoutManager layout){

        if(layout instanceof StaggeredGridLayoutManager){

            addOnChildAttachStateChangeListener(new ZKRvHfStaggeredAttacher(this));

        }else if(layout instanceof GridLayoutManager){

            ((GridLayoutManager) layout).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {

                    if (ITEM_VIEW_TYPE_HEADER == getItemViewType(position) || ITEM_VIEW_TYPE_FOOTER == getItemViewType(position))
                        return ((GridLayoutManager) layout).getSpanCount();
                    else
                        return mGridSpanSizeLookUp == null ? 1 : mGridSpanSizeLookUp.getSpanCount(getDataPosByAdapterPos(position));
                }
            });

        }
    }

    @Override
    public void setAdapter(Adapter adapter) {

        setAdapter((ZKRvAdapterBase) adapter);
    }

    public void setAdapter(ZKRvAdapterBase adapter) {

        super.setAdapter(adapter);

        if (adapter != null) {

            if (mTempLoadMorer != null)
                adapter.setLoadMorer(mTempLoadMorer, mTempLoadMoreLisn);
        }

        mTempLoadMorer = null;
        mTempLoadMoreLisn = null;
    }

    @Override
    public ZKRvAdapterBase<?, ?> getAdapter() {

        return (ZKRvAdapterBase<?, ?>) super.getAdapter();
    }

    @Override
    public ZKRvItemViewHolderBase getChildViewHolder(View child) {

        return (ZKRvItemViewHolderBase) super.getChildViewHolder(child);
    }

    /**
     * 级联滑动时，是否需要拦截Touch事件
     * @param interceptTouchEvent
     */
    public void setInterceptTouchEventIfNeeded(boolean interceptTouchEvent) {
        mInterceptTouchEventIfNeeded = interceptTouchEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        // 级联滑动时，是否需要拦截Touch事件，不再分发，分发会导致 Tap事件到子View上，进而出现不必要的界面跳转
        if (mInterceptTouchEventIfNeeded) {

            mInterceptTouchEventIfNeeded = false;
            return true;
        }

        return super.onInterceptTouchEvent(e);
    }

    //**************************** 新增api，适配器item相关 *******************************


    public void setGridSpanSizeLookUp(ZKGridSpanSizeLookUp mGridSpanSizeLookUp) {

        this.mGridSpanSizeLookUp = mGridSpanSizeLookUp;
    }

    public int getItemCount() {

        return super.getAdapter().getItemCount();
    }

    public int getItemViewType(int position) {

        return super.getAdapter().getItemViewType(position);
    }

    public int getDataItemCount() {

        return getAdapter() == null ? 0 : getAdapter().getDataItemCount();
    }

    public Object getDataItem(int dataPosition) {

        return getAdapter() == null ? null : getAdapter().getDataItem(dataPosition);
    }

    public int getDataPosByAdapterPos(int adapterPos) {

        return getAdapter().getDataPosByAdapterPos(adapterPos);
    }


    //***************************** header api ****************************


    public void addHeaderViewFirst(View headerView) {

        if (getAdapter() != null)
            getAdapter().addHeaderViewFirst(headerView);
    }

    public void addHeaderView(View headerView) {

        if (getAdapter() != null)
            getAdapter().addHeaderViewFirst(headerView);
    }

    public void setHeaderPaddingTop(int paddingTop) {

        if (getAdapter() != null)
            getAdapter().setHeaderPaddingTop(paddingTop);
    }

    public void setHeaderPaddingBottom(int paddingBottom) {

        if (getAdapter() != null)
            getAdapter().setHeaderPaddingBottom(paddingBottom);
    }

    public boolean hasHeader(){

        if(getAdapter() == null)
            return false;
        else
            return getAdapter().hasHeader();
    }

    public int getHeaderChildCount() {

        if (getAdapter() == null)
            return 0;
        else
            return getAdapter().getHeaderChildCount();
    }

    public ZKRvItemViewHolderHeader getHeader(){

        if(getAdapter() == null)
            return null;
        else
            return getAdapter().getHeader();
    }


    //***************************** footer api ****************************


    public boolean hasFooter(){

        if(getAdapter() == null)
            return false;
        else
            return getAdapter().hasFooter();
    }

    public ZKRvItemViewHolderFooter getFooter(){

        if(getAdapter() == null)
            return null;
        else
            return getAdapter().getFooter();
    }

    public void addFooterViewFirst(View footerView) {

        if (getAdapter() != null)
            getAdapter().addFooterViewFirst(footerView);
    }

    public void addFooterView(View footerView) {

        if (getAdapter() != null)
            getAdapter().addFooterView(footerView);
    }

    public void setFooterPaddingTop(int paddingTop) {

        if (getAdapter() != null)
            getAdapter().setFooterPaddingTop(paddingTop);
    }

    public void setFooterPaddingBottom(int paddingBottom) {

        if (getAdapter() != null)
            getAdapter().setFooterPaddingBottom(paddingBottom);
    }

    public int getFooterChildCount() {

        if (getAdapter() == null)
            return 0;
        else
            return getAdapter().getFooterChildCount();
    }

    public void setLoadMorer(ZKRvItemViewHolderFooter.ILoadMorer loadMorer,
                             ZKRvItemViewHolderFooter.ILoadMoreListener lisn) {

        if (getAdapter() == null) {

            mTempLoadMorer = loadMorer;
            mTempLoadMoreLisn = lisn;
        } else {

            getAdapter().setLoadMorer(loadMorer, lisn);
        }
    }

    public ZKRvItemViewHolderFooter.ILoadMorer getLoadMorer(){

        if(mTempLoadMorer != null)
            return mTempLoadMorer;
        else
            return getAdapter() == null ? null : getAdapter().getLoadMorer();
    }

    public void stopLoadMore(){

        if(getAdapter() != null)
            getAdapter().stopLoadMore();
    }

    public void stopLoadMoreFail(){

        if(getAdapter() != null)
            getAdapter().stopLoadMoreFail();
    }

    public void setLoadMoreEnable(boolean enable) {

        if(getAdapter() != null)
            getAdapter().setLoadMoreEnable(enable);
    }

    public boolean isLoadMoreEnable(){

        if(getAdapter() == null)
            return false;
        else
            return getAdapter().isLoadMoreEnable();
    }
}
