package cc.zkteam.juediqiusheng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qq.e.ads.nativ.NativeExpressADView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.utils.ZKImageViewExtKt;
import cc.zkteam.juediqiusheng.view.ZKImageView;

/**
 * HotNewsAdapter
 * Created by Doraemon123 on 2017/12/17.
 */
public class QuestionAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Object> list = new ArrayList<>();
    final int TYPE_DATA = 0;
    final int TYPE_AD = 1;

    private onItemClickListener listener;

    public QuestionAdapter() {
    }

    public void setNewData(List list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public void addNewData(List list) {
        this.list.addAll(list);
    }

    public void setListener(onItemClickListener listener) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
////                .inflate(R.layout.item_hot_news_item, parent, false);
////        return new NormalViewHolder(view);
        if (viewType == TYPE_AD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_express_ad, parent, false);
            return new AdViewHolder(view);
        } else if (viewType == TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_detail, parent, false);
            return new NormalViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (obj == null) {
            return;
        }
        if (obj instanceof NativeExpressADView && getItemViewType(position) == TYPE_AD) {
            final NativeExpressADView adView = (NativeExpressADView) list.get(position);
            if (mAdViewPositionMap != null) {
                mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            }
            AdViewHolder adHolder = (AdViewHolder) holder;
            if (adHolder.container.getChildCount() > 0
                    && adHolder.container.getChildAt(0) == adView) {
                return;
            }

            if (adHolder.container.getChildCount() > 0) {
                adHolder.container.removeAllViews();
            }

            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }

            adHolder.container.addView(adView);
            adView.render(); // 调用render方法后sdk才会开始展示广告
        } else if (obj instanceof SortDetailBean && getItemViewType(position) == TYPE_DATA) {
            final SortDetailBean item = (SortDetailBean) list.get(position);
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;

            Date date = TimestampToDate(item.getArtifactDate());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");

            normalViewHolder.tv_content.setText(item.getArtifactName() + "");
            normalViewHolder.tv_time.setText(sf.format(date) + "");

            ZKImageViewExtKt.loadUrl(normalViewHolder.iv_head, item.getPicUrl(), R.mipmap.iv_head);

            if (listener != null) {
                ((NormalViewHolder) holder).setOnItemClickListener(listener, item);
            }
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

    public static class NormalViewHolder extends RecyclerView.ViewHolder {

        public final ZKImageView iv_head;
        public final TextView tv_content;
        public final TextView tv_time;
        public View contentView;

        public NormalViewHolder(View view) {
            super(view);
            this.contentView = view;
            iv_head = view.findViewById(R.id.iv_head);
            tv_content = view.findViewById(R.id.tv_content);
            tv_time = view.findViewById(R.id.tv_time);
        }

        public void setOnItemClickListener(final onItemClickListener listener, SortDetailBean data) {
            contentView.setOnClickListener(view -> listener.onItemClicked(data));
        }
    }

    public interface onItemClickListener {
        void onItemClicked(SortDetailBean data);
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {

        public ViewGroup container;

        public AdViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.express_ad_container);
        }
    }

    public Date TimestampToDate(Integer time) {
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
            //System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
