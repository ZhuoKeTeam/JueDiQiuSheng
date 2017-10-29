package cc.zkteam.juediqiusheng.module.pic.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.R

/**
 * Created by kutear on 10/29/17.
 */
class PicMainHolderFactory {
    companion object {
        fun build(type: Int, parent: ViewGroup): PicMainHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pic_main_list_layout, parent, false)
            return PicMainHolder(itemView)
        }
    }
}