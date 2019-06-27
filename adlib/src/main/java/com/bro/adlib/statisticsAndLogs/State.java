package com.bro.adlib.statisticsAndLogs;

public interface State {

    void notifyStatisticsOnly(String event);

    void notifyLogOnly(String logInfo);

    void notifyStatisticsAndLog(String statisticsTag, String logInfo);
}
