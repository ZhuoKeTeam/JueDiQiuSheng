package cc.zkteam.juediqiusheng.module.recommend

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.bean.RecommendedBean
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager
import cc.zkteam.juediqiusheng.module.pic.main.adapter.RecommendAdapter
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_recommendation.*
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by Gzw on 2017/10/30 0030.
 */
@Route(path = "/modules/recommend/recommendation")
class JDQSRecommendFragment : Fragment() {
    private val adapter = RecommendAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_recommendation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recommendation_list.layoutManager = LinearLayoutManager(ctx)
        recommendation_list.adapter = adapter

        requestData()
    }

    private fun requestData() {
        ZKConnectionManager.getInstance()
                .zkApi.getRecommended("10004", "20")
                .enqueue(object : ZKCallback<List<RecommendedBean>>() {
                    override fun onFailure(throwable: Throwable?) {
                        throwable?.printStackTrace()
                    }

                    override fun onResponse(result: List<RecommendedBean>?) {
                        Log.d("JDQSRecommendFragment", result?.toString())
                        adapter.covertData(result)
                    }

                })
    }
}