package cc.zkteam.juediqiusheng.view.recyclerview.viewholder;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 10:58 PM
 */
public class ZKRvItemViewHolderEmpty extends ZKRvItemViewHolderBase {

    private ZKRvItemViewHolderEmpty(ViewGroup parent, int orientation) {

        super(getEmptyView(parent, orientation));
    }

    public static ZKRvItemViewHolderEmpty newHoriInstance(ViewGroup parent) {

        return new ZKRvItemViewHolderEmpty(parent, OrientationHelper.HORIZONTAL);
    }

    public static ZKRvItemViewHolderEmpty newVertInstance(ViewGroup parent) {

        return new ZKRvItemViewHolderEmpty(parent, OrientationHelper.VERTICAL);
    }

    private static View getEmptyView(ViewGroup parent, int orientation) {

        View view = new View(parent.getContext());

        //0表示水平朝向，1表示垂直朝向
//        if(orientation == OrientationHelper.VERTICAL)
//            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 100));
//        else
//            view.setLayoutParams(new RecyclerView.LayoutParams(50, RecyclerView.LayoutParams.WRAP_CONTENT));
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return view;
    }

    @Override
    protected void initConvertView(View convertView) {

        //nothing
    }
}
