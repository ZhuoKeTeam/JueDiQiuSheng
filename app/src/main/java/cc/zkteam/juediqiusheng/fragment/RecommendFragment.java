package cc.zkteam.juediqiusheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.adapter.SimpleStringRecyclerViewAdapter;
import cc.zkteam.juediqiusheng.base.OldBaseFragment;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * RecommendFragment
 * Created by WangQing on 2017/10/30.
 */

public class RecommendFragment extends OldBaseFragment {

    ZKBanner zkBanner;
    ZKRecyclerView zkRecyclerView;
    ZKRefreshLayout zkRefreshLayout;

    public RecommendFragment() {
    }

    public void initZKRefreshLayout(ZKRefreshLayout zkRefreshLayout) {
        zkRefreshLayout.setWaveColor(0x555555);
        zkRefreshLayout.setLoadMore(true);
    }

    SimpleStringRecyclerViewAdapter adapter;

    // TODO: 2017/11/21 去除这个 arrayList
    protected void initZKRecyclerView(ZKRecyclerView zkRecyclerView, List arrayList) {
        if (adapter == null) {
            adapter = new SimpleStringRecyclerViewAdapter(arrayList);
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
        return 0;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rec_recommend, container, false);
        zkBanner = rootView.findViewById(R.id.zk_banner);
        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List testData = TestData.getDefaultTextData();

        initZKBanner(zkBanner);
        initZKRecyclerView(zkRecyclerView, testData);
        initZKRefreshLayout(zkRefreshLayout);
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testData.addAll(testData);
                        zkRecyclerView.getAdapter().notifyDataSetChanged();
                        zkRefreshLayout.finishRefresh();
                    }
                }, 3000);
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testData.addAll(testData);
                        zkRecyclerView.getAdapter().notifyDataSetChanged();
                        zkRefreshLayout.finishRefreshLoadMore();
                    }
                }, 3000);
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
}
