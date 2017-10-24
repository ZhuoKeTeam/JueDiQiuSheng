package cc.zkteam.juediqiusheng.module.pic.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.utils.loadUrl
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_see_big_pic_layout.*

/**
 * Created by kutear on 10/24/17.
 *
 * 大图预览
 */
@Route(path = "/module/pic/details")
class JDQSSeeBigPicActivity : AppCompatActivity() {
    @Autowired(name = "url")
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_big_pic_layout)
        ARouter.getInstance().inject(this)
        see_big_pic_imageView.loadUrl(url)
    }
}