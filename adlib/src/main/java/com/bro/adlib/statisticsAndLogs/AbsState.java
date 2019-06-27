package com.bro.adlib.statisticsAndLogs;

import com.bro.adlib.util.UMUtils;

public abstract class AbsState implements State {

    @Override
    public void notifyStatisticsOnly(String event) {
        UMUtils.event(event);
    }

    @Override
    public void notifyStatisticsAndLog(String event, String logInfo) {
        notifyStatisticsOnly(event);
        notifyLogOnly(logInfo);
    }
}
