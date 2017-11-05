package cc.zkteam.juediqiusheng.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;
import cc.zkteam.juediqiusheng.view.ZKRefreshLayout;

/**
 * Created by WangQing on 2017/10/30.
 */

public class HotInformationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    LinearLayoutManager linearLayoutManager;
    ZKRecyclerView zkRecyclerView;
    ZKRefreshLayout zkRefreshLayout;
    ArrayAdapter<String> arrayAdapter;
    SimpleStringRecyclerViewAdapter recyclerViewAdapter;

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

        zkRecyclerView = rootView.findViewById(R.id.zk_recycler_view);
        zkRefreshLayout = rootView.findViewById(R.id.zk_refresh_layout);
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

        zkRefreshLayout.setWaveColor(0x555555);
        zkRefreshLayout.setLoadMore(true);
//        https://github.com/gdky005/Android-MaterialRefreshLayout
        zkRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrayList.addAll(arrayList);
                        recyclerViewAdapter.notifyDataSetChanged();
                        zkRefreshLayout.finishRefresh();
                    }
                }, 3000);

                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrayList.addAll(arrayList);
                        recyclerViewAdapter.notifyDataSetChanged();
                        zkRefreshLayout.finishRefreshLoadMore();
                    }
                }, 3000);
            }
        });



//        https://github.com/recruit-lifestyle/WaveSwipeRefreshLayout  水滴效果
//        zkRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GRAY);
//        zkRefreshLayout.setWaveColor(Color.argb(100,255,0,0));
//        zkRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                zkRefreshLayout.setRefreshing(true);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        arrayAdapter.addAll(arrayList);
//                        arrayAdapter.notifyDataSetChanged();
//
//                        zkRefreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });

//        SwipeRefreshLayout 版本
//        zkRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        // 设置下拉进度的主题颜色
//        zkRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
//        zkRefreshLayout.setProgressViewOffset(true, 0, 200);
//
//
//        zkRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                zkRefreshLayout.setRefreshing(true);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        arrayAdapter.addAll(arrayList);
//                        arrayAdapter.notifyDataSetChanged();
//
//                        zkRefreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });


//        arrayAdapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_list_item_1, arrayList);
//        zkRecyclerView.setAdapter(arrayAdapter);


        linearLayoutManager = new LinearLayoutManager(zkRecyclerView.getContext());
        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        recyclerViewAdapter = new SimpleStringRecyclerViewAdapter(zkRecyclerView.getContext(), arrayList);
        zkRecyclerView.setAdapter(recyclerViewAdapter);
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }



    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {


        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final ImageView imageView;
            public final TextView textView;

            public ViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.pic);
                textView = view.findViewById(R.id.text);
            }


        }

        List list;

        public SimpleStringRecyclerViewAdapter(Context context, List list) {
            super();
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.textView.setText((CharSequence) list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



}
