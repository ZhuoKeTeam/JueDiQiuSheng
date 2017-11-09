package cc.zkteam.juediqiusheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.adapter.SimpleStringRecyclerViewAdapter;
import cc.zkteam.juediqiusheng.bean.PicBean;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * 热门资讯
 * <p>
 * Created by WangQing on 2017/10/30.
 */
public class HotInformationFragment extends Fragment implements SimpleStringRecyclerViewAdapter.onItemClickListener, OnBannerListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ZKBanner zkBanner;
    ZKRecyclerView zkRecyclerView;
    ZKRefreshLayout zkRefreshLayout;

    public HotInformationFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HotInformationFragment newInstance(String text) {
        HotInformationFragment fragment = new HotInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rec_hot_infor, container, false);
        zkBanner = rootView.findViewById(R.id.zk_banner);
        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);
        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initZKBanner();
        initZKRecyclerView();
        initZKRefreshLayout(zkRefreshLayout);
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                initZKRecyclerView();
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
            }
        });
    }

    protected void initZKBanner() {
        ZKConnectionManager.getInstance().getZKApi()
                .getCategoryList("901183", 7)
                .enqueue(new ZKCallback<List<PicBean>>() {
                    @Override
                    public void onResponse(List<PicBean> result) {
                        List<String> images = new ArrayList<>();

                        for (PicBean picBean : result) {
                            images.add(picBean.getPicUrl());
                        }

                        //设置图片加载器
                        zkBanner.setImageLoader(new ZKImageLoader());
                        //设置图片集合
                        zkBanner.setImages(images);
                        //banner设置方法全部调用完毕时最后调用
                        zkBanner.start();
                        zkBanner.setOnBannerListener(HotInformationFragment.this);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });

    }

    protected void initZKRefreshLayout(ZKRefreshLayout zkRefreshLayout) {
        zkRefreshLayout.setWaveColor(0x555555);
        zkRefreshLayout.setLoadMore(false);
    }

    protected void initZKRecyclerView() {
        ZKConnectionManager.getInstance().getZKApi()
                .getRecommended("10005", "20")
                .enqueue(new ZKCallback<List<RecommendedBean>>() {
                    @Override
                    public void onResponse(List<RecommendedBean> result) {
                        SimpleStringRecyclerViewAdapter recyclerViewAdapter =
                                new SimpleStringRecyclerViewAdapter(result);
                        recyclerViewAdapter.setListener(HotInformationFragment.this);
                        zkRecyclerView.setAdapter(recyclerViewAdapter);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
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

    @Override
    public void onItemClicked(RecommendedBean data) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        String artifactUrl = data.getNewsSourceUrl();
        String url = artifactUrl.substring(artifactUrl.lastIndexOf("\">") + 2, artifactUrl.lastIndexOf("</a>"));
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
