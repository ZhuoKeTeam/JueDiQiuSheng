package cc.zkteam.juediqiusheng.module.pic.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.bean.PicCategoryBean

/**
 * Created by kutear on 10/29/17.
 */
class PicMainAdapter : RecyclerView.Adapter<PicMainHolder>(), ICovertData<List<PicCategoryBean>> {
    override fun covertData(dt: List<PicCategoryBean>?) {
        dt?.let {
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    private val data = arrayListOf<PicCategoryBean>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicMainHolder {
        return PicMainHolderFactory.build(viewType, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PicMainHolder?, position: Int) {
        holder?.covertData(data[position])
    }
}