package cc.zkteam.juediqiusheng.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.strategy.CommonAdapter;
import cc.zkteam.juediqiusheng.strategy.base.ViewHolder;
import cc.zkteam.juediqiusheng.strategy.wrapper.HeaderAndFooterWrapper;
import cc.zkteam.juediqiusheng.strategy.wrapper.LoadMoreWrapper;
import cc.zkteam.juediqiusheng.view.ZKImageView;
public class SunflowerFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView mRecyclerView;
    private List<RecommendedBean> mDatas = new ArrayList<>();
    private CommonAdapter<RecommendedBean> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    public SunflowerFragment() {
    }
    public static SunflowerFragment newInstance(String text) {
        SunflowerFragment fragment = new SunflowerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rec_sunflower, container, false);
        mRecyclerView = rootView.findViewById(R.id.sunflower_main_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mAdapter = new CommonAdapter<RecommendedBean>(getActivity(), R.layout.list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, RecommendedBean r, int position) {
                holder.setText(R.id.text, r.getContext());
                ZKImageView zkImageView = holder.getView(R.id.pic);
                Log.e("----", r.getPicUrl());
                if (TextUtils.isEmpty(r.getPicUrl())) {
                    zkImageView.setImageResource(R.drawable.bg);
                } else {
                    zkImageView.setImageURI(r.getPicUrl());
                }
            }
        };
        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        initDatas();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                String artifactUrl = mDatas.get(position).getTjSourceUrl();
                String url = artifactUrl.substring(artifactUrl.lastIndexOf("\">") + 2, artifactUrl.lastIndexOf("</a>"));
                intent.putExtra("url", url);
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return rootView;
    }
    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.ic_default);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "轮播图", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initDatas() {
        ZKConnectionManager.getInstance().getZKApi().getRecommended("10002", "20")
                .enqueue(new ZKCallback<List<RecommendedBean>>() {
                    @Override
                    public void onResponse(List<RecommendedBean> result) {
                        mDatas.clear();
                        mDatas.addAll(result);
                        mLoadMoreWrapper.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                });
    }
}
