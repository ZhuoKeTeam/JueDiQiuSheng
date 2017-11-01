package cc.zkteam.juediqiusheng.module.pic.category

import android.support.v7.widget.LinearLayoutManager
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.activity.BaseActivity
import cc.zkteam.juediqiusheng.bean.PicBean
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager
import cc.zkteam.juediqiusheng.module.pic.category.adapter.PicCategoryAdapter
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.fragment_pic_main_layout.*
import org.jetbrains.anko.ctx

/**
 * Created by kutear on 2017/11/1.
 *
 * 图片分类
 */

@Route(path = "/modules/pic/category")
class PicCategoryActivity : BaseActivity() {
    @Autowired(name = "categoryId")
    lateinit var categoryId: String
    private val adapter = PicCategoryAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_pic_main_layout
    }

    override fun initViews() {
        ARouter.getInstance().inject(this)
        pic_main_list.layoutManager = LinearLayoutManager(ctx)
        pic_main_list.adapter = adapter
    }

    override fun initListener() {
    }

    override fun initData() {
        ZKConnectionManager.getInstance()
                .zkApi.getCategoryList(categoryId, 20)
                .enqueue(object : ZKCallback<List<PicBean>>() {
                    override fun onResponse(result: List<PicBean>?) {
                        adapter.covertData(result)
                    }

                    override fun onFailure(throwable: Throwable?) {
                    }

                })
    }

}