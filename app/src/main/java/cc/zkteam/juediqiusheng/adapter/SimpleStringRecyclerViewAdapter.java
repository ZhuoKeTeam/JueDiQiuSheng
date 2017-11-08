package cc.zkteam.juediqiusheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.juediqiusheng.R;

/**
 * SimpleStringRecyclerViewAdapter
 * Created by WangQing on 2017/11/7.
 */
public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;
        public final TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.pic);
            textView = view.findViewById(R.id.text);
        }


    }

    List list;

    public SimpleStringRecyclerViewAdapter(Context context, List list) {
        super();
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText((CharSequence) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
