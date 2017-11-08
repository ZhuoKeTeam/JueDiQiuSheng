package cc.zkteam.juediqiusheng.module.answer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.adapter.SortAdapter;
import cc.zkteam.juediqiusheng.base.RvListener;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;

import static cc.zkteam.juediqiusheng.activity.MainActivity.TAG;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rvSort;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ZKConnectionManager.getInstance().getZKApi().getSortDetail("34425", "100")
                .enqueue(new ZKCallback<List<SortDetailBean>>() {
                    @Override
                    public void onResponse(final List<SortDetailBean> result) {

                        SortAdapter sortAdapter = new SortAdapter(mContext, result, new RvListener() {
                            @Override
                            public void onItemClick(int id, int position) {
                                Intent intent = new Intent();
                                intent.setClass(mContext, WebViewActivity.class);

                                String url = result.get(position).getArtifactUrl();
//                                url = "<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861</a></p>";
                                url = url.substring(url.lastIndexOf("\">") + 2, url.lastIndexOf("</a>"));
                                intent.putExtra("url", url);
                                startActivity(intent);

                            }
                        });
                        rvSort.setLayoutManager(new LinearLayoutManager(mContext));
                        rvSort.setAdapter(sortAdapter);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }
                });
    }

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_question, container, false);
        rvSort = inflate.findViewById(R.id.rv_sort);

        return inflate;
    }

    public QuestionFragment() {

    }

}
