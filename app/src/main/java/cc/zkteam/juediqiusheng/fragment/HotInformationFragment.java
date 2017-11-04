package cc.zkteam.juediqiusheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by WangQing on 2017/10/30.
 */

public class HotInformationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ListView listView;
    ZKRefreshLayout refreshLayout;
    ArrayAdapter<String> arrayAdapter;

    public HotInformationFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HotInformationFragment newInstance(String text) {
        HotInformationFragment fragment = new HotInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rec_hot_infor, container, false);
        TextView textView = rootView.findViewById(R.id.text);

        listView = rootView.findViewById(R.id.listView);
        refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        textView.setText(getString(R.string.section_format, getArguments().getString(ARG_SECTION_NUMBER)));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] list= new String[] {
                "one", "two", "three", "four", "five",
        };

        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            arrayList.add(list[i % 5] + "-" + i);
        }



//        https://github.com/recruit-lifestyle/WaveSwipeRefreshLayout  水滴效果
//        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GRAY);
//        refreshLayout.setWaveColor(Color.argb(100,255,0,0));
        refreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrayAdapter.addAll(arrayList);
                        arrayAdapter.notifyDataSetChanged();

                        refreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

//        SwipeRefreshLayout 版本
//        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        // 设置下拉进度的主题颜色
//        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
//        refreshLayout.setProgressViewOffset(true, 0, 200);
//
//
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshLayout.setRefreshing(true);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        arrayAdapter.addAll(arrayList);
//                        arrayAdapter.notifyDataSetChanged();
//
//                        refreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });


        arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
