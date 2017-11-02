package cc.zkteam.juediqiusheng.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKLiveData;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKText;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKViewModule;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.module.recommend.JDQSRecommendFragment;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.strategy.StrategyFragment;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";
    public TextView mTextView;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private TextView mTextMessage;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int itemId = R.id.navigation_recommend;
            switch (position) {
                case 0:
                    itemId = R.id.navigation_recommend;
                    break;
                case 1:
                    itemId = R.id.navigation_game;
                    break;
                case 2:
                    itemId = R.id.navigation_picture;
                    break;
                case 3:
                    itemId = R.id.navigation_question;
                    break;
            }

            navigation.setSelectedItemId(itemId);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recommend:
                    mViewPager.setCurrentItem(0);
                    mTextMessage.setText(R.string.title_recommend);
                    mTextView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_game:
                    mViewPager.setCurrentItem(1);
                    mTextMessage.setText(R.string.title_game);
                    mTextView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_picture:
                    mViewPager.setCurrentItem(2);
                    mTextMessage.setText(R.string.title_picture);
                    mTextView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_question:
                    mViewPager.setCurrentItem(3);
                    mTextMessage.setText("");
//                    mTextMessage.setText(R.string.title_question);
                    mTextView.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mTextView = (TextView) findViewById(R.id.jump);
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

    }

    @Override
    protected void initListener() {

        mViewPager.addOnPageChangeListener(onPageChangeListener);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/module/pic/details")
                        .withString("url", "https://modao.cc/uploads3/images/1361/13616427/raw_1508656162.png")
                        .navigation();
            }
        });
    }

    @Override
    protected void initData() {
        // 演示如何快速使用网络请求
        testRequestApi();

        // 演示 如何使用 LifeComponents
        testLifeComponents(mTextView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    /**
     * 演示快速使用测试 Api
     */
    private void testRequestApi() {
        ZKConnectionManager.getInstance().getZKApi().categoryData()
                .enqueue(new ZKCallback<List<CategoryBean>>() {
                    @Override
                    public void onResponse(List<CategoryBean> result) {
                        Log.d(TAG, "onResponse() called with: resultList = [" + result + "]");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }
                });
        Log.d(TAG, "testRequestApi() called");
    }

    /**
     * ------------------------------------------
     * -----可以通过 Log 查看相关状态------------
     * ------------------------------------------
     *
     * @param textView
     */
    private void testLifeComponents(final TextView textView) {
        // 对 LiveData 进行测试
        LiveData<String> zkLiveData = new ZKLiveData();
//        //这个方法向LiveData中添加观察者，LiveData 则通过 LifecycleOwner 来判断，当前传入的观察者是否是活跃的
//        // 也就是当前 UI 是否可见
        zkLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // update
                // 当 LiveData 中通过 setValue() 修改了数据时，
                // 这里将收到修改后的数据
                Log.d(TAG, "LiveData onChanged() called with: s = [" + s + "]");
            }
        });

        // 对 组件 的生命周期进行测试
        new ZKText(getLifecycle());

        // 对 ZKViewModule 进行测试
        ZKViewModule module = ViewModelProviders.of(this).get(ZKViewModule.class);
        module.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                 update UI
                Log.d(TAG, "WQViewModule onChanged() called with: s = [" + s + "]");
                if (textView != null) {
                    textView.setText(s);
                }
            }
        });
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        private SectionsPagerAdapter mSectionsPagerAdapter;
        private ViewPager mViewPager;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
//            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);

            WQSectionsPagerAdapter mSectionsPagerAdapter = new WQSectionsPagerAdapter(getChildFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) view.findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 1) {
                return    StrategyFragment.newInstance();
            } else if (position == 2) {
                return (Fragment) ARouter.getInstance().build("/modules/pic/main").navigation();
            } else if (position == 3) {
                return new JDQSRecommendFragment();
            } else {
                return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
