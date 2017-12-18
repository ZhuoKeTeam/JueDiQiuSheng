package cc.zkteam.juediqiusheng.strategy;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cc.zkteam.juediqiusheng.activity.SortActivity;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.bean.BannerBean;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.lifecycle.strategy.ZKStrategyViewModel;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.strategy.base.ViewHolder;
import cc.zkteam.juediqiusheng.strategy.wrapper.HeaderAndFooterWrapper;
import cc.zkteam.juediqiusheng.strategy.wrapper.LoadMoreWrapper;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;


public class StrategyFragment extends Fragment {
    private static String TAG = "StrategyFragment";
    private ZKBanner zkBanner;
    private ZKRefreshLayout zkRefreshLayout;
    private ZKRecyclerView mRecyclerView;
    private List<CategoryBean> mDatas = new ArrayList<>();
    private CommonAdapter<CategoryBean> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;


    private ZKStrategyViewModel strategyViewModel;

    public StrategyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StrategyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StrategyFragment newInstance() {
        StrategyFragment fragment = new StrategyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategy, container, false);

        strategyViewModel = ViewModelProviders.of(getActivity()).get(ZKStrategyViewModel.class);

        zkBanner = view.findViewById(R.id.zk_banner);
        zkRefreshLayout = view.findViewById(R.id.zk_refresh_layout);
        mRecyclerView = view.findViewById(R.id.zk_recycler_view);

//        initZKBanner(zkBanner);
        zkBanner.setLifecycle(getLifecycle());
        getLifecycle().addObserver(zkBanner);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zkRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });

        mAdapter = new CommonAdapter<CategoryBean>(getActivity(), R.layout.item_list, mDatas) {
            @Override
            protected void convert(ViewHolder holder, CategoryBean s, int position) {
                holder.setText(R.id.id_item_list_title, s.getCategoryName());
            }
        };
        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
//        mLoadMoreWrapper.setLoadMoreView(R.item_banner.default_loading);
        mRecyclerView.setAdapter(mLoadMoreWrapper);


        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();

                intent.setClass(getActivity(), SortActivity.class);
                String id = String.valueOf(mDatas.get(position).getId());
                intent.putExtra("id", id);
                intent.putExtra("name", mDatas.get(position).getCategoryName());

                intent.putExtra("id", String.valueOf(mDatas.get(position).getId()));
                startActivity(intent);
//                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        strategyViewModel.getCategoryList().observe(this, new Observer<List<CategoryBean>>() {
            @Override
            public void onChanged(@Nullable List<CategoryBean> categoryBeans) {
                mDatas.clear();
                mDatas.addAll(categoryBeans);
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });
        ZKConnectionManager.getInstance().getZKApi().getStrategy(5)
                .enqueue(new ZKCallback<List<BannerBean>>() {
                    @Override
                    public void onResponse(List<BannerBean> bannerBeans) {
                        List<String> bannerData = new ArrayList<>();
                        if (bannerBeans == null && bannerBeans.size() == 0)
                            return;
                        for (int i = 0; i < bannerBeans.size(); i++) {
                            bannerData.add(bannerBeans.get(i).getTjPicUrl());
                        }
                        initZKBanner(zkBanner, bannerData, bannerBeans);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }


                });


    }

    protected void initZKBanner(ZKBanner zkBanner, List<String> bannerData, List<BannerBean> bannerBeans) {
        //设置图片加载器
        zkBanner.setImageLoader(new ZKImageLoader());
        //设置图片集合
        zkBanner.setImages(bannerData);
        //banner设置方法全部调用完毕时最后调用
        zkBanner.start();
        zkBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = bannerBeans.get(position);


                // TODO: 2017/12/18 test data
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String url = bannerBean.getTjUrl();
                //url = "<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861</a></p>";
                url = url.substring(url.lastIndexOf("\">") + 2, url.lastIndexOf("</a>"));
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
//        mHeaderAndFooterWrapper.addHeaderView(zkBanner);
    }

}
