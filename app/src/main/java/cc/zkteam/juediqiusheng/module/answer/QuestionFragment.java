package cc.zkteam.juediqiusheng.module.answer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import javax.inject.Inject;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.fragment.BaseRecyclerViewFragment;
import cc.zkteam.juediqiusheng.module.answer.mvp.QFPresenterImpl;
import cc.zkteam.juediqiusheng.module.answer.mvp.QFView;
import dagger.android.support.AndroidSupportInjection;
public class QuestionFragment extends BaseRecyclerViewFragment implements QFView, View.OnClickListener {
    LinearLayout llAbout;
    @Inject
    QFPresenterImpl presenter;
    @Inject
    QuestionViewModel questionViewModel;
    public static QuestionFragment newInstance() {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_question;
    }
    @Override
    public void initView(View rootView) {
        llAbout = rootView.findViewById(R.id.ll_about);
    }
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
    @Override
    public void initData(Bundle savedInstanceState) {
        questionViewModel.getQuestionList().observe(this, sortDetailBeans -> {
            adapter.setNewData(sortDetailBeans);
            adapter.notifyDataSetChanged();
        });
        presenter.currentPage = 0;
        presenter.loadData(false);
    }
    @Override
    public void initListener() {
        llAbout.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_about:
                String url = "file:///android_asset/about_rules.html";
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
                break;
        }
    }
}
