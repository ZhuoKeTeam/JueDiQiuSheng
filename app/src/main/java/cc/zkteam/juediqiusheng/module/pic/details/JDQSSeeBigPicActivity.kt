package cc.zkteam.juediqiusheng.module.pic.details

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.RelativeLayout
import app.dinus.com.loadingdrawable.LoadingView
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.ad.ZKAD
import cc.zkteam.juediqiusheng.utils.L
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.baidu.mobads.AdView
import com.baidu.mobads.AdViewListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import kotlinx.android.synthetic.main.activity_see_big_pic_layout.*
import org.json.JSONObject


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


        val adViewContent = findViewById<RelativeLayout>(R.id.ad_content_view_new)

        val adPlaceID = "6294768"//广告位 ID
        val adView = AdView(this, adPlaceID)
        adView.setListener(object : AdViewListener {
            override fun onAdReady(adView: AdView) {
                ZKAD.logD("BD_HF_onAdReady->")
            }

            override fun onAdShow(jsonObject: JSONObject) {
                ZKAD.logD("BD_HF_onAdShow->")
            }

            override fun onAdClick(jsonObject: JSONObject) {
                ZKAD.logD("BD_HF_onAdClick->")
            }

            override fun onAdFailed(s: String) {
                ZKAD.logD("BD_HF_onAdFailed->$s")
            }

            override fun onAdSwitch() {
                ZKAD.logD("BD_HF_onAdSwitch->")
            }

            override fun onAdClose(jsonObject: JSONObject) {
                ZKAD.logD("BD_HF_onAdClose->")
            }
        })

//        DisplayMetrics dm = new DisplayMetrics();
//        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
//        int winW = dm.widthPixels;
//        int winH = dm.heightPixels;
//        int width = Math.min(winW, winH);
//        int height = width * 3 / 20;
//        //把横幅 view 添加到自己的 viewGroup 组件中，必须写，才可以展示横幅广告
//        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
//        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adViewContent.addView(adView)


        initToolbar()
        loadImg()

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

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_pic_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

}