package cc.zkteam.juediqiusheng.module.answer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bro.adlib.ad.ZKTencentAD;
import com.bro.adlib.listener.ZKNativeListener;
import com.bro.adlib.strategy.ZKContext;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.adapter.HotNewsItemAdapter;
import cc.zkteam.juediqiusheng.adapter.QuestionAdapter;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.fragment.BaseFragment;
import cc.zkteam.juediqiusheng.module.answer.mvp.QFPresenterImpl;
import cc.zkteam.juediqiusheng.module.answer.mvp.QFView;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;
import dagger.android.support.AndroidSupportInjection;

/**
 * 问答 Fragment
 */
public class QuestionFragment extends BaseFragment implements QFView, View.OnClickListener {

    ZKRefreshLayout zkRefreshLayout;
    ZKRecyclerView zkRecyclerView;
    LinearLayout llAbout;
    Button btn_change_ad;

    @Inject
    QFPresenterImpl presenter;

    @Inject
    QuestionViewModel questionViewModel;


    public static QuestionFragment newInstance() {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_question;
    }

    @Override
    public void initView(View rootView) {
        llAbout = rootView.findViewById(R.id.ll_about);
        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);
        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
        btn_change_ad = rootView.findViewById(R.id.btn_change_ad);
        initBtn();

        initAdapter();
    }
    private void initAdapter() {
        questionAdapter = new QuestionAdapter();
        questionAdapter.setADViewPositionMap(mAdViewPositionMap);
        zkRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        zkRecyclerView.setAdapter(questionAdapter);
    }

    QuestionAdapter questionAdapter;
    public static int FIRST_AD_POSITION = 1; // 第一条广告的位置
    public static int ITEMS_PER_AD = 2;     // 每间隔多少个个条目插入一条广告
    public static final int AD_COUNT = 10;    // 加载广告的条数，取值范围为[1, 10]
    private List<NativeExpressADView> mAdViewList;
    private List<Object> mNormalDataList = new ArrayList<>();
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<>();

    private void initNativeExpressAD() {
        ZKContext zkContext = new ZKContext(ZKTencentAD.getInstance());
        zkContext.initNativeExpressAD(getActivity().getApplicationContext(), AD_COUNT, new ZKNativeListener() {
            @Override
            public void onNativeCallBack(List adList) {
                if (!adList.isEmpty() && adList.get(0) instanceof NativeExpressADView) {
                    mAdViewList = adList;
                    for (int i = 0; i < mAdViewList.size(); i++) {
                        int position = FIRST_AD_POSITION + ITEMS_PER_AD * i;
                        if (position < mNormalDataList.size()) {
                            NativeExpressADView view = mAdViewList.get(i);
//                            GDTLogger.i("ad load[" + i + "]: " + getAdInfo(view));
//                            if (view.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
//                                view.setMediaListener(mediaListener);
//                            }
                            mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                            questionAdapter.addADViewToPosition(position, mAdViewList.get(i));
                        }
                    }
                    questionAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        questionViewModel.getQuestionList().observe(this, sortDetailBeans -> {
            mNormalDataList.clear();
            mNormalDataList.addAll(sortDetailBeans);
            questionAdapter.setNewData(mNormalDataList);
            questionAdapter.notifyDataSetChanged();
            initNativeExpressAD();
        });

        presenter.currentPage = 0;
        presenter.loadData(false);
    }

    @Override
    public void initListener() {
        llAbout.setOnClickListener(this);
        btn_change_ad.setOnClickListener(this);
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.currentPage = 0;
                presenter.loadData(false);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                presenter.loadData(true);
            }
        });
        questionAdapter.setListener(sortDetailBean -> {
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                String url = sortDetailBean.getArtifactUrl();
                //url = "<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861</a></p>";
                url = url.substring(url.lastIndexOf("\">") + 2, url.lastIndexOf("</a>"));
                intent.putExtra("url", url);
                startActivity(intent);
            });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_about:
                String url = "file:///android_asset/about_rules.html";
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
                break;
            case R.id.btn_change_ad:
                changeAd();
                break;
        }
    }

    private void changeAd() {
//        int ad_type = SPUtils.getInstance().getInt("ad_type", -1);
        if (Constant.ADTYPE == 1) {
            btn_change_ad.setText("当前广告:" + "百度");
            Constant.ADTYPE = 2;
            SPUtils.getInstance().put("ad_type", 2, true);
            ToastUtils.showShort("广告切换成百度");
        } else if (Constant.ADTYPE == 2) {
            btn_change_ad.setText("当前广告:" + "腾讯");
            Constant.ADTYPE = 1;
            SPUtils.getInstance().put("ad_type", 1, true);
            ToastUtils.showShort("广告切换成腾讯");
        }

    }

    private void initBtn() {
        if (Constant.ADTYPE == 1) {
            btn_change_ad.setText("当前广告:" + "腾讯");
        } else if (Constant.ADTYPE == 2) {
            btn_change_ad.setText("当前广告:" + "百度");
        }
    }

    @Override
    public void requestFinish() {
        zkRefreshLayout.postDelayed(() -> {
            zkRefreshLayout.finishRefresh();
            zkRefreshLayout.finishRefreshLoadMore();
        }, 1000);
    }
}
