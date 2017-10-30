package cc.zkteam.juediqiusheng.module.pic.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.bean.PicCategoryBean
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager
import cc.zkteam.juediqiusheng.module.pic.main.adapter.PicMainAdapter
import cc.zkteam.juediqiusheng.module.pic.main.adapter.RecommendAdapter
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.fragment_pic_main_layout.*
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by kutear on 10/24/17.
 */
@Route(path = "modules/pic/main")
class JDQSPicMainFragment : Fragment() {
    private val adapter = PicMainAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pic_main_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pic_main_list.layoutManager = LinearLayoutManager(ctx)
        pic_main_list.adapter = adapter

        ZKConnectionManager.getInstance()
                .zkApi.picCategory
                .enqueue(object : ZKCallback<List<PicCategoryBean>>() {
                    override fun onFailure(throwable: Throwable?) {

                    }

                    override fun onResponse(result: List<PicCategoryBean>?) {
                        adapter.covertData(result)
                    }

                })


    }
}