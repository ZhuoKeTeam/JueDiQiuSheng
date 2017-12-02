package cc.zkteam.juediqiusheng.ui.fragment.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.base.BaseRecyclerViewFragment;
import cc.zkteam.juediqiusheng.ui.fragment.question.mvp.NewQuestionPresenterImpl;
import cc.zkteam.juediqiusheng.ui.fragment.question.mvp.NewQuestionView;

/**
 * NewQuestionFragment
 * Created by WangQing on 2017/12/2.
 */


public class NewQuestionFragment extends BaseRecyclerViewFragment<NewQuestionPresenterImpl> implements NewQuestionView {

    @Inject
    QuestionViewModel questionViewModel;

    @Inject
    NewQuestionPresenterImpl presenter;


    public static NewQuestionFragment newInstance() {
        NewQuestionFragment fragment = new NewQuestionFragment();
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

        questionViewModel.getQuestionList().observe(this, sortDetailBeans -> {
            adapter.setNewData(sortDetailBeans);
            adapter.notifyDataSetChanged();
        });

        presenter.loadData(false);

    }

    @Override
    public void initListener() {
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.currentPage = 0;
                presenter.loadData(false);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                presenter.loadData(true);
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
