package cc.zkteam.juediqiusheng.view.recyclerview.viewholder;

import static cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderConstant.ITEM_VIEW_TYPE_FOOTER;
import static cc.zkteam.juediqiusheng.view.recyclerview.viewholder.ZKRvItemViewHolderConstant.ITEM_VIEW_TYPE_HEADER;

/**
 * Create By DaYin(gaoyin_vip@126.com) on 2019/6/23 11:08 PM
 */
public class ZKRvItemViewHolderUtil {

    public static boolean isHeaderOrFooter(ZKRvItemViewHolderBase viewHolder) {

        return isHeader(viewHolder) || isFooter(viewHolder);
    }

    public static boolean isHeader(ZKRvItemViewHolderBase viewHolder) {

        if (viewHolder == null)
            return false;
        else
            return viewHolder.getItemViewType() == ITEM_VIEW_TYPE_HEADER;
    }

    public static boolean isFooter(ZKRvItemViewHolderBase viewHolder) {

        if (viewHolder == null)
            return false;
        else
            return viewHolder.getItemViewType() == ITEM_VIEW_TYPE_FOOTER;
    }
}
