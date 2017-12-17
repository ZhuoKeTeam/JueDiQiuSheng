package cc.zkteam.juediqiusheng.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.adapter.WQSectionsPagerAdapter;
import cc.zkteam.juediqiusheng.ui.main.MainActivity;
import cc.zkteam.juediqiusheng.view.ZKViewPager;

/**
 * Created by WangQing on 2017/10/30.
 */

public class WQFragment extends BaseFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ZKViewPager mViewPager;
    private TabLayout tabLayout;

    public WQFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WQFragment newInstance(int sectionNumber) {
        WQFragment fragment = new WQFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.container);
        tabLayout=rootView.findViewById(R.id.tabs);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mViewPager.setLifecycle(getLifecycle());
        mViewPager.setViewPager(null, new WQSectionsPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initListener() {

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        mViewPager = rootView.findViewById(R.id.container);
//        mViewPager.setLifecycle(getLifecycle());
//        mViewPager.setViewPager(null, new WQSectionsPagerAdapter(getChildFragmentManager()));
//        return rootView;
//    }
}
