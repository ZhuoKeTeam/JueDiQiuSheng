package cc.zkteam.juediqiusheng.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.base.RvAdapter;
import cc.zkteam.juediqiusheng.base.RvHolder;
import cc.zkteam.juediqiusheng.base.RvListener;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.holder.SortHolder;

/**
 * Created by wustor
 * 日期  2017-10-30.
 * 邮箱  fat_chao@163.com
 */

public class SortAdapter extends RvAdapter<SortDetailBean> {
    public SortAdapter(Context context, List<SortDetailBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_sort_detail;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view,viewType,listener);
    }
}
