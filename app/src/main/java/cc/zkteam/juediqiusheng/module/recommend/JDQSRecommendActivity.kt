package cc.zkteam.juediqiusheng.module.recommend

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.bean.RecommendedBean
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager
import cc.zkteam.juediqiusheng.module.pic.main.adapter.RecommendAdapter
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_recommendation.*
import org.jetbrains.anko.ctx

/**
 * Created by Gzw on 2017/10/30 0030.
 */
@Route(path = "/modules/recommend/recommendation")
class JDQSRecommendActivity : AppCompatActivity() {
    private val adapter = RecommendAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        initView()
        initData()
    }

    private fun initData() {
        ZKConnectionManager.getInstance()
                .zkApi.getRecommended("10004", "20")
                .enqueue(object : ZKCallback<List<RecommendedBean>>() {
                    override fun onFailure(throwable: Throwable?) {
                        throwable?.printStackTrace()
                    }

                    override fun onResponse(result: List<RecommendedBean>?) {
                        Log.d("JDQSRecommendActivity", result?.toString())
                        adapter.covertData(result)
                    }

                })
    }

    private fun initView() {
        recommendation_list.layoutManager = LinearLayoutManager(ctx)
        recommendation_list.adapter = adapter

    }

}