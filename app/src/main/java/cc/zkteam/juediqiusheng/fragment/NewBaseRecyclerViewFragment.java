package cc.zkteam.juediqiusheng.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.adapter.SimpleStringRecyclerViewAdapter;
import cc.zkteam.juediqiusheng.base.NewBaseFragment;
import cc.zkteam.juediqiusheng.base.mvp.BaseMVPPresenter;
import cc.zkteam.juediqiusheng.ui.fragment.question.RequestFinishView;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * BaseRecyclerViewFragment
 * Created by WangQing on 2017/11/21.
 */

public abstract class NewBaseRecyclerViewFragment<P extends BaseMVPPresenter> extends NewBaseFragment<P> implements RequestFinishView {

    private static final String TAG = "BaseRecyclerViewFragment";

    public BaseQuickAdapter adapter;

    public ZKRecyclerView zkRecyclerView;
    public ZKRefreshLayout zkRefreshLayout;

    public abstract BaseQuickAdapter getAdapter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initView(rootView);

        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        adapter = getAdapter();

        initZKRefreshLayout(zkRefreshLayout);
        initZKRecyclerView(zkRecyclerView, null, adapter);

        initListener();
        initData(savedInstanceState);

        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void requestFinish() {
        refreshFinish();
    }

    public void initZKRefreshLayout(ZKRefreshLayout zkRefreshLayout) {
        zkRefreshLayout.setWaveColor(0x555555);
        zkRefreshLayout.setLoadMore(true);
    }

    // TODO: 2017/11/21 去除这个 arrayList
    protected void initZKRecyclerView(ZKRecyclerView zkRecyclerView, List arrayList, RecyclerView.Adapter adapter) {
        if (adapter == null) {
            adapter = new SimpleStringRecyclerViewAdapter(arrayList);
        }

        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        zkRecyclerView.setAdapter(adapter);
    }

    /**
     * 加载完成
     */
    public void refreshFinish() {
        zkRefreshLayout.postDelayed(() -> {
            zkRefreshLayout.finishRefresh();
            zkRefreshLayout.finishRefreshLoadMore();
        }, 1000);

    }
}
