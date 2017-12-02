package cc.zkteam.juediqiusheng.base.mvp;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.api.ZKApi;

/**
 * MVP Module 基类
 * Created by WangQing on 2017/12/2.
 */

public class BaseMVPModule {

    @Inject
    protected ZKApi zkApi;

    // TODO: 2017/12/2  有什么用？？？
    @Inject
    protected BaseMVPModule() {

    }
}
