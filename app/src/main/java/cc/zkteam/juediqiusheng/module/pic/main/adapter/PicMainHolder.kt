package cc.zkteam.juediqiusheng.module.pic.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import cc.zkteam.juediqiusheng.bean.PicCategoryBean
import cc.zkteam.juediqiusheng.utils.loadUrl
import kotlinx.android.synthetic.main.item_pic_main_list_layout.view.*


/**
 * Created by kutear on 10/29/17.
 */
class PicMainHolder(v: View) : RecyclerView.ViewHolder(v), ICovertData<PicCategoryBean> {
    private val imageView = v.item_pic_main_list_pic

    private val textView = v.item_pic_main_item_category_name

    override fun covertData(dt: PicCategoryBean?) {
        imageView.loadUrl("https://modao.cc/uploads3/images/1361/13616427/raw_1508656162.png")
        textView.text = dt?.picCategoryName
    }
}