package cc.zkteam.juediqiusheng.module.pic.details

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.utils.L
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
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
        setContentView(R.layout.activity_see_big_pic_layout)
        ARouter.getInstance().inject(this)

        L.i("当前的 Url是：" + url)

//        添加 https://github.com/ongakuer/PhotoDraweeView

        val controller = Fresco.newDraweeControllerBuilder()
        controller.setUri(url)
        controller.oldController = see_big_pic_imageView.getController()
        controller.setControllerListener(object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                if (imageInfo == null || see_big_pic_imageView == null) {
                    return
                }
                see_big_pic_imageView.update(imageInfo.width, imageInfo.height)
            }
        })
        see_big_pic_imageView.setController(controller.build())

    }
}