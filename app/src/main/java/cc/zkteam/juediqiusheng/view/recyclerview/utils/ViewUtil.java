package cc.zkteam.juediqiusheng.view.recyclerview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * view工具类
 */
public class ViewUtil {

    public static void scaleLayoutParams(View view, float measureW, float measureH, int scaleW, int defScaleH) {

        if (view == null)
            return;

        int scaleH;
        if (measureW > 0 && measureH > 0)
            scaleH = (int) (scaleW * (measureH / measureW));
        else
            scaleH = defScaleH;

        ViewGroup.LayoutParams vglp = view.getLayoutParams();
        if (vglp.width != scaleW || vglp.height != scaleH) {

            vglp.width = scaleW;
            vglp.height = scaleH;
            view.setLayoutParams(vglp);
        }
    }

    public static void scaleLayoutParamsByH(View view, float measureW, float measureH, int scaleH, int defScaleW) {

        if (view == null)
            return;

        int scaleW;
        if (measureW > 0 && measureH > 0)
            scaleW = (int) (scaleH * (measureW / measureH));
        else
            scaleW = defScaleW;

        ViewGroup.LayoutParams vglp = view.getLayoutParams();
        if (vglp.width != scaleW || vglp.height != scaleH) {

            vglp.width = scaleW;
            vglp.height = scaleH;
            view.setLayoutParams(vglp);
        }
    }

