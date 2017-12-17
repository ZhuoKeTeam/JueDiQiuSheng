package cc.zkteam.juediqiusheng.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cc.zkteam.juediqiusheng.fragment.HotInformationFragment;
import cc.zkteam.juediqiusheng.fragment.RecommendFragment;
import cc.zkteam.juediqiusheng.fragment.SunflowerFragment;
import cc.zkteam.juediqiusheng.module.recommend.JDQSRecommendFragment;

/**
 * Created by WangQing on 2017/10/30.
 */

public class WQSectionsPagerAdapter extends FragmentPagerAdapter {
    private String mTabs[] = new String[]{"热门资讯", "精品攻略推荐", "葵花宝典"};

    public WQSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return WQFragment.newInstance(position + 1);

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = RecommendFragment.newInstance();
                break;
            case 1:
                fragment = new JDQSRecommendFragment();
                break;
            case 2:
                fragment = SunflowerFragment.newInstance("葵花宝典");
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }
}
