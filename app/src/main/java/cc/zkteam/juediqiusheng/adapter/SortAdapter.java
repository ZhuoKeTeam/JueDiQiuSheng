package cc.zkteam.juediqiusheng.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.utils.ZKImageViewExtKt;

/**
 * Created by wustor
 * 日期  2017-10-30.
 * 邮箱  fat_chao@163.com
 */

public class SortAdapter extends BaseQuickAdapter<SortDetailBean, BaseViewHolder> {

    public SortAdapter(@Nullable List<SortDetailBean> data) {
        super(R.layout.item_sort_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortDetailBean item) {
//        helper.setText(R.id.text, item.getTitle());
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));

        Date date = TimestampToDate(item.getArtifactDate());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");

        helper.setText(R.id.tv_content, item.getArtifactName());
        helper.setText(R.id.tv_time, sf.format(date));

        ZKImageViewExtKt.loadUrl(helper.getView(R.id.iv_sort), item.getPicUrl(), R.mipmap.ic_launcher);
    }


    public static Date TimestampToDate(Integer time) {
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
            //System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
