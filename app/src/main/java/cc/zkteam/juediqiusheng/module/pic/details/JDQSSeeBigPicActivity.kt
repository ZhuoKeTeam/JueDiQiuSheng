package cc.zkteam.juediqiusheng.module.pic.details

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import app.dinus.com.loadingdrawable.LoadingView
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.ad.ZKAD
import cc.zkteam.juediqiusheng.utils.L
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_see_big_pic_layout)
        ARouter.getInstance().inject(this)

        initToolbar()
        loadImg()

        ZKAD.initHFAD(this, false)
        ZKAD.loadGoogleCPAD()
    }

    private fun loadImg() {
        L.i("当前的 Url是：" + url)
        val loadingView: LoadingView = findViewById(R.id.loading_view)
        loadingView.visibility = View.VISIBLE

        val controller = Fresco.newDraweeControllerBuilder()
        controller.setUri(url)
        controller.oldController = see_big_pic_imageView.controller
        controller.controllerListener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                loadingView.visibility = View.GONE
                if (imageInfo == null || see_big_pic_imageView == null) {
                    return
                }
                see_big_pic_imageView.update(imageInfo.width, imageInfo.height)
            }

            override fun onFailure(id: String?, throwable: Throwable?) {
                super.onFailure(id, throwable)
                loadingView.visibility = View.GONE
            }
        }
        see_big_pic_imageView.controller = controller.build()
    }

    // 关于 Toolbar 的使用
    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_pic_detail)
        toolbar.setTitle(R.string.pic_detail)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.black))
        toolbar.inflateMenu(R.menu.pic_top_right)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pic_top_right,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.item_save_pic -> {
                ZKAD.showGoogleCPAD()
                ToastUtils.showShort("保存图片到本地")
            }
        }

        return super.onOptionsItemSelected(item)
    }


}