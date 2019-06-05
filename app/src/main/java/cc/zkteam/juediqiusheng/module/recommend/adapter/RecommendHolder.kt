package cc.zkteam.juediqiusheng.module.pic.main.adapter
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.widget.RecyclerView
import android.view.View
import cc.zkteam.juediqiusheng.ZKBase
import cc.zkteam.juediqiusheng.activity.WebViewActivity
import cc.zkteam.juediqiusheng.bean.RecommendedBean
import cc.zkteam.juediqiusheng.utils.loadUrl
import kotlinx.android.synthetic.main.item_recommendation.view.*
class RecommendHolder(v: View) : RecyclerView.ViewHolder(v), ICovertData<RecommendedBean> {
    private val imageView = v.item_recommendation_pic
    private val textView = v.item_recommendation_content
    override fun covertData(dt: RecommendedBean?) {
        imageView.loadUrl(dt?.picUrl)
        textView.text = dt?.context
        imageView.setOnClickListener {
            val intent = Intent()
            intent.setClass(ZKBase.getContext(), WebViewActivity::class.java)
            var url = dt?.tjSourceUrl
            url = url?.substring(url.lastIndexOf("\">") + 2, url.lastIndexOf("</a>"))
            intent.putExtra("url", url)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            ZKBase.getContext().startActivity(intent)
        }
    }
}