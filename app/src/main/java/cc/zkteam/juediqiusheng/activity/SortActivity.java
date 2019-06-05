package cc.zkteam.juediqiusheng.activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.List;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
public class SortActivity extends BaseActivity {
    private static final String TAG = "sort";
    private ZKRecyclerView zkRecyclerView;
    private String jid;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort;
    }
    @Override
    protected void initViews() {
        zkRecyclerView = findViewById(R.id.zk_recycler_view);
    }
    @Override
    protected void initListener() {
        Intent intent = getIntent();
        if (intent != null){
            jid = intent.getStringExtra("id");
            String name=intent.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                setTitle(name);
            else
                setTitle("分类详情");
        }
    }
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null)
            jid = intent.getStringExtra("id");
        ZKConnectionManager.getInstance().getZKApi().itemJson(jid, 20, 0)
                .enqueue(new ZKCallback<List<SortDetailBean>>() {
                    @Override
                    public void onResponse(final List<SortDetailBean> result) {
                        SortAdapter sortAdapter = new SortAdapter(result);
                        sortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent();
                                intent.setClass(mContext, WebViewActivity.class);
                                String artifactUrl = result.get(position).getArtifactUrl();
                                String url = artifactUrl.substring(artifactUrl.lastIndexOf("\">") + 2, artifactUrl.lastIndexOf("</a>"));
                                intent.putExtra("url", url);
                                startActivity(intent);
                            }
                        });
                        zkRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                        zkRecyclerView.setAdapter(sortAdapter);
                        Log.d(TAG, "onResponse() called with: resultList = [" + result + "]");
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }
                });
    }
}
