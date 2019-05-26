package cc.zkteam.juediqiusheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.ad.ZKAD;
import cc.zkteam.juediqiusheng.adapter.HotNewsAdapter;
import cc.zkteam.juediqiusheng.bean.HotNewsBean;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * RecommendFragment
 * Created by WangQing on 2017/10/30.
 */

public class RecommendFragment extends BaseFragment implements OnBannerListener {

    ZKBanner zkBanner;
    ZKRecyclerView zkRecyclerView;
    ZKRefreshLayout zkRefreshLayout;
    private List<RecommendedBean> bannerBeans = new ArrayList<>();
    private List<HotNewsBean> listBeans = new ArrayList<>();
    private int pageIndex = 1;

    public RecommendFragment() {
    }

    public void initZKRefreshLayout(ZKRefreshLayout zkRefreshLayout) {
        zkRefreshLayout.setWaveColor(0x555555);
        zkRefreshLayout.setLoadMore(true);
    }

    HotNewsAdapter adapter;

    // TODO: 2017/11/21 去除这个 arrayList
    protected void initZKRecyclerView(ZKRecyclerView zkRecyclerView, List arrayList) {
        if (adapter == null) {
            adapter = new HotNewsAdapter(arrayList);
            adapter.setListener(data -> {
                Intent intent = new Intent(Utils.getApp(), WebViewActivity.class);
                String artifactUrl = data.getTjSourceUrl();
                String url = artifactUrl.substring(artifactUrl.lastIndexOf("\">") + 2, artifactUrl.lastIndexOf("</a>"));
                intent.putExtra("url", url);
                getActivity().startActivity(intent);
            });
        }

        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        zkRecyclerView.setAdapter(adapter);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rec_recommend;
    }

    @Override
    public void initView(View rootView) {
        zkBanner = rootView.findViewById(R.id.zk_banner);
        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);

        ZKAD.initHFAD(rootView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_rec_recommend, container, false);
//        zkBanner = rootView.findViewById(R.id.zk_banner);
//        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
//        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);
//        return rootView;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initZKRecyclerView(zkRecyclerView, listBeans);
        initZKRefreshLayout(zkRefreshLayout);
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                listBeans.clear();
                updateListData();
//                materialRefreshLayout.postDelayed(() -> {
//                    listBeans.addAll(testData);
//                    getListData(pageIndex);
//                    zkRecyclerView.getAdapter().notifyDataSetChanged();
//                    zkRefreshLayout.finishRefresh();
//                }, 1000);
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                materialRefreshLayout.finishRefreshLoadMore();

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        testData.addAll(testData);
//                        pageIndex++;
//                        getListData(pageIndex);
//                        zkRecyclerView.getAdapter().notifyDataSetChanged();
//                        zkRefreshLayout.finishRefreshLoadMore();
//                    }
//                }, 3000);
            }
        });
        zkBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        getBannerDatas();
        updateListData();
    }

    private void updateListData() {
        listBeans.clear();
        getUpdatesData();
        getNewsData();
        getVideoData();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        zkBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        zkBanner.stopAutoPlay();
    }

    protected void getBannerDatas() {
        ZKConnectionManager.getInstance().getZKApi()
                .getRecommended("10005", "20")
                .enqueue(new ZKCallback<List<RecommendedBean>>() {
                    @Override
                    public void onResponse(List<RecommendedBean> result) {
//                        SimpleStringRecyclerViewAdapter recyclerViewAdapter =
//                                new SimpleStringRecyclerViewAdapter(result);
//                        recyclerViewAdapter.setListener(HotInformationFragment.this);
//                        zkRecyclerView.setAdapter(recyclerViewAdapter);
                        bannerBeans.addAll(result);
                        List<String> images = new ArrayList<>();

                        for (RecommendedBean recommendedBean : result) {
                            images.add(recommendedBean.getPicUrl());
                        }
                        List<String> titles = new ArrayList<>();
                        for (RecommendedBean recommendedBean : result) {
                            titles.add(recommendedBean.getContext());
                        }
                        initZKBanner(zkBanner, images, titles);
                        zkBanner.setOnBannerListener(RecommendFragment.this);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        String artifactUrl = bannerBeans.get(position).getTjSourceUrl();
        String url = artifactUrl.substring(artifactUrl.lastIndexOf("\">") + 2, artifactUrl.lastIndexOf("</a>"));
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void getUpdatesData() {
        ZKConnectionManager.getInstance().getZKApi()
                .getRecommended("10006", "5")
                .enqueue(new ZKCallback<List<RecommendedBean>>() {
                    @Override
                    public void onResponse(List<RecommendedBean> result) {

                        HotNewsBean hotNewsBean = new HotNewsBean();
                        hotNewsBean.setTitle("游戏更新");
                        hotNewsBean.setSortDetailBeans(result);
                        listBeans.add(0, hotNewsBean);

                        zkRecyclerView.getAdapter().notifyDataSetChanged();
                        zkRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
    }

    private void getNewsData() {
        ZKConnectionManager.getInstance().getZKApi()
                .getRecommended("10007", "5")
                .enqueue(new ZKCallback<List<RecommendedBean>>() {
                    @Override
                    public void onResponse(List<RecommendedBean> result) {

                        HotNewsBean hotNewsBean = new HotNewsBean();
                        hotNewsBean.setTitle("上手体验");
                        hotNewsBean.setSortDetailBeans(result.subList(0, 5));
                        listBeans.add(0, hotNewsBean);

                        zkRecyclerView.getAdapter().notifyDataSetChanged();
                        zkRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
    }

    private void getVideoData() {
        ZKConnectionManager.getInstance().getZKApi()
                .getRecommended("10008", "5")
                .enqueue(new ZKCallback<List<RecommendedBean>>() {
                    @Override
                    public void onResponse(List<RecommendedBean> result) {

//                        HotNewsBean hotNewsBean = new HotNewsBean();
//                        hotNewsBean.setTitle("精彩视频");
//                        hotNewsBean.setSortDetailBeans(result);
//                        listBeans.add(hotNewsBean);
////                        listBeans.addAll(result);
//                        zkRecyclerView.getAdapter().notifyDataSetChanged();
//                        zkRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
    }
}
