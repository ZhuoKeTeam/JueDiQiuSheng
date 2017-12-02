package cc.zkteam.juediqiusheng.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.TestData;
import cc.zkteam.juediqiusheng.base.mvp.BaseMVPPresenter;
import cc.zkteam.juediqiusheng.base.mvp.BaseMVPView;
import cc.zkteam.juediqiusheng.utils.L;
import cc.zkteam.juediqiusheng.view.ZKBanner;
import cc.zkteam.juediqiusheng.view.ZKImageLoader;
import dagger.android.support.DaggerFragment;

/**
 * Fragment 基类
 * Created by WangQing on 2017/12/2.
 */

public abstract class NewBaseFragment<P extends BaseMVPPresenter> extends DaggerFragment implements BaseMVPView {

    /**
     * Presenter 对象
     */
    @Inject
    protected P presenter;

    /**
     * Context对象，保存当前Activity
     */
    protected AppCompatActivity mContext;

    /**
     * log 标记
     */
    protected static String TAG;

    protected View rootView;

    // 获取布局资源文件
    @LayoutRes
    public abstract int getLayoutId();

    // 初始化布局
    public abstract void initView(View rootView);

    // 初始化数据
    public abstract void initData(Bundle savedInstanceState);

    // 初始化相关 View 的 listener
    public abstract void initListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log标记初始化为当前类名
        TAG = getClass().getSimpleName();
        // Context初始化为当前Fragment所依附Activity对象
        mContext = (AppCompatActivity) getActivity();
        // 进入Fragment时，打印当前Fragment名称
        L.d(TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
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



    protected void initZKBanner(ZKBanner zkBanner) {
        //设置图片加载器
        zkBanner.setImageLoader(new ZKImageLoader());
        //设置图片集合
        zkBanner.setImages(TestData.getTestPics());
        //banner设置方法全部调用完毕时最后调用
        zkBanner.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (presenter != null) {
            presenter.onDetach();
        }
    }
}
