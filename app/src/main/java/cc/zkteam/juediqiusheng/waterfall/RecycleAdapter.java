package cc.zkteam.juediqiusheng.waterfall;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cc.zkteam.juediqiusheng.R;

/**
 * Created by ztw on 2017/10/27.
 */

public class RecycleAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {

    private final Context mContext;

    public RecycleAdapter(@LayoutRes int layoutResId, Context mContext) {
        super(layoutResId);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ItemBean itemBean) {
        ImageView imageView = baseViewHolder.getView(R.id.pic_img);
        int width = ((Activity) imageView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        //设置图片的相对于屏幕的宽高比
        params.width = width / 3;
        params.height = (int) (200 + Math.random() * 400);
        imageView.setLayoutParams(params);
        ImageUtils.load(mContext, itemBean.getImgUrl(), imageView);
    }
}
