package cc.zkteam.juediqiusheng.module.waterfall;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.fresco.helper.ImageLoader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.view.ZKImageView;

/**
 * Created by ztw on 2017/10/27.
 */

public class RecycleAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {

    private final Context mContext;

    private Map<Integer, Integer> randomHeight = new HashMap<>();

    public RecycleAdapter(@LayoutRes int layoutResId, Context mContext) {
        super(layoutResId);
        this.mContext = mContext;
    }

    @Override
    public void setNewData(@Nullable List<ItemBean> data) {
        super.setNewData(data);
        randomHeight.clear();
        randomHeight(data);
    }

    @Override
    public void addData(@NonNull Collection<? extends ItemBean> newData) {
        super.addData(newData);
        randomHeight(newData);
    }

    private void randomHeight(@Nullable Collection<? extends ItemBean> newData) {
        if (newData == null) {
            return;
        }
        Iterator iterator = newData.iterator();
        while (iterator.hasNext()) {
            ItemBean itemBean = (ItemBean) iterator.next();
            int height = new Random().nextInt(200) + 400;//[100,500)的随机数
            randomHeight.put(itemBean.getId(), height);
        }
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ItemBean itemBean) {
        ZKImageView imageView = baseViewHolder.getView(R.id.pic_img);
        int width = ScreenUtils.getScreenWidth() / 2;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = width;
        if(randomHeight.containsKey(itemBean.getId())){
            params.height = randomHeight.get(itemBean.getId());
        }
        imageView.setLayoutParams(params);
        ImageLoader.loadImage(imageView, itemBean.getPicTinyUrl());
    }
}
