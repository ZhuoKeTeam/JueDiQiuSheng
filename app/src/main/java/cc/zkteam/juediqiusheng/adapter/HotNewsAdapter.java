package cc.zkteam.juediqiusheng.adapter;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.pi.AdData;
import com.qq.e.comm.util.AdError;
import com.qq.e.comm.util.GDTLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.HotNewsBean;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;

import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TENCENT_APP_ID;
import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TENCENT_ORIGINAL_KEY;

/**
 * HotNewsAdapter
 * Created by Doraemon123 on 2017/12/17.
 */
public class HotNewsAdapter
        extends RecyclerView.Adapter<HotNewsAdapter.ViewHolder> {

    public List<HotNewsBean> list;


    private onItemClickListener listener;
    Activity activity;

    public HotNewsAdapter(Activity activity, List list) {
        this.list = list;
        this.activity = activity;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hot_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object obj = list.get(position);
        if (obj == null)
            return;

        if (obj instanceof HotNewsBean) {
            HotNewsBean hotNewsBean = list.get(position);
            holder.tvTitle.setText(hotNewsBean.getTitle());
            initZKRecyclerView(holder.zkRecyclerView, hotNewsBean.getSortDetailBeans());

        } else if (obj instanceof String) {
//            holder.textView.setText((CharSequence) obj);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ZKRecyclerView zkRecyclerView;
        public final TextView tvTitle;


        public View contentView;


        public ViewHolder(View view) {
            super(view);
            this.contentView = view;
            zkRecyclerView = view.findViewById(R.id.zk_recycler_view);
            tvTitle = view.findViewById(R.id.tv_title);
        }

    }

    public interface onItemClickListener {
        void onItemClicked(RecommendedBean data);
    }

    HotNewsItemAdapter hotNewsItemAdapter;

    protected void initZKRecyclerView(ZKRecyclerView zkRecyclerView, List arrayList) {
        initNativeExpressAD();
        mNormalDataList.clear();
        mNormalDataList.addAll(arrayList);
        hotNewsItemAdapter = new HotNewsItemAdapter(arrayList);
        hotNewsItemAdapter.setListener(listener);
        hotNewsItemAdapter.setADViewPositionMap(mAdViewPositionMap);
        zkRecyclerView.setLayoutManager(new LinearLayoutManager(zkRecyclerView.getContext()));
        zkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        zkRecyclerView.setAdapter(hotNewsItemAdapter);
    }

    private NativeExpressAD mADManager;
    public static int FIRST_AD_POSITION = 1; // 第一条广告的位置
    public static int ITEMS_PER_AD = 2;     // 每间隔10个条目插入一条广告
    public static final int AD_COUNT = 1;    // 加载广告的条数，取值范围为[1, 10]
    private List<NativeExpressADView> mAdViewList;
    private List<Object> mNormalDataList = new ArrayList<Object>();
    private static final String TAG = HotNewsAdapter.class.getSimpleName();
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<NativeExpressADView, Integer>();

    /**
     *
     */
    private void initNativeExpressAD() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        mADManager = new NativeExpressAD(activity, adSize, AD_TENCENT_APP_ID, AD_TENCENT_ORIGINAL_KEY, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> adList) {
                Log.i(TAG, "onADLoaded: " + adList.size());
                mAdViewList = adList;
                for (int i = 0; i < mAdViewList.size(); i++) {
                    int position = FIRST_AD_POSITION + ITEMS_PER_AD * i;
                    if (position < mNormalDataList.size()) {
                        NativeExpressADView view = mAdViewList.get(i);
                        GDTLogger.i("ad load[" + i + "]: " + getAdInfo(view));
                        if (view.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                            view.setMediaListener(mediaListener);
                        }
                        mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                        hotNewsItemAdapter.addADViewToPosition(position, mAdViewList.get(i));
                    }
                }
                hotNewsItemAdapter.notifyDataSetChanged();
            }


            @Override
            public void onRenderFail(NativeExpressADView adView) {
                Log.i(TAG, "onRenderFail: " + adView.toString());
            }

            @Override
            public void onRenderSuccess(NativeExpressADView adView) {
                Log.i(TAG, "onRenderSuccess: " + adView.toString() + ", adInfo: " + getAdInfo(adView));
            }

            @Override
            public void onADExposure(NativeExpressADView adView) {
                Log.i(TAG, "onADExposure: " + adView.toString());
            }

            @Override
            public void onADClicked(NativeExpressADView adView) {
                Log.i(TAG, "onADClicked: " + adView.toString());
            }

            @Override
            public void onADClosed(NativeExpressADView adView) {
                Log.i(TAG, "onADClosed: " + adView.toString());
                if (hotNewsItemAdapter != null) {
                    int removedPosition = mAdViewPositionMap.get(adView);
                    hotNewsItemAdapter.removeADView(removedPosition, adView);
                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView adView) {
                Log.i(TAG, "onADLeftApplication: " + adView.toString());
            }

            @Override
            public void onADOpenOverlay(NativeExpressADView adView) {
                Log.i(TAG, "onADOpenOverlay: " + adView.toString());
            }

            @Override
            public void onADCloseOverlay(NativeExpressADView adView) {
                Log.i(TAG, "onADCloseOverlay");
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.i(TAG, String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                        adError.getErrorMsg()));
            }
        });
        mADManager.setMaxVideoDuration(30000);
        mADManager.loadAD(AD_COUNT);
    }

    private String getAdInfo(NativeExpressADView nativeExpressADView) {
        AdData adData = nativeExpressADView.getBoundData();
        if (adData != null) {
            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("title:").append(adData.getTitle()).append(",")
                    .append("desc:").append(adData.getDesc()).append(",")
                    .append("patternType:").append(adData.getAdPatternType());
            if (adData.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                infoBuilder.append(", video info: ")
                        .append(getVideoInfo(adData.getProperty(AdData.VideoPlayer.class)));
            }
            return infoBuilder.toString();
        }
        return null;
    }

    private String getVideoInfo(AdData.VideoPlayer videoPlayer) {
        if (videoPlayer != null) {
            StringBuilder videoBuilder = new StringBuilder();
            videoBuilder.append("state:").append(videoPlayer.getVideoState()).append(",")
                    .append("duration:").append(videoPlayer.getDuration()).append(",")
                    .append("position:").append(videoPlayer.getCurrentPosition());
            return videoBuilder.toString();
        }
        return null;
    }

    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoInit: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoLoading(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoLoading: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
            Log.i(TAG, "onVideoReady: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoStart: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPause: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoComplete: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
            Log.i(TAG, "onVideoError");
        }

        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageOpen");
        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageClose");
        }
    };

}
