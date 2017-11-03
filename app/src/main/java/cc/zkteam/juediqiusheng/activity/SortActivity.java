package cc.zkteam.juediqiusheng.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.base.RvListener;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;

public class SortActivity extends BaseActivity {
    private static final String TAG = "sort";
    private RecyclerView rvSort;
    private String jid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort;
    }

    @Override
    protected void initViews() {
        setTitle("分类");
        rvSort = findViewById(R.id.rv_sort);


    }

    @Override
    protected void initListener() {
        Intent intent = getIntent();
        if (intent != null)
            jid = intent.getStringExtra("id");
    }

    @Override
    protected void initData() {
        ZKConnectionManager.getInstance().getZKApi().getSortDetail(jid, "20")
                .enqueue(new ZKCallback<List<SortDetailBean>>() {
                    @Override
                    public void onResponse(final List<SortDetailBean> result) {

                        SortAdapter sortAdapter = new SortAdapter(mContext, result, new RvListener() {
                            @Override
                            public void onItemClick(int id, int position) {
                                Intent intent = new Intent();
                                intent.setClass(mContext, SortDetailActivity.class);
                                String artifactUrl = result.get(position).getArtifactUrl();
                                String url = artifactUrl.substring(artifactUrl.lastIndexOf("\">") + 2, artifactUrl.lastIndexOf("</a>"));
                                intent.putExtra("url", url);
                                startActivity(intent);

                            }
                        });
                        rvSort.setLayoutManager(new LinearLayoutManager(mContext));
                        rvSort.setAdapter(sortAdapter);
                        Log.d(TAG, "onResponse() called with: resultList = [" + result + "]");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }
                });
    }
}
