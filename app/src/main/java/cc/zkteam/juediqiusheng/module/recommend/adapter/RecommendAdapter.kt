package cc.zkteam.juediqiusheng.module.pic.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.bean.RecommendedBean
import cc.zkteam.juediqiusheng.module.recommend.adapter.RecommendHolderFactory

/**
 * Created by kutear on 10/29/17.
 */
class RecommendAdapter : RecyclerView.Adapter<RecommendHolder>(), ICovertData<List<RecommendedBean>> {
    override fun covertData(dt: List<RecommendedBean>?) {
        dt?.let {
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    private val data = arrayListOf<RecommendedBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        return RecommendHolderFactory.build(viewType, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecommendHolder?, position: Int) {
        holder?.covertData(data[position])
    }
}