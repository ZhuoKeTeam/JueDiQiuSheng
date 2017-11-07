package cc.zkteam.juediqiusheng.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.adapter.SimpleStringRecyclerViewAdapter;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * BaseFragment
 * Created by WangQing on 2017/11/7.
 */

public class BaseFragment extends Fragment {

    protected void initZKBanner(ZKBanner zkBanner) {
        //设置图片加载器
        zkBanner.setImageLoader(new ZKImageLoader());
        //设置图片集合
        zkBanner.setImages(TestData.getTestPics());
        //banner设置方法全部调用完毕时最后调用
        zkBanner.start();
    }

    protected void initZKRefreshLayout(ZKRefreshLayout zkRefreshLayout) {
        zkRefreshLayout.setWaveColor(0x555555);
        zkRefreshLayout.setLoadMore(true);
    }

    protected void initZKRecyclerView(ZKRecyclerView zkRecyclerView, List arrayList) {
        SimpleStringRecyclerViewAdapter recyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getContext(), arrayList);
        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        zkRecyclerView.setAdapter(recyclerViewAdapter);
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
