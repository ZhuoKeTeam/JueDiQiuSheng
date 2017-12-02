package cc.zkteam.juediqiusheng.ui.fragment.recommend;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.adapter.SimpleStringRecyclerViewAdapter;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.base.BaseRecyclerViewFragment;
import cc.zkteam.juediqiusheng.ui.fragment.recommend.mvp.RecommendPresenterImpl;
import cc.zkteam.juediqiusheng.ui.fragment.recommend.mvp.RecommendView;
import cc.zkteam.juediqiusheng.view.ZKBanner;

/**
 * Created by WangQing on 2017/12/3.
 */

public class RecommendFragment extends BaseRecyclerViewFragment<RecommendPresenterImpl> implements RecommendView {

    ZKBanner zkBanner;
    SimpleStringRecyclerViewAdapter adapter;

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
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        final List testData = TestData.getDefaultTextData();

        zkBanner.setLifecycle(getLifecycle());
        initZKBanner(zkBanner);
        initZKRecyclerView(zkRecyclerView, testData, adapter);


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
    public void initListener() {

    }




    @Override
    public BaseQuickAdapter getAdapter() {
        return new SortAdapter(null);
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onNoData() {

    }

    @Override
    public void onNetFinished() {

    }
}
