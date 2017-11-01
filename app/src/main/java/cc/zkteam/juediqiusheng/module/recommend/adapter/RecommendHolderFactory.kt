package cc.zkteam.juediqiusheng.module.recommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.module.pic.main.adapter.RecommendHolder

/**
 * Created by kutear on 10/29/17.
 */
class RecommendHolderFactory {
    companion object {
        fun build(type: Int, parent: ViewGroup): RecommendHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
            return RecommendHolder(itemView)
        }
    }
}