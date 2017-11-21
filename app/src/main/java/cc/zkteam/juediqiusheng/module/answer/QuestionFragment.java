package cc.zkteam.juediqiusheng.module.answer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.fragment.BaseRecyclerViewFragment;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;

/**
 * 问答 Fragment
 */
public class QuestionFragment extends BaseRecyclerViewFragment {

    private static final String TAG = "QuestionFragment";

    /**
     * 当前页面对应 ID
     */
    public static final String CATEGORY_ID = "34425";
    public static final int PAGE_COUNT = 5;

    private int currentPage = 0;

    public static QuestionFragment newInstance() {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_question;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData(false);
    }

    @Override
    public void initListener() {
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                currentPage = 0;
                loadData(false);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                loadData(true);
            }
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {

            SortDetailBean sortDetailBean = (SortDetailBean) adapter.getData().get(position);

            Intent intent = new Intent();
            intent.setClass(mContext, WebViewActivity.class);
            String url = sortDetailBean.getArtifactUrl();
            //url = "<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861</a></p>";
            url = url.substring(url.lastIndexOf("\">") + 2, url.lastIndexOf("</a>"));
            intent.putExtra("url", url);
            startActivity(intent);
        });
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new SortAdapter(null);
    }

    private void loadData(boolean isLoadMore) {
        ZKConnectionManager.getInstance().getZKApi().getSortDetail(CATEGORY_ID, PAGE_COUNT, currentPage++)
                .enqueue(new ZKCallback<List<SortDetailBean>>() {
                    @Override
                    public void onResponse(final List<SortDetailBean> result) {
                        Log.d(TAG, "onResponse() called with: result = [" + result.size() + "]");
                        if (!isLoadMore) {
                            adapter.setNewData(result);
                        } else {
                            adapter.getData().addAll(result);
                            adapter.notifyDataSetChanged();
                        }

                        refreshFinish();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                        refreshFinish();
                    }
                });
    }


}
