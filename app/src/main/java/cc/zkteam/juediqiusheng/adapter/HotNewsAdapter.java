package cc.zkteam.juediqiusheng.adapter;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bro.adlib.ad.ZKTencentAD;
import com.bro.adlib.strategy.ZKContext;
import com.bro.adlib.listener.ZKNativeListener;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.HotNewsBean;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.view.ZKRecyclerView;

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

    public static int FIRST_AD_POSITION = 1; // 第一条广告的位置
    public static int ITEMS_PER_AD = 2;     // 每间隔10个条目插入一条广告
    public static final int AD_COUNT = 1;    // 加载广告的条数，取值范围为[1, 10]
    private List<NativeExpressADView> mAdViewList;
    private List<Object> mNormalDataList = new ArrayList<>();
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<NativeExpressADView, Integer>();

    /**
     *
     */
    private void initNativeExpressAD() {
        ZKContext zkContext = new ZKContext(ZKTencentAD.getInstance());
        zkContext.initNativeExpressAD(activity.getApplicationContext(), 3, new ZKNativeListener() {
            @Override
            public void onNativeCallBack(List adList) {
                if(!adList.isEmpty() && adList.get(0) instanceof NativeExpressADView){
                    mAdViewList = adList;
                    for (int i = 0; i < mAdViewList.size(); i++) {
                        int position = FIRST_AD_POSITION + ITEMS_PER_AD * i;
                        if (position < mNormalDataList.size()) {
                            NativeExpressADView view = mAdViewList.get(i);
//                            GDTLogger.i("ad load[" + i + "]: " + getAdInfo(view));
//                            if (view.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
//                                view.setMediaListener(mediaListener);
//                            }
                            mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                            hotNewsItemAdapter.addADViewToPosition(position, mAdViewList.get(i));
                        }
                    }
                    hotNewsItemAdapter.notifyDataSetChanged();
                }

            }
        });
    }

}
