package com.bro.adlib.statisticsAndLogs;

import com.blankj.utilcode.util.LogUtils;

public class TencentSALState extends AbsState {

    @Override
    public void notifyLogOnly(String logInfo) {
        LogUtils.iTag("AD_TENCENT", logInfo);
    }
}
