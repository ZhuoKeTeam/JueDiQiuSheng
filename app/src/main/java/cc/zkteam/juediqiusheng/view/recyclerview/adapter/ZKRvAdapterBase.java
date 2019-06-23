package cc.zkteam.juediqiusheng.view.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderFooter;
import cc.zkteam.juediqiusheng.view.recyclerview.holderfooter.ZKRvItemViewHolderHeader;
import cc.zkteam.juediqiusheng.view.recyclerview.listener.OnExRvItemViewClickListener;
import cc.zkteam.juediqiusheng.view.recyclerview.listener.OnExRvItemViewLongClickListener;
import cc.zkteam.juediqiusheng.view.recyclerview.listener.OnExRvItemViewSelectListener;
import cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderBase;

import static cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderConstant.ITEM_VIEW_TYPE_FOOTER;
import static cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderConstant.ITEM_VIEW_TYPE_HEADER;

/**
 * RecycleView 适配器 基类
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:11 PM
 */
public abstract class ZKRvAdapterBase<T, K extends ZKRvItemViewHolderBase> extends RecyclerView.Adapter<ZKRvItemViewHolderBase> {

    private ZKRvItemViewHolderHeader mHeader;
    private ZKRvItemViewHolderFooter mFooter;

    private List<T> mData;
    private int mOrientation = OrientationHelper.VERTICAL;

    private OnExRvItemViewClickListener mClickLisn;
    private OnExRvItemViewLongClickListener mLongClickLisn;
    private OnExRvItemViewSelectListener mSelectLisn;

    protected ZKRvAdapterBase() {
    }

    protected ZKRvAdapterBase(List<T> data) {

        setData(data);
    }


    //************************ 覆写的父类函数 **************************


    /**
     * 返回所有item个数
     * 包含 header,footer,data
     * 如果没有 header,footer 就只是data的size
     *
     * @return
     */
    @Override
    public final int getItemCount() {

        int count = 0;

        if (hasHeader())
            count++;

        if (hasFooter())
            count++;

        return count + getDataItemCount();
    }

    /**
     * 返回 item 的id
     * 默认实现返回 position
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * 如果是header 返回 ITEM_VIEW_TYPE_HEADER
     * 如果是footer 返回 ITEM_VIEW_TYPE_FOOTER
     * 如果是data item 返回 0
     *
     * @param position
     * @return
     */
    @Override
    public final int getItemViewType(int position) {

        if (position == 0) {

            if (hasHeader())
                return ITEM_VIEW_TYPE_HEADER;

            if (hasData())
                return getDataItemViewType(getDataPosByAdapterPos(position));

            if (hasFooter())
                return ITEM_VIEW_TYPE_FOOTER;

        } else if (position == getItemCount() - 1) {

            if (hasFooter())
                return ITEM_VIEW_TYPE_FOOTER;
        }

        return getDataItemViewType(getDataPosByAdapterPos(position));
    }

