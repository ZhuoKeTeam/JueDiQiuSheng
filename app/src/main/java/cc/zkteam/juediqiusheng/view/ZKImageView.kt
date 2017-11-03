package cc.zkteam.juediqiusheng.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.facebook.drawee.view.SimpleDraweeView

@SuppressLint("ViewConstructor")
/**
 * Created by kutear on 10/24/17.
 *
 * 代替SimpleDraweeView使用
 */
class ZKImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SimpleDraweeView(context, attrs, defStyleAttr)