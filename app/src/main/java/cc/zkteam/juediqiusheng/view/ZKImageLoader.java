package cc.zkteam.juediqiusheng.view;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * ZKImageLoader
 * Created by WangQing on 2017/11/7.
 */

public class ZKImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }

    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        return new ZKImageView(context);
    }
}