    public static void addOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener lisn) {

        if (view != null && lisn != null)
            view.getViewTreeObserver().addOnGlobalLayoutListener(lisn);
    }

    /**
     * 移除指定view的layout改变监听
     *
     * @param view
     * @param lisn
     */
    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener lisn) {

        if (view == null || lisn == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.getViewTreeObserver().removeOnGlobalLayoutListener(lisn);
        else
            view.getViewTreeObserver().removeGlobalOnLayoutListener(lisn);
    }

    /**
     * 设置文本文字颜色选择器
     *
     * @param tv
     * @param colorStateResId
     */
    public static void setTextColorState(TextView tv, int colorStateResId) {

        if (tv == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            tv.setTextColor(tv.getContext().getColorStateList(colorStateResId));
        else
            tv.setTextColor(tv.getContext().getResources().getColorStateList(colorStateResId));
    }

    /**
     * 设置view的背景资源
     *
     * @param v
     * @param drawable
     */
    public static void setViewBackground(View v, Drawable drawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            v.setBackground(drawable);
        else
            v.setBackgroundDrawable(drawable);
    }

    /**
     * 判断textview是否为空
     *
     * @param tv
     * @return
     */
    public static boolean checkTextViewEmpty(TextView tv) {

        if (tv == null)
            return true;

        CharSequence text = tv.getText();
        return text == null ? true : text.length() == 0;
    }

    /**
     * 判断textview trim() 后是否为空
     *
     * @param tv
     * @return
     */
    public static boolean checkTextViewTrimEmpty(TextView tv) {

        if (tv == null)
            return true;

        String text = tv.getText().toString();
        return text == null ? true : text.trim().length() == 0;
    }

    /**
     * 获取textview的文本
     *
     * @param tv
     * @return
     */
    public static String getTextViewText(TextView tv) {

        if (tv == null)
            return TextUtil.TEXT_EMPTY;

        String text = tv.getText().toString();
        return text == null ? TextUtil.TEXT_EMPTY : text;
    }

    /**
     * 获取textview trim() 后的文本
     *
     * @param tv
     * @return
     */
    public static String getTextViewTrimText(TextView tv) {

        if (tv == null)
            return TextUtil.TEXT_EMPTY;

        String text = tv.getText().toString();
        return text == null ? TextUtil.TEXT_EMPTY : text.trim();
    }

    /**
     * 获取textView 过滤全部空格 包括首尾，中间
     *
     * @param tv
     * @return
     */
    public static String getTextViewFilterTrimText(TextView tv) {

        if (tv == null)
            return TextUtil.TEXT_EMPTY;

        String text = tv.getText().toString();
        return text == null ? TextUtil.TEXT_EMPTY : text.replaceAll(" ", TextUtil.TEXT_EMPTY);
    }

    /**
     * 获取imageview src bitmap对象
     *
     * @param iv
     * @return
     */
    public static Bitmap getImageViewBitmap(ImageView iv) {

        if (iv == null)
            return null;

        if (iv.getDrawable() instanceof BitmapDrawable)
            return ((BitmapDrawable) iv.getDrawable()).getBitmap();
        else
            return null;
    }

    /**
     * 显示view
     *
     * @param v
     * @return
     */
    public static boolean showView(View v) {

        if (v == null)
            return false;

        if (v.getVisibility() == View.VISIBLE) {

            return false;
        } else {

            v.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public static boolean showOrGoneView(View v, boolean isShow) {
        return isShow ? showView(v) : goneView(v);
    }

    /**
     * 是否为显示状态(visible)
     *
     * @param v
     * @return
     */
    public static boolean isShow(View v) {

        return v == null ? false : v.getVisibility() == View.VISIBLE;
    }

    /**
     * 隐藏view
     *
     * @param v
     * @return
     */
    public static boolean hideView(View v) {

        if (v == null)
            return false;

        if (v.getVisibility() == View.INVISIBLE) {

            return false;
        } else {

            v.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    /**
     * gone view
     *
     * @param v
     * @return
     */
    public static boolean goneView(View v) {

        if (v == null)
            return false;

        if (v.getVisibility() == View.GONE) {

            return false;
        } else {

            v.setVisibility(View.GONE);
            return true;
        }
    }

    /**
     * 显示iamgeview, 并设置指定图片资源
     *
     * @param iv
     * @param imageResId
     */
    public static void showImageView(ImageView iv, int imageResId) {

        if (iv.getVisibility() != View.VISIBLE)
            iv.setVisibility(View.VISIBLE);

        if (imageResId > 0) {

            iv.setImageResource(imageResId);
        } else {

            iv.setImageDrawable(null);
        }
    }

    /**
     * 显示iamgeview, 并设置指定drawable
     *
     * @param iv
     * @param drawable
     */
    public static void showImageView(ImageView iv, Drawable drawable) {

        if (iv.getVisibility() != View.VISIBLE)
            iv.setVisibility(View.VISIBLE);

        iv.setImageDrawable(drawable);
    }

    /**
     * 隐藏imageview，并将图片资源清除掉
     *
     * @param iv
     */
    public static void hideImageView(ImageView iv) {

        if (iv.getVisibility() != View.INVISIBLE)
            iv.setVisibility(View.INVISIBLE);

        iv.setImageDrawable(null);
    }

    /**
     * gone imageview，并将图片资源清除掉
     *
     * @param iv
     */
    public static void goneImageView(ImageView iv) {

        if (iv.getVisibility() != View.GONE)
            iv.setVisibility(View.GONE);

        iv.setImageDrawable(null);
    }

    /**
     * 获取该view的快照
     *
     * @param v
     * @return
     */
    public static Bitmap snapshot(View v) {

        Bitmap bmp = null;
        try {

            bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bmp);
            canvas.drawColor(Color.WHITE);
            v.draw(canvas);
        } catch (Throwable throwable) {

            throwable.printStackTrace();
        }
        return bmp;
    }

    /**
     * 获取ListView，该ListView对公共的样式做了清除
     *
     * @param context
     * @return
     */
    public static ListView getCleanListView(Context context, int id) {

        ListView lv = new ListView(context);
        lv.setId(id);
        lv.setDivider(null);
        lv.setDividerHeight(0);
        lv.setFooterDividersEnabled(false);
        lv.setHeaderDividersEnabled(false);
        lv.setSelector(new ColorDrawable(0X00000000));
        lv.setFadingEdgeLength(0);
        lv.setScrollingCacheEnabled(false);
        lv.setVerticalScrollBarEnabled(false);
        lv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return lv;
    }



    /**
     * 获取FrameLayout
     *
     * @param context
     * @param id
     * @return
     */
    public static FrameLayout getFrameLayout(Context context, int id) {

        FrameLayout fl = new FrameLayout(context);
        fl.setId(id);
        return fl;
    }

    /**
     * 通过反射设置ViewPager左右切换滑动时持续时间
     *
     * @param viewPager
     * @param setDuration
     */
    public static void setViewPagerScrollDuration(ViewPager viewPager, final int setDuration) {

        try {

            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, new Scroller(viewPager.getContext()) {

                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {

                    super.startScroll(startX, startY, dx, dy, setDuration);
                }

                @Override
                public void startScroll(int startX, int startY, int dx, int dy) {

                    super.startScroll(startX, startY, dx, dy, setDuration);
                }
            });

        } catch (Throwable t) {

            t.printStackTrace();
        }
    }

    /**
     * 测量view的高度，调用该方法后，可以知道view的高宽信息
     * 该方法有待验证！！！
     *
     * @param v
     */
    public static void measureView(View v) {

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }

    /**
     * 给ListView设置数据为空时的提示view
     *
     * @param listView
     * @param emptyView
     */
    public static void setEmptyListView(ListView listView, View emptyView) {

        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        param.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        //param.topMargin = DensityUtil.dip2px(40f);
        ((ViewGroup) (listView.getParent())).addView(emptyView, param);

        listView.setEmptyView(emptyView);
        ViewUtil.hideView(emptyView);
    }

    /**
     * 获取view 所在屏幕的位置
     *
     * @param view
     * @return
     */
    public static int[] getViewLocation(View view) {

        int[] size = new int[2];
        view.getLocationInWindow(size);
        return size;
    }

    public static void setLeftDrawable(TextView tv, Drawable drawable){

        if(tv != null)
            tv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public static void setLeftDrawable(TextView textView, int resource) {

        if (textView != null)
            textView.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
    }

    public static void setRightDrawable(TextView textView, int resource) {

        if (textView != null)
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resource, 0);
    }

    public static void setTopDrawable(TextView textView, int resource) {

        if (textView != null)
            textView.setCompoundDrawablesWithIntrinsicBounds(0, resource, 0, 0);
    }

    public static void setBottomDrawable(TextView textView, int resource) {

        if (textView != null)
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resource);
    }

    public static void setCompoundDrawable(TextView textView, int leftResId, int topResId, int rightResId, int btmResId) {

        if (textView != null)
            textView.setCompoundDrawablesWithIntrinsicBounds(leftResId, topResId, rightResId, btmResId);
    }

    /**
     * @param text
     * @param deviceWidth in pixels
     * @param padding     in pixels
     * @return
     */
    public static int measureTextLineCount(TextPaint myTextPaint, CharSequence text, int deviceWidth, int padding) {

        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;

        float spacingMultiplier = 1;
        float spacingAddition = padding;
        boolean includePadding = padding != 0;
        if (text == null)
            text = TextUtil.TEXT_EMPTY;

        StaticLayout myStaticLayout = new StaticLayout(text, myTextPaint, deviceWidth, alignment, spacingMultiplier, spacingAddition, includePadding);
        return myStaticLayout.getLineCount();
    }

    /**
     * 估算TextView内文字的dp宽度
     * @param textView
     * @param text
     * @return
     */
    public static int measureTextViewWidth(TextView textView, String text){

        if(textView == null || TextUtil.isEmpty(text))
            return 0;

        Rect bounds = new Rect();
        TextPaint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int mTextWidth = bounds.width();
        return mTextWidth;
    }

    /**
     * 反射清除FragmentStatePagerAdapter 保持的页面状态
     * @param object
     */
    public static void clearViewPagerState(FragmentStatePagerAdapter object) {

        try {

            Field field = FragmentStatePagerAdapter.class.getDeclaredField("mSavedState");
            field.setAccessible(true);
            field.set(object, new ArrayList<Fragment.SavedState>());

        } catch (Throwable t) {

            t.printStackTrace();
        }
    }
}
