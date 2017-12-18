package cc.zkteam.juediqiusheng.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.HotNewsBean;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;

/**
 * HotNewsAdapter
 * Created by Doraemon123 on 2017/12/17.
 */
public class HotNewsAdapter
        extends RecyclerView.Adapter<HotNewsAdapter.ViewHolder> {

    List<HotNewsBean> list;


    private onItemClickListener listener;

    public HotNewsAdapter(List list) {
        this.list = list;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hot_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (obj == null)
            return;

        if (obj instanceof HotNewsBean) {
            HotNewsBean hotNewsBean = list.get(position);
            holder.tvTitle.setText(hotNewsBean.getTitle());
            initZKRecyclerView(holder.zkRecyclerView, hotNewsBean.getSortDetailBeans());

        } else if (obj instanceof String) {
//            holder.textView.setText((CharSequence) obj);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ZKRecyclerView zkRecyclerView;
        public final TextView tvTitle;


        public View contentView;


        public ViewHolder(View view) {
            super(view);
            this.contentView = view;
            zkRecyclerView = view.findViewById(R.id.zk_recycler_view);
            tvTitle = view.findViewById(R.id.tv_title);
        }

    }

    public interface onItemClickListener {
        void onItemClicked(SortDetailBean data);
    }

    protected void initZKRecyclerView(ZKRecyclerView zkRecyclerView, List arrayList) {
        HotNewsItemAdapter hotNewsItemAdapter = new HotNewsItemAdapter(arrayList);
        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        zkRecyclerView.setAdapter(hotNewsItemAdapter);
    }
}
