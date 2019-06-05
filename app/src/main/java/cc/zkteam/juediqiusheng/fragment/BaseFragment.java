package cc.zkteam.juediqiusheng.fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Context mContext = null;
    public abstract @LayoutRes
    int getLayoutId();
    public abstract void initView(View rootView);
    public abstract void initData(Bundle savedInstanceState);
    public abstract void initListener();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initView(rootView);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData(savedInstanceState);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }
    protected void initZKBanner(ZKBanner zkBanner) {
        zkBanner.setImageLoader(new ZKImageLoader());
        zkBanner.setImages(TestData.getTestPics());
        zkBanner.start();
    }
    protected void initZKBanner(ZKBanner zkBanner, List<String> bannerData, List<String> titles) {
        zkBanner.setImageLoader(new ZKImageLoader());
        zkBanner.setImages(bannerData);
        zkBanner.setBannerTitles(titles);
        zkBanner.start();
    }
}
