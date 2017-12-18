package cc.zkteam.juediqiusheng.module.pic.main.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import cc.zkteam.juediqiusheng.ZKBase
import cc.zkteam.juediqiusheng.activity.WebViewActivity
import cc.zkteam.juediqiusheng.bean.RecommendedBean
import cc.zkteam.juediqiusheng.utils.loadUrl
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

        imageView.setOnClickListener {
            // TODO: 2017/12/18 test data
            val intent = Intent()
            intent.setClass(ZKBase.getContext(), WebViewActivity::class.java)
            var url = dt?.newsSourceUrl
            //url = "<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968861</a></p>";
            url = url?.substring(url.lastIndexOf("\">") + 2, url.lastIndexOf("</a>"))
            intent.putExtra("url", url)
            ZKBase.getContext().startActivity(intent)
        }
    }
}