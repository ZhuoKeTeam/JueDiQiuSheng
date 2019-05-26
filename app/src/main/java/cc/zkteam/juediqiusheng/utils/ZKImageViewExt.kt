package cc.zkteam.juediqiusheng.utils

import cc.zkteam.juediqiusheng.R
import cc.zkteam.juediqiusheng.view.ZKImageView
import com.facebook.fresco.helper.ImageLoader


/**
 * Created by kutear on 10/24/17.
 */
/**
 * 加载图片
 */
fun ZKImageView.loadUrl(url: String?, defHolderImage: Int = R.drawable.ic_default) {
    url?.let {
        ImageLoader.loadImage(this, it)
    }
    setPlaceHolderImage(defHolderImage)
}

/**
 * 设置占位图
 */
fun ZKImageView.setPlaceHolderImage(defHolderImage: Int) {
    hierarchy.setPlaceholderImage(defHolderImage)
}