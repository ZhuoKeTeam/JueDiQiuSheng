package cc.zkteam.juediqiusheng.module.category

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Window
import cc.zkteam.juediqiusheng.R
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

@Route(path = "/module/category")
class BaseCategoryListActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var list: RecyclerView? = null
    private var adapter: CategoryAdapter? = null
    private var datas: List<BeanCategory.ResultBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_base_category_list)
        toolbar = findViewById(R.id.toolbar_category) as Toolbar?
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView()
        requestData()
    }

    private fun initView() {
        toolbar!!.setTitleTextColor(Color.WHITE)
        toolbar!!.title = getString(R.string.category_list)
        list = findViewById(R.id.list_category) as RecyclerView?
        list!!.layoutManager = LinearLayoutManager(this)
    }

    private fun requestData() {
        val client = OkHttpClient()

        val request: Request = Request.Builder().url("http://www.zkteam.cc/JueDiQiuSheng/categoryJson?pageCount=20").build()

        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(var1: Call, var2: IOException) {
                ToastUtils.showShort(var2.message)
            }

            override fun onResponse(var1: Call, var2: Response) {
                val str = var2.body()!!.string()
                callbackOnSuccess(str)
            }
        })
    }

    private fun callbackOnSuccess(str: String) {
        val result: BeanCategory = Gson().fromJson(str, BeanCategory::class.java)

        if (result.result != null && !result.result.isEmpty()) {
            datas = result.result
            adapter = CategoryAdapter(this, datas)
            list!!.post({
                run {
                    list!!.adapter = adapter
                }
            })
        }
    }
}
