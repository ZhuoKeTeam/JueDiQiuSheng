package cc.zkteam.juediqiusheng.module.waterfall;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.BaseActivity;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by abc on 2017/10/27.
 */
@Route(path = "/modules/waterfall/WaterfallActivity")
public class WaterfallActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private RecycleAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private int page = 0;

    @Autowired(name = "categoryId")
    public String categoryId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waterfall;
    }

    @Override
    protected void initViews() {
        initWidget();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        Log.d("param", categoryId);
    }

    private void initWidget() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        adapter = new RecycleAdapter(R.layout.item_waterfall, WaterfallActivity.this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        View headView = LayoutInflater.from(this).inflate(R.layout.view_custom_head, null, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.view_custom_footer, null, false);
        final View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty, (ViewGroup) recyclerView.getParent(), false);
        Spinner spinner = (Spinner) headView.findViewById(R.id.animation_sp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] animationTypes = getResources().getStringArray(R.array.animation_types);
                String type = animationTypes[pos];
                switch (type) {
                    case "Alpha In":
                        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        break;
                    case "Scale In":
                        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        break;
                    case "Slide Bottom":
                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        break;
                    case "SlideIn Right":
                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        break;
                    case "SlideIn Left":
                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Switch switchBtn = (Switch) headView.findViewById(R.id.switch_btn);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    adapter.isFirstOnly(true);
                } else {
                    adapter.isFirstOnly(false);
                }
            }
        });
        TextView loadNoContentView = footerView.findViewById(R.id.nocontent_tv);
        loadNoContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNewData(null);
                adapter.setEmptyView(emptyView);
            }
        });
        TextView retryTv = emptyView.findViewById(R.id.retry_tv);
        retryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        adapter.addHeaderView(headView);
        adapter.addFooterView(footerView);
        adapter.isFirstOnly(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ItemBean itemBean = (ItemBean) adapter.getData().get(position);
                ARouter.getInstance().build("/module/pic/details").withString("url", itemBean.getPicUrl()).navigation();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getData(final boolean isPulldownRefresh) {

        ZKConnectionManager.getInstance().getZKApi()
                .getCategoryListByJId(categoryId, 10, page)
                .enqueue(new ZKCallback<List<ItemBean>>() {
                    @Override
                    public void onResponse(List<ItemBean> result) {
                        if (isPulldownRefresh) {
                            adapter.setEnableLoadMore(true);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        setData(isPulldownRefresh, result);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(WaterfallActivity.this, "发生错误", Toast.LENGTH_SHORT).show();
                        if (!isPulldownRefresh) {
                            adapter.loadMoreFail();
                        } else {
                            adapter.setEnableLoadMore(true);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    private void refresh() {
        page = 0;
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        getData(true);
    }

    private void setData(boolean isRefresh, List<ItemBean> itemBeens) {
        page += 1;
        int size = itemBeens == null ? 0 : itemBeens.size();
        if (isRefresh) {
            adapter.setNewData(itemBeens);
        } else {
            if (size > 0) {
                adapter.addData(itemBeens);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefresh);
            Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
        } else {
            adapter.loadMoreComplete();
        }
    }
}
