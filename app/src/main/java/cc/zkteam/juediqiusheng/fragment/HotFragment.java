package cc.zkteam.juediqiusheng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.adapter.NewsAdapter;
import cc.zkteam.juediqiusheng.view.NetworkImageHolderView;

/**
 * Created by Doraemon on 2017/10/28.
 */

public class HotFragment extends Fragment {
    @BindView(R.id.convenient_banner)
    ConvenientBanner hotBanner;
    @BindView(R.id.rv_hot_news)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private List<String> bannerData;
    private NewsAdapter newsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.setDebug(true);
        unbinder = ButterKnife.bind(this, view);
        getBannerData();
        newsAdapter=new NewsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getBannerData() {
        //TODO 网络获取banner数据
        bannerData = new ArrayList<>();
        bannerData.add("http://imgs.gamersky.com/upimg/2017/201707271436528741.jpg");
        bannerData.add("http://imgs.gamersky.com/upimg/2017/201707270917441268.jpg");
        bannerData.add("http://imgs.gamersky.com/upimg/2017/201707261820365903.jpg");
        bannerData.add("http://imgs.gamersky.com/upimg/2017/201703241052599978.jpg");
        Log.v("TAG", hotBanner.toString());
        hotBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerData).setPageIndicator(new int[]{R.drawable.shape_normal, R.drawable.shape_selected});
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        hotBanner.startTurning(2 * 1000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        hotBanner.stopTurning();
    }
}
