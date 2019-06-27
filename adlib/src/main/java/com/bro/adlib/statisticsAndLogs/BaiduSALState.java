package com.bro.adlib.statisticsAndLogs;

import com.blankj.utilcode.util.LogUtils;

public class BaiduSALState extends AbsState{

    @Override
    public void notifyLogOnly(String logInfo) {
        LogUtils.iTag("AD_BAIDU", logInfo);
    }
}
