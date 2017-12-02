package cc.zkteam.juediqiusheng.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import dagger.android.support.DaggerFragment;

/**
 * BaseFragment
 * Created by WangQing on 2017/11/7.
 */

public abstract class OldBaseFragment extends DaggerFragment {

    protected View rootView;
    //context
    protected Context mContext = null;

    // 获取布局资源文件
    public abstract @LayoutRes int getLayoutId();

    // 初始化布局
    public abstract void initView(View rootView);

    // 初始化数据
    public abstract void initData(Bundle savedInstanceState);

    // 初始化相关 View 的 listener
    public abstract void initListener();

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
        //设置图片加载器
        zkBanner.setImageLoader(new ZKImageLoader());
        //设置图片集合
        zkBanner.setImages(TestData.getTestPics());
        //banner设置方法全部调用完毕时最后调用
        zkBanner.start();
    }
}
