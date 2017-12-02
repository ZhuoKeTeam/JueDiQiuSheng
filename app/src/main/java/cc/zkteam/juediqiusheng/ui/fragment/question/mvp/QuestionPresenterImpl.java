package cc.zkteam.juediqiusheng.ui.fragment.question.mvp;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.base.mvp.BaseMVPPresenter;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.ui.fragment.question.QuestionViewModel;

/**
 * Created by WangQing on 2017/12/2.
 */

public class QuestionPresenterImpl extends BaseMVPPresenter<QuestionView, QuestionModule>
        implements QuestionPresenter {

    private static final String TAG = "NewQuestionPresenterImp";

    /**
     * 当前页面对应 ID
     */
    public static final String CATEGORY_ID = "34425";
    public static final int PAGE_COUNT = 5;

    public int currentPage = 0;

    QuestionView view;
    QuestionViewModel questionViewModel;


    @Inject
    public QuestionPresenterImpl(QuestionView view, QuestionViewModel questionViewModel) {
        super();
        this.view = view;
        this.questionViewModel = questionViewModel;
    }


    @Override
    public void loadData(boolean isLoadMore) {
        ZKConnectionManager.getInstance().getZKApi().getSortDetail(CATEGORY_ID, PAGE_COUNT, currentPage++)
                .enqueue(new ZKCallback<List<SortDetailBean>>() {
                    @Override
                    public void onResponse(final List<SortDetailBean> result) {
                        Log.d(TAG, "onResponse() called with: result = [" + result.size() + "]");

                        List list = questionViewModel.getQuestionList().getValue();

                        if (!isLoadMore) {
                            list = result;
                        } else {
                            list.addAll(result);
                        }

                        questionViewModel.getQuestionList().setValue(list);

                        view.requestFinish();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                        view.requestFinish();
                    }
                });
    }
}
