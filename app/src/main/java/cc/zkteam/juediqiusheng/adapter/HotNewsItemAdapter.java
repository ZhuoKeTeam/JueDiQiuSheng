package cc.zkteam.juediqiusheng.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.Utils;

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
    public List<RecommendedBean> list;

    private HotNewsAdapter.onItemClickListener listener;

    public HotNewsItemAdapter(List list) {
        this.list = list;
    }

    public void setListener(HotNewsAdapter.onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hot_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (obj == null)
            return;

        if (obj instanceof SortDetailBean) {
//            SortDetailBean sortDetailBean = list.get(position);
//            holder.tvArticalName.setText(sortDetailBean.getArtifactName());
//            holder.imageView.setImageURI(sortDetailBean.getPicUrl());
//            holder.tvOrigin.setText("来源：XX");
//            holder.tvTime.setText("2017/12/17");
//            if (listener != null) {
//                holder.setOnClickListener(listener, sortDetailBean);
//            }
        } else if (obj instanceof RecommendedBean) {
            RecommendedBean recommendedBean = list.get(position);
            holder.tvArticalName.setText(recommendedBean.getContext());
            if (!TextUtils.isEmpty(recommendedBean.getPicUrl())){
                holder.imageView.setImageURI(recommendedBean.getPicUrl());}
            else{
                holder.imageView.setImageResource(R.drawable.ic_default);
            }
            holder.tvOrigin.setText(Utils.getApp().getResources().getString(R.string.source_text));
            holder.tvTime.setText(TimeUtils.millis2String(recommendedBean.getCollectionDate()*1000));
            if (listener != null) {
                holder.setOnClickListener(listener, recommendedBean);
            }

        } else if (obj instanceof String) {
//            holder.textView.setText((CharSequence) obj);
        }
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

        public ViewHolder(View view) {
            super(view);
            this.contentView = view;
            imageView = view.findViewById(R.id.pic);
            tvArticalName = view.findViewById(R.id.tv_title);
            tvOrigin = view.findViewById(R.id.tv_origin);
            tvTime = view.findViewById(R.id.tv_time);
        }

        public void setOnClickListener(final HotNewsAdapter.onItemClickListener listener, RecommendedBean data) {
            this.data = data;
            contentView.setOnClickListener(view -> listener.onItemClicked(ViewHolder.this.data));
        }
    }

}
