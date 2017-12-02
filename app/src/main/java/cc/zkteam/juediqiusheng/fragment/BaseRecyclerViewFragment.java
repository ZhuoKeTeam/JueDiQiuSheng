package cc.zkteam.juediqiusheng.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.adapter.SimpleStringRecyclerViewAdapter;
import cc.zkteam.juediqiusheng.module.answer.mvp.RequestFinishView;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;
import dagger.android.support.DaggerFragment;

/**
 * BaseRecyclerViewFragment
 * Created by WangQing on 2017/11/21.
 */

public abstract class BaseRecyclerViewFragment extends DaggerFragment implements RequestFinishView {


    protected View rootView;
    //context
    protected Context mContext = null;

    // 获取布局资源文件
    public abstract @LayoutRes
    int getLayoutId();

    // 初始化布局
    public abstract void initView(View rootView);

    // 初始化数据
    public abstract void initData(Bundle savedInstanceState);

    // 初始化相关 View 的 listener
    public abstract void initListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    protected void initZKBanner(ZKBanner zkBanner) {
        //设置图片加载器
        zkBanner.setImageLoader(new ZKImageLoader());
        //设置图片集合
        zkBanner.setImages(TestData.getTestPics());
        //banner设置方法全部调用完毕时最后调用
        zkBanner.start();
    }







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
