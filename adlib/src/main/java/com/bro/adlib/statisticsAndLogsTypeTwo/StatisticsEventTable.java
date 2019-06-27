package com.bro.adlib.statisticsAndLogsTypeTwo;

import java.util.HashMap;
import java.util.Map;

import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADCLICKED;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADCLOSED;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADCLOSEOVERLAY;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADEXPOSURE;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADLEFTAPPLICATION;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADOPENOVERLAY;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_ADRECEIVE;
import static com.bro.adlib.util.UMUtils.EVENT_TENCENT_BANNER_NOAD;

/**
 * 统计事件表
 */
public class StatisticsEventTable {
    private static StatisticsEventTable sInstance = new StatisticsEventTable();
    private Map<String, String> mStatisticsEventTable = new HashMap<>();

    private StatisticsEventTable() {
        //  TODO 这里维护这个统计事件表比较麻烦，看看是否能否自动化来处理
        //  这里只是为了展示方法思路，只写了一小部分
        mStatisticsEventTable.put("UnifiedBannerADListener->onNoAD", EVENT_TENCENT_BANNER_NOAD);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADReceive", EVENT_TENCENT_BANNER_ADRECEIVE);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADExposure", EVENT_TENCENT_BANNER_ADEXPOSURE);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADClosed", EVENT_TENCENT_BANNER_ADCLOSED);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADClicked", EVENT_TENCENT_BANNER_ADCLICKED);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADLeftApplication", EVENT_TENCENT_BANNER_ADLEFTAPPLICATION);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADOpenOverlay", EVENT_TENCENT_BANNER_ADOPENOVERLAY);
        mStatisticsEventTable.put("UnifiedBannerADListener->onADCloseOverlay", EVENT_TENCENT_BANNER_ADCLOSEOVERLAY);
    }

    public static StatisticsEventTable getsInstance() {
        return sInstance;
    }

    public String getEventByKey(String key) {
        return mStatisticsEventTable.get(key);
    }
}
