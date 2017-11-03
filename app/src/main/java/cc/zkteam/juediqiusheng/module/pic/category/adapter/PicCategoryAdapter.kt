package cc.zkteam.juediqiusheng.module.pic.category.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.bean.PicBean
import cc.zkteam.juediqiusheng.module.pic.main.adapter.ICovertData

/**
 * Created by kutear on 2017/11/1.
 */
class PicCategoryAdapter : RecyclerView.Adapter<PicCategoryHolder>(), ICovertData<List<PicBean>> {
    private val data = mutableListOf<PicBean>()

    override fun onBindViewHolder(holder: PicCategoryHolder?, position: Int) {
        holder?.covertData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PicCategoryHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_pic_main_list_layout, parent, false)
        return PicCategoryHolder(itemView)
    }

    override fun covertData(dt: List<PicBean>?) {
        dt?.let {
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

}