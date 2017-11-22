package cc.zkteam.juediqiusheng.module.answer;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cc.zkteam.juediqiusheng.bean.SortDetailBean;

/**
 * QuestionViewModel
 * Created by WangQing on 2017/11/21.
 */

public class QuestionViewModel extends ViewModel{

    private MutableLiveData<List<SortDetailBean>> questionList;

    public MutableLiveData<List<SortDetailBean>> getQuestionList() {
        if (questionList == null) {
            questionList = new MutableLiveData<>();
        }
        return questionList;
    }


}
