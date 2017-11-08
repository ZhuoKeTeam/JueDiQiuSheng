package cc.zkteam.juediqiusheng.strategy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.activity.SortActivity;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.strategy.base.ViewHolder;
import cc.zkteam.juediqiusheng.strategy.wrapper.HeaderAndFooterWrapper;
import cc.zkteam.juediqiusheng.strategy.wrapper.LoadMoreWrapper;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StrategyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StrategyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static final String TAG = "StrategyFragment";

    private ZKBanner zkBanner;
    private ZKRefreshLayout zkRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<CategoryBean> mDatas = new ArrayList<>();
    private CommonAdapter<CategoryBean> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

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


        zkBanner = view.findViewById(R.id.zk_banner);
        zkRefreshLayout = view.findViewById(R.id.zk_refresh_layout);
        mRecyclerView = view.findViewById(R.id.zk_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        initZKBanner(zkBanner);

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
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        initDatas();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();

                intent.setClass(getActivity(), SortActivity.class);
                String id = String.valueOf(mDatas.get(position - 1).getId());
                intent.putExtra("id", id);
                intent.putExtra("name", mDatas.get(position - 1).getCategoryName());

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

    protected void initZKBanner(ZKBanner zkBanner) {
        //设置图片加载器
        zkBanner.setImageLoader(new ZKImageLoader());
        //设置图片集合
        zkBanner.setImages(TestData.getTestPics());
        //banner设置方法全部调用完毕时最后调用
        zkBanner.start();
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
//        ImageView imageView = new ImageView(getActivity());
//        imageView.setImageResource(R.mipmap.ic_launcher);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity().getApplicationContext(), "轮播图", Toast.LENGTH_SHORT).show();
//            }
//        });
//        mHeaderAndFooterWrapper.addHeaderView(imageView);
    }


    /**
     * 演示快速使用测试 Api
     */
    private void initDatas() {
        ZKConnectionManager.getInstance().getZKApi().categoryData(20)
                .enqueue(new ZKCallback<List<CategoryBean>>() {
                    @Override
                    public void onResponse(List<CategoryBean> result) {
                        mDatas.clear();
                        mDatas.addAll(result);
                        mLoadMoreWrapper.notifyDataSetChanged();
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


}
