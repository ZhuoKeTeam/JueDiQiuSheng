package cc.zkteam.juediqiusheng.base.mvp;

/**
 * MVP View 基类
 * Created by WangQing on 2017/12/2.
 */

public interface BaseMVPView {

    /**
     * 加载中
     */
    void onLoading();

    /**
     * 无数据
     */
    void onNoData();

    /**
     * 网络请求结束
     */
    void onNetFinished();
}
