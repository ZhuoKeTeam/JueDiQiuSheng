package cc.zkteam.juediqiusheng.fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.adapter.WQSectionsPagerAdapter;
import cc.zkteam.juediqiusheng.view.ZKViewPager;
public class WQFragment extends BaseFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ZKViewPager mViewPager;
    private TabLayout tabLayout;
    public WQFragment() {
    }
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
}
