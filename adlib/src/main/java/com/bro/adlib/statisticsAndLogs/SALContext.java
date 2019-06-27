package com.bro.adlib.statisticsAndLogs;

public class SALContext {
    //持有一个State类型的对象实例
    private State state;

    public void setState(State state) {
        this.state = state;
    }


    public void requestStatisticsAndLog(String event, String logInfo) {
        //转调state来处理
        state.notifyStatisticsAndLog(event, logInfo);
    }

    public void requestStatisticsOnlu(String event) {
        //转调state来处理
        state.notifyStatisticsOnly(event);
    }

    public void requestLogOnlu(String logInfo) {
        //转调state来处理
        state.notifyLogOnly(logInfo);
    }
}
