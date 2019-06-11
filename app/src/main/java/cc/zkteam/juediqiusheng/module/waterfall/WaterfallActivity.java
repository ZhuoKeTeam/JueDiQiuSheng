package cc.zkteam.juediqiusheng.module.waterfall;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.BaseActivity;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.utils.ZKSP;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TENCENT_APP_ID;
import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TENCENT_REWARD_KEY;

/**
 * Created by abc on 2017/10/27.
 */
@Route(path = "/modules/waterfall/WaterfallActivity")
public class WaterfallActivity extends BaseActivity implements RewardVideoADListener {

    private static final String TAG = WaterfallActivity.class.getSimpleName();

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
        initTencentRewardVideoAd(this);
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
        adapter.setOnItemClickListener((adapter, view, position) -> {
            ItemBean itemBean = (ItemBean) adapter.getData().get(position);


            if (ZKSP.isLiving()) {
                ZKSP.save();
                ToastUtils.showShort("消耗10点生命值");
                ARouter.getInstance().build("/module/pic/details").withString("url", itemBean.getPicUrl()).navigation();
            } else {
                // 2019-05-30 Dialog 提示观看广告赚生命值
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(this);
                normalDialog.setTitle("是否续命？");
                normalDialog.setMessage("您的生命值每次启动 App 就会分配了30点。 \r\n 每看一次高清无码大图会消耗10点，目前已经用尽。\r\n 点击 续命 可以自动获取30点。");
                normalDialog.setPositiveButton("我要续命",
                        (dialog, which) -> {
                            // 2019-05-30 启动广告，等广告完成后直接继续操作。
//                            ZKAD.showGoogleJLAD(this, ZKAD.getCurrentRewardedAd());
                            // 2. 加载激励视频广告
                            if (rewardVideoAD != null) {
                                rewardVideoAD.loadAD();
                            }
                        });
                normalDialog.setNegativeButton("不看了吧",
                        (dialog, which) -> {
                            //...To-do
                        });
                // 显示
                normalDialog.show();

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


    //**************************** 腾讯激励视频 *****************************
    private RewardVideoAD rewardVideoAD;
    private boolean adLoaded;//广告加载成功标志
    private boolean videoCached;//视频素材文件下载完成标志

    public void initTencentRewardVideoAd(RewardVideoADListener listener) {
        // 1. 初始化激励视频广告
        rewardVideoAD = new RewardVideoAD(this, AD_TENCENT_APP_ID, AD_TENCENT_REWARD_KEY, listener);
        adLoaded = false;
        videoCached = false;
//        rewardVideoAD.loadAD();
    }

    public void showTencentRewardVideoAd() {
        // 3. 展示激励视频广告
        //广告展示检查1：广告成功加载，此处也可以使用videoCached来实现视频预加载完成后再展示激励视频广告的逻辑
        if (adLoaded && rewardVideoAD != null) {
            //广告展示检查2：当前广告数据还没有展示过
            if (!rewardVideoAD.hasShown()) {
                //建议给广告过期时间加个buffer，单位ms，这里demo采用1000ms的buffer
                long delta = 1000;
                //广告展示检查3：展示广告前判断广告数据未过期
                if (SystemClock.elapsedRealtime() < (rewardVideoAD.getExpireTimestamp() - delta)) {
                    rewardVideoAD.showAD();
                } else {
                    Toast.makeText(this, "激励视频广告已过期，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "此条广告已经展示过，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "成功加载广告后再进行广告展示！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 广告加载成功，可在此回调后进行广告展示
     **/
    @Override
    public void onADLoad() {
        adLoaded = true;
        String msg = "load ad success ! expireTime = " + new Date(System.currentTimeMillis() +
                rewardVideoAD.getExpireTimestamp() - SystemClock.elapsedRealtime());
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        showTencentRewardVideoAd();
    }

    /**
     * 视频素材缓存成功，可在此回调后进行广告展示
     */
    @Override
    public void onVideoCached() {
        videoCached = true;
        Log.i(TAG, "onVideoCached");
    }

    /**
     * 激励视频广告页面展示
     */
    @Override
    public void onADShow() {
        Log.i(TAG, "onADShow");
    }

    /**
     * 激励视频广告曝光
     */
    @Override
    public void onADExpose() {
        Log.i(TAG, "onADExpose");
    }

    /**
     * 激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
     */
    @Override
    public void onReward() {
        Log.i(TAG, "onReward");
    }

    /**
     * 激励视频广告被点击
     */
    @Override
    public void onADClick() {
        Log.i(TAG, "onADClick");
    }

    /**
     * 激励视频播放完毕
     */
    @Override
    public void onVideoComplete() {
        Log.i(TAG, "onVideoComplete");
    }

    /**
     * 激励视频广告被关闭
     */
    @Override
    public void onADClose() {
        Log.i(TAG, "onADClose");
    }

    /**
     * 广告流程出错
     */
    @Override
    public void onError(AdError adError) {
        String msg = String.format(Locale.getDefault(), "onError, error code: %d, error msg: %s",
                adError.getErrorCode(), adError.getErrorMsg());
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
