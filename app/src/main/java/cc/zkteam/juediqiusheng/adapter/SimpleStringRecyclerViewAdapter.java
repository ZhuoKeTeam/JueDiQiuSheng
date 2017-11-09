package cc.zkteam.juediqiusheng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.view.ZKImageView;

/**
 * SimpleStringRecyclerViewAdapter
 * Created by WangQing on 2017/11/7.
 */
public class SimpleStringRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    List<RecommendedBean> list;


    private onItemClickListener listener;

    public SimpleStringRecyclerViewAdapter(List list) {
        this.list = list;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecommendedBean recommendedBean = list.get(position);
        holder.textView.setText(recommendedBean.getContext());
        holder.imageView.setImageURI(recommendedBean.getPicUrl());

        if (listener!=null){
            holder.setOnClickListener(listener, recommendedBean);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ZKImageView imageView;
        public final TextView textView;

        public View contentView;
        private RecommendedBean data;

        public ViewHolder(View view) {
            super(view);
            this.contentView = view;
            imageView = view.findViewById(R.id.pic);
            textView = view.findViewById(R.id.text);
        }

        public void setOnClickListener(final onItemClickListener listener, RecommendedBean data) {
            this.data = data;
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(ViewHolder.this.data);
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClicked(RecommendedBean data);
    }

}
