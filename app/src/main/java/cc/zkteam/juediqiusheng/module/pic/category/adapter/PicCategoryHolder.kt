package cc.zkteam.juediqiusheng.module.pic.category.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import cc.zkteam.juediqiusheng.bean.PicBean
import cc.zkteam.juediqiusheng.module.pic.main.adapter.ICovertData
import cc.zkteam.juediqiusheng.utils.loadUrl
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.item_pic_main_list_layout.view.*

/**
 * Created by kutear on 2017/11/1.
 */
class PicCategoryHolder(v: View) : RecyclerView.ViewHolder(v), ICovertData<PicBean> {
    private val imageView = v.item_pic_main_list_pic

    private val textView = v.item_pic_main_item_category_name

    override fun covertData(dt: PicBean?) {
        imageView.loadUrl(dt?.picSmallUrl)
        textView.text = ""
        itemView.setOnClickListener {
            ARouter.getInstance().build("/module/pic/details").withString("url", dt?.picUrl).navigation()
        }
    }

}