package cc.zkteam.juediqiusheng.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.Utils;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.HashMap;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.view.ZKImageView;

/**
 * HotNewsAdapter
 * Created by Doraemon123 on 2017/12/17.
 */
public class HotNewsItemAdapter
        extends RecyclerView.Adapter<HotNewsItemAdapter.ViewHolder> {

    //    List<SortDetailBean> list;
    public List<Object> list;
    final int TYPE_DATA = 0;
    final int TYPE_AD = 1;
//    public List<RecommendedBean> list;

    private HotNewsAdapter.onItemClickListener listener;

    public HotNewsItemAdapter(List<Object> list) {
        this.list = list;
    }

    public void setListener(HotNewsAdapter.onItemClickListener listener) {
        this.listener = listener;
    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < list.size() && adView != null) {
            list.add(position, adView);
        }
    }

    HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    // 把返回的NativeExpressADView添加到数据集里面去
    public void setADViewPositionMap(HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        this.mAdViewPositionMap = mAdViewPositionMap;
    }


    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(int position, NativeExpressADView adView) {
        list.remove(position);
        notifyItemRemoved(position); // position为adView在当前列表中的位置
        notifyItemRangeChanged(0, list.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
////                .inflate(R.layout.item_hot_news_item, parent, false);
////        return new ViewHolder(view);
        int layoutId = (viewType == TYPE_AD) ? R.layout.item_express_ad : R.layout.item_hot_news_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (obj == null) {
            return;
        }
        if (obj instanceof NativeExpressADView) {
            final NativeExpressADView adView = (NativeExpressADView) list.get(position);
            if (mAdViewPositionMap != null) {
                mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            }
            if (holder.container.getChildCount() > 0
                    && holder.container.getChildAt(0) == adView) {
                return;
            }

            if (holder.container.getChildCount() > 0) {
                holder.container.removeAllViews();
            }

            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }

            holder.container.addView(adView);
            adView.render(); // 调用render方法后sdk才会开始展示广告
        } else if (obj instanceof SortDetailBean) {
//            SortDetailBean sortDetailBean = list.get(position);
//            holder.tvArticalName.setText(sortDetailBean.getArtifactName());
//            holder.imageView.setImageURI(sortDetailBean.getPicUrl());
//            holder.tvOrigin.setText("来源：XX");
//            holder.tvTime.setText("2017/12/17");
//            if (listener != null) {
//                holder.setOnClickListener(listener, sortDetailBean);
//            }
        } else if (obj instanceof RecommendedBean) {
            RecommendedBean recommendedBean = (RecommendedBean) list.get(position);
            holder.tvArticalName.setText(recommendedBean.getContext());
            if (!TextUtils.isEmpty(recommendedBean.getPicUrl())) {
                holder.imageView.setImageURI(recommendedBean.getPicUrl());
            } else {
                holder.imageView.setImageResource(R.drawable.ic_default);
            }
            holder.tvOrigin.setText(Utils.getApp().getResources().getString(R.string.source_text));
            holder.tvTime.setText(TimeUtils.millis2String(recommendedBean.getCollectionDate() * 1000));
            if (listener != null) {
                holder.setOnClickListener(listener, recommendedBean);
            }

        } else if (obj instanceof String) {
//            holder.textView.setText((CharSequence) obj);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) instanceof NativeExpressADView ? TYPE_AD : TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ZKImageView imageView;
        public final TextView tvArticalName;
        public final TextView tvOrigin;
        public final TextView tvTime;


        public View contentView;
        private RecommendedBean data;
        public ViewGroup container;

        public ViewHolder(View view) {
            super(view);
            this.contentView = view;
            imageView = view.findViewById(R.id.pic);
            tvArticalName = view.findViewById(R.id.tv_title);
            tvOrigin = view.findViewById(R.id.tv_origin);
            tvTime = view.findViewById(R.id.tv_time);
            container = view.findViewById(R.id.express_ad_container);
        }

        public void setOnClickListener(final HotNewsAdapter.onItemClickListener listener, RecommendedBean data) {
            this.data = data;
            contentView.setOnClickListener(view -> listener.onItemClicked(ViewHolder.this.data));
        }
    }

}
