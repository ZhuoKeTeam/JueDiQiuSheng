package cc.zkteam.juediqiusheng.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.base.RvHolder;
import cc.zkteam.juediqiusheng.base.RvListener;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.utils.ZKImageViewExtKt;
import cc.zkteam.juediqiusheng.view.ZKImageView;

/**
 * Created by fatchao
 * 日期  2017-10-30.
 * 邮箱  fat_chao@163.com
 */

public class SortHolder extends RvHolder<SortDetailBean> {
    private ZKImageView ivSort;
    private TextView tvContent, tvTime;

    public SortHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ivSort = itemView.findViewById(R.id.iv_sort);
        tvContent = itemView.findViewById(R.id.tv_content);
        tvTime = itemView.findViewById(R.id.tv_time);

    }

    @Override
    public void bindHolder(SortDetailBean sortDetailBean, int position) {

        String picUrl = sortDetailBean.getPicUrl();

        if (!TextUtils.isEmpty(picUrl))
            ZKImageViewExtKt.loadUrl(ivSort, sortDetailBean.getPicUrl(), R.mipmap.ic_launcher);
        tvContent.setText(sortDetailBean.getArtifactName());
        Date date = TimestampToDate(sortDetailBean.getArtifactDate());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        tvTime.setText(sf.format(date));

    }

    public static Date TimestampToDate(Integer time){
        long temp = (long)time*1000;
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
