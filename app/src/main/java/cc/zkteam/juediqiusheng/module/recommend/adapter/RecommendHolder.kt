package cc.zkteam.juediqiusheng.module.pic.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import cc.zkteam.juediqiusheng.bean.RecommendedBean
import cc.zkteam.juediqiusheng.utils.loadUrl
import kotlinx.android.synthetic.main.item_pic_main_list_layout.view.*
import kotlinx.android.synthetic.main.item_recommendation.view.*


/**
 * Created by kutear on 10/29/17.
 */
class RecommendHolder(v: View) : RecyclerView.ViewHolder(v), ICovertData<RecommendedBean> {
    private val imageView = v.item_recommendation_pic

    private val textView = v.item_recommendation_content

    override fun covertData(dt: RecommendedBean?) {
        imageView.loadUrl(dt?.picUrl)
        textView.text = dt?.context
    }
}