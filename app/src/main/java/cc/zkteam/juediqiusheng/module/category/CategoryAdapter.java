package cc.zkteam.juediqiusheng.module.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.SortDetailActivity;

/**
 *
 * @author renxuelong
 * @date 17-10-26
 * Description: 分类列表界面
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private List<BeanCategory.ResultBean> datas;

    public CategoryAdapter(Context context, List datas) {
        this.context = context;
        this.datas = datas;
    }

    @SuppressLint("InflateParams")
    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.item_category, null));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        final BeanCategory.ResultBean item = datas.get(position);
        holder.tvTitle.setText(item.getCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, SortDetailActivity.class);

                String url = item.getCategoryUrl();
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;

        CategoryHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
