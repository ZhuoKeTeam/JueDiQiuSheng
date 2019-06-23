package cc.zkteam.juediqiusheng.view.recyclerview.viewholder;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * header footer 基类
 * <p>
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:08 PM
 */
public abstract class ZKRvItemViewHolderLinearBase extends ZKRvItemViewHolderBase {

    public ZKRvItemViewHolderLinearBase(Context context, int orientation) {

        super(newContentView(context, orientation));
    }

    @Override
    protected void initConvertView(View convertView) {

        //nothing
    }

    @Override
    public LinearLayout getConvertView() {

        return (LinearLayout) super.getConvertView();
    }

    public void addView(View view, int index) {

        getConvertView().addView(view, index);
    }

    public int getChildCount() {

        return getConvertView().getChildCount();
    }

    public void setPaddingTop(int padding) {

        LinearLayout contentView = getConvertView();
        contentView.setPadding(contentView.getPaddingLeft(), padding,
                contentView.getPaddingRight(), contentView.getPaddingBottom());
    }

    public void setPaddingBottom(int padding) {

        LinearLayout contentView = getConvertView();
        contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(),
                contentView.getPaddingRight(), padding);
    }

    private static LinearLayout newContentView(Context context, int orientation) {

        LinearLayout llContent = new LinearLayout(context);
        llContent.setOrientation(orientation == OrientationHelper.VERTICAL ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        llContent.setLayoutParams(getRvlp(orientation));
        return llContent;
    }

    private static RecyclerView.LayoutParams getRvlp(int orientation) {

        if (orientation == OrientationHelper.VERTICAL)
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        else
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT);
    }
}