    @Override
    public final ZKRvItemViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                return mHeader;
            case ITEM_VIEW_TYPE_FOOTER:
                return mFooter;
            default:
                K vh = onCreateDataViewHolder(parent, viewType);
                vh.onAdapterSetEventListener(mClickLisn, mLongClickLisn, mSelectLisn);
                vh.onAdapterInitConvertView();
                return vh;
        }
    }

    /**
     * 如果有header position 偏移量要减 1, 才是实际data的position
     *
     * @param holder
     * @param position
     */
    @Override
    public final void onBindViewHolder(ZKRvItemViewHolderBase holder, int position) {

        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE_HEADER:
            case ITEM_VIEW_TYPE_FOOTER:
                holder.onAdapterSetDataPosition(position);
                holder.onAdapterViewBind();
                break;
            default:
                int dataPos = getDataPosByAdapterPos(position);
                holder.onAdapterSetDataPosition(dataPos);
                onBindDataViewHolder((K) holder, dataPos);
                break;
        }
    }

    @Override
    public void onBindViewHolder(ZKRvItemViewHolderBase holder, int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);

        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE_HEADER:
            case ITEM_VIEW_TYPE_FOOTER:
                break;
            default:
                int dataPos = getDataPosByAdapterPos(position);
                holder.onAdapterSetDataPosition(dataPos);
                onBindDataViewHolder((K) holder, getDataPosByAdapterPos(position), payloads);
                break;
        }
    }

    @Override
    public void onViewAttachedToWindow(ZKRvItemViewHolderBase holder) {
        if (holder != null) {
            holder.onAdapterViewAttachedToWindow();
        }
    }

    @Override
    public void onViewDetachedFromWindow(ZKRvItemViewHolderBase holder) {
        if (holder != null) {
            holder.onAdapterViewDetachedFromWindow();
        }
    }

    @Override
    public void onViewRecycled(ZKRvItemViewHolderBase holder) {
        if (holder != null) {
            holder.onAdapterViewRecycled();
        }
    }

    //***************************** 供子类覆写的函数 ****************************


    /**
     * 获取data item view type
     *
     * @param dataPos
     * @return
     */
    public int getDataItemViewType(int dataPos) {

        return 0;
    }

    public abstract K onCreateDataViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindDataViewHolder(K holder, int dataPos);

    public void onBindDataViewHolder(K holder, int dataPos, List<Object> payLoads) {
        //nothing
    }

    //*************************** 与data集合操作相关的函数 **************************


    public List<T> getData() {

        return mData;
    }

    /**
     * 返回data的个数
     *
     * @return
     */
    public int getDataItemCount() {

        return mData == null ? 0 : mData.size();
    }

    public T getDataItem(int dataPos) {

        return checkDataPosition(dataPos) ? mData.get(dataPos) : null;
    }

    public void setData(List<T> data) {

        clearData();
        addDataAll(data);
    }

    public void addData(T item) {

        if (item != null) {

            initDataIfNull();
            mData.add(item);
        }
    }

    public void addData(int dataPos, T item) {

        if (item != null) {

            initDataIfNull();
            if (checkDataInsertPosition(dataPos))
                mData.add(dataPos, item);
        }
    }

    public void addDataAll(List<? extends T> data) {

        if (data != null && data.size() > 0) {

            initDataIfNull();
            mData.addAll(data);
        }
    }

    public void addDataAll(int dataPos, List<? extends T> data) {

        if (data != null && data.size() > 0 &&
                checkDataInsertPosition(dataPos)) {

            initDataIfNull();
            mData.addAll(dataPos, data);
        }
    }

    public boolean removeData(T item) {

        if (hasData() && item != null)
            return mData.remove(item);
        else
            return false;
    }

    public T removeData(int dataPos) {

        if (hasData() && checkDataPosition(dataPos))
            return mData.remove(dataPos);
        else
            return null;
    }

    /**
     * 清空数据
     */
    public void clearData() {

        if (mData != null)
            mData.clear();
    }

    /**
     * 获取最后一item的position
     *
     * @return 如果集合为空返回－1
     */
    public int getDataLastItemPosition() {

        return getDataItemCount() - 1;
    }

    /**
     * 获取集合中指定type的第一个position
     *
     * @param type
     * @return
     */
    public int findDataPosByDataItemViewType(int type) {

        if (isDataEmpty())
            return -1;

        for (int i = 0; i < mData.size(); i++) {

            if (getDataItemViewType(i) == type)
                return i;
        }

        return -1;
    }

    /**
     * 获取最后一种itemType的数据对象
     *
     * @param type
     * @return
     */
    public T findDataByLastDataItemViewType(int type) {

        if (isDataEmpty())
            return null;

        int size = getDataItemCount();
        for (int i = size - 1; i >= 0; i--) {

            if (getDataItemViewType(i) == type)
                return getDataItem(i);
        }

        return null;
    }

    /**
     * 获取最后一个元素的类型
     *
     * @return
     */
    public int findDataLastItemViewType() {

        if (isDataEmpty())
            return -1;
        else
            return getDataItemViewType(getDataItemCount() - 1);
    }

    /**
     * 检查pos是否越界
     *
     * @param position
     * @return
     */
    public boolean checkDataPosition(int position) {

        return position >= 0 && position < getDataItemCount();
    }

    /**
     * 检查插入操作pos是否越界
     *
     * @param position
     * @return
     */
    public boolean checkDataInsertPosition(int position) {

        return position >= 0 && position <= getDataItemCount();
    }

    /**
     * 判断是否有数据
     *
     * @return
     */
    public boolean hasData() {

        return getDataItemCount() > 0;
    }

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean isDataEmpty() {

        return getDataItemCount() <= 0;
    }

    /**
     * 将adapterPosition 转换为 data对应的position
     * 因为在有header的情况下,将adapterPosition将不再是data对应的position
     *
     * @param adapterPosition adapter的position
     * @return
     */
    public int getDataPosByAdapterPos(int adapterPosition) {

        return hasHeader() ? adapterPosition - 1 : adapterPosition;//校正有header时的pos值
    }

    /**
     * 将dataPosition 转换为 adapter对应的position
     *
     * @param dataPosition
     * @return
     */
    public int getAdapterPosByDataPos(int dataPosition) {

        return hasHeader() ? dataPosition + 1 : dataPosition;
    }

    private void initDataIfNull() {

        if (mData == null)
            mData = new ArrayList<T>();
    }


    //***************************** header api ****************************


    public void addHeaderViewFirst(View headerView) {

        addHeaderView(headerView, 0);
    }

    public void addHeaderView(View headerView) {

        addHeaderView(headerView, getHeaderChildCount());
    }

    public void setHeaderPaddingTop(int paddingTop) {

        if (hasHeader())
            mHeader.setPaddingTop(paddingTop);
    }

    public void setHeaderPaddingBottom(int paddingBottom) {

        if (hasHeader())
            mHeader.setPaddingBottom(paddingBottom);
    }

    public ZKRvItemViewHolderHeader getHeader() {

        return mHeader;
    }

    public int getHeaderChildCount() {

        return mHeader == null ? 0 : mHeader.getChildCount();
    }

    private void addHeaderView(View headerView, int index) {

        if (headerView != null) {

            boolean result = initHeaderIfNull(headerView.getContext(), mOrientation);
            mHeader.addView(headerView, index);
            if (result)
                notifyDataSetChanged();//以后再研究局部刷新;
        }
    }


    //***************************** footer api ****************************


    public void addFooterViewFirst(View footerView) {

        addFooterView(footerView, 0);
    }

    public void addFooterView(View footerView) {

        addFooterView(footerView, getFooterChildCount());
    }

    public void setFooterPaddingTop(int paddingTop) {

        if (hasFooter())
            mFooter.setPaddingTop(paddingTop);
    }

    public void setFooterPaddingBottom(int paddingBottom) {

        if (hasFooter())
            mFooter.setPaddingBottom(paddingBottom);
    }

    public ZKRvItemViewHolderFooter getFooter() {

        return mFooter;
    }

    public int getFooterChildCount() {

        return mFooter == null ? 0 : mFooter.getChildCount();
    }

    private void addFooterView(View footerItemView, int index) {

        if (footerItemView != null) {

            boolean result = initFooterIfNull(footerItemView.getContext(), mOrientation);
            mFooter.addView(footerItemView, index);
            if (result)
                notifyDataSetChanged();//以后再研究局部刷新;
        }
    }


    //***************************** load more api ****************************


    public void setLoadMorer(ZKRvItemViewHolderFooter.ILoadMorer loadMorer, ZKRvItemViewHolderFooter.ILoadMoreListener lisn) {

        if (loadMorer != null) {

            initFooterIfNull(loadMorer.getContentView().getContext(), mOrientation);
            mFooter.setLoadMorer(loadMorer, lisn);
        }
    }

    public ZKRvItemViewHolderFooter.ILoadMorer getLoadMorer() {

        return mFooter == null ? null : mFooter.getLoadMorer();
    }

    public void startLoadMore() {

        if (mFooter != null)
            mFooter.switchLoadMoreStartState();
    }

    public void stopLoadMore() {

        if (mFooter != null)
            mFooter.switchLoadMoreStopState();
    }

    public void stopLoadMoreFail() {

        if (mFooter != null)
            mFooter.switchLoadMoreFailState();
    }

    public void setLoadMoreEnable(boolean enable) {

        if (mFooter != null)
            mFooter.setEnable(enable);
    }

    public void setLoadMoreAttachWindowRetry(boolean retry) {

        if (mFooter != null)
            mFooter.setAttachedToWindowRetryLoadMore(retry);
    }

    public boolean isLoadMoreEnable() {

        if (mFooter == null)
            return false;
        else
            return mFooter.isLoadMoreEnable();
    }

    public boolean hasHeader() {

        return mHeader != null;
    }

    public boolean hasFooter() {

        return mFooter != null;
    }

    private boolean initHeaderIfNull(Context context, int orientation) {

        if (mHeader == null) {

            mHeader = new ZKRvItemViewHolderHeader(context, orientation);
            mHeader.onAdapterInitConvertView();
            return true;
        } else {

            return false;
        }
    }

    private boolean initFooterIfNull(Context context, int orientation) {

        if (mFooter == null) {

            mFooter = new ZKRvItemViewHolderFooter(context, orientation);
            mFooter.onAdapterInitConvertView();
            return true;
        } else {

            return false;
        }
    }


    //***************************** 监听器相关函数 ****************************


    /**
     * 设置点击监听器
     *
     * @param lisn
     */
    public void setOnExRvItemViewClickListener(OnExRvItemViewClickListener lisn) {

        mClickLisn = lisn;
    }

    /**
     * 设置长按监听器
     *
     * @param lisn
     */
    public void setOnExRvItemViewLongClickListener(OnExRvItemViewLongClickListener lisn) {

        mLongClickLisn = lisn;
    }

    /**
     * 设置选择监听器
     *
     * @param lisn
     */
    public void setOnExRvItemViewSelectListener(OnExRvItemViewSelectListener lisn) {

        mSelectLisn = lisn;
    }


    //**************************   其他   ****************************


    public void setOrientation(int orientation) {

        mOrientation = orientation;
    }

    private String simpleTag() {

        return getClass().getSimpleName();
    }

    public void setHeaderIsRecyclable(boolean recyclable) {
        if (mHeader != null) {
            mHeader.setIsRecyclable(recyclable);
        }
    }

    public void setFooterIsRecyclable(boolean recyclable) {
        if (mFooter != null) {
            mFooter.setIsRecyclable(recyclable);
        }
    }
}
