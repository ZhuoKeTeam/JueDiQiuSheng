package cc.zkteam.juediqiusheng.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cc.zkteam.juediqiusheng.fragment.HotInformationFragment;
import cc.zkteam.juediqiusheng.fragment.SunflowerFragment;
import cc.zkteam.juediqiusheng.module.recommend.JDQSRecommendFragment;

/**
 * Created by WangQing on 2017/10/30.
 */

public class WQSectionsPagerAdapter extends FragmentPagerAdapter {
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
                fragment = HotInformationFragment.newInstance("热门资讯");
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
}
