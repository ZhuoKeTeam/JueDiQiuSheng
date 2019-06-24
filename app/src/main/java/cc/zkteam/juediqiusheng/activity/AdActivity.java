package cc.zkteam.juediqiusheng.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cc.zkteam.juediqiusheng.R;

/**
 * Created by renxuelong on 2019-06-24
 */
public class AdActivity extends BaseActivity {

    @BindView(R.id.list_ad)
    RecyclerView recyclerView;
    private List<String> mDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mDatas.add("视频奖励 -> " + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdAdapter());
    }

    private class AdAdapter extends RecyclerView.Adapter<AdViewHolder> {

        @Override
        public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad_list, parent, false));
        }

        @Override
        public void onBindViewHolder(AdViewHolder holder, int position) {
            holder.tvAd.setText(mDatas.get(position));
            holder.tvAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AdActivity.this, "AdActivity", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    private class AdViewHolder extends RecyclerView.ViewHolder {

        TextView tvAd;

        AdViewHolder(View itemView) {
            super(itemView);
            tvAd = itemView.findViewById(R.id.tv_ad_item);
        }
    }
}
