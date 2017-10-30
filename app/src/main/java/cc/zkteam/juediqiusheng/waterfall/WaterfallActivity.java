package cc.zkteam.juediqiusheng.waterfall;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by abc on 2017/10/27.
 */
@Route(path = "/waterfall/WaterfallActivity")
public class WaterfallActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecycleAdapter adapter;

    private ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);
        initWidget();
        initData();
    }

    private void initWidget() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(WaterfallActivity.this, "上拉刷新", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        progressDialog = new ProgressDialog(this);
        adapter = new RecycleAdapter(R.layout.item_waterfall, WaterfallActivity.this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Toast.makeText(WaterfallActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
                adapter.loadMoreComplete();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    private void initData() {
        OkGo.get("http://112.124.22.238:8081/course_api/campaign/recommend")
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        progressDialog.setMessage("加载中...");
                        progressDialog.show();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        s = "{" + "\"data\":" + s + "}";
                        RefreshBean refreshBean = new Gson().fromJson(s, RefreshBean.class);
                        if (refreshBean != null) {
                            if (refreshBean.getData().size() != 0) {
                                List<ItemBean> itemBeens = new ArrayList<ItemBean>();
                                List<RefreshBean.DataBean> datas = refreshBean.getData();
                                for (RefreshBean.DataBean dataBean : datas) {
                                    RefreshBean.DataBean.CpOneBean cpOneBean = dataBean.getCpOne();
                                    if (cpOneBean != null) {
                                        ItemBean itemBean = new ItemBean();
                                        itemBean.setId(cpOneBean.getId());
                                        itemBean.setTitle(cpOneBean.getTitle());
                                        itemBean.setImgUrl(cpOneBean.getImgUrl());
                                        itemBeens.add(itemBean);
                                    }
                                    RefreshBean.DataBean.CpTwoBean cpTwoBean = dataBean.getCpTwo();
                                    if (cpTwoBean != null) {
                                        ItemBean itemBean = new ItemBean();
                                        itemBean.setId(cpTwoBean.getId());
                                        itemBean.setTitle(cpTwoBean.getTitle());
                                        itemBean.setImgUrl(cpTwoBean.getImgUrl());
                                        itemBeens.add(itemBean);
                                    }
                                    RefreshBean.DataBean.CpThreeBean cpThreeBean = dataBean.getCpThree();
                                    if (cpThreeBean != null) {
                                        ItemBean itemBean = new ItemBean();
                                        itemBean.setId(cpThreeBean.getId());
                                        itemBean.setTitle(cpThreeBean.getTitle());
                                        itemBean.setImgUrl(cpThreeBean.getImgUrl());
                                        itemBeens.add(itemBean);
                                    }
                                }
                                adapter.addData(itemBeens);
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Toast.makeText(WaterfallActivity.this, "发生错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAfter(String s, Exception e) {
                        super.onAfter(s, e);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}
