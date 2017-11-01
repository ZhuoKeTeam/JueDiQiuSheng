package cc.zkteam.juediqiusheng.managers;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.yw.game.floatmenu.FloatItem;
import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;

import java.util.ArrayList;

import cc.zkteam.juediqiusheng.fragment.WebViewDialogFragment;
import cc.zkteam.juediqiusheng.R;

/**
 * 悬浮窗控制管理类
 * 参考：https://github.com/crosg/FloatMenuSample
 * Created by WangQing on 2017/11/1.
 */
public class ZKFloatMenuManager {


    private ArrayList<FloatItem> itemList = new ArrayList<>();

    private String HOME = "首页";
    private String[] MENU_ITEMS = {HOME};
    private int[] menuIcons = new int[]{R.drawable.yw_menu_account};

    private FloatLogoMenu mFloatMenu;

    private static ZKFloatMenuManager instance = null;

    private ZKFloatMenuManager() {
    }

    public static ZKFloatMenuManager getInstance() {
        if (instance == null) {
            synchronized (ZKFloatMenuManager.class) {
                ZKFloatMenuManager temp = instance;
                if (temp == null) {
                    temp = new ZKFloatMenuManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }


    public FloatLogoMenu getFloatLogoMenu(final Activity mActivity, final FragmentManager fragmentManager) {
        if (mFloatMenu == null) {
            for (int i = 0; i < menuIcons.length; i++) {
                itemList.add(new FloatItem(MENU_ITEMS[i], 0x99000000, 0x99000000, BitmapFactory.decodeResource(mActivity.getResources(), menuIcons[i]), String.valueOf(i + 1)));
            }

            mFloatMenu = new FloatLogoMenu.Builder()
                    .withActivity(mActivity)
//                    .withContext(mActivity.getApplication())//这个在7.0（包括7.0）以上以及大部分7.0以下的国产手机上需要用户授权，需要搭配<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
                    .logo(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.yw_game_logo))
                    .drawCicleMenuBg(true)
                    .backMenuColor(0xffe4e3e1)
                    .setBgDrawable(mActivity.getResources().getDrawable(R.drawable.yw_game_float_menu_bg))
                    //这个背景色需要和logo的背景色一致
                    .setFloatItems(itemList)
                    .defaultLocation(FloatLogoMenu.RIGHT)
                    .drawRedPointNum(false)
                    .showWithListener(new FloatMenuView.OnMenuClickListener() {
                        @Override
                        public void onItemClick(int position, String title) {
//                            Toast.makeText(mActivity, "position " + position + " title:" + title + " is clicked.", Toast.LENGTH_SHORT).show();

                            WebViewDialogFragment editNameDialog = WebViewDialogFragment.getInstance();
                            editNameDialog.show(fragmentManager, "PayDialog");
                        }

                        @Override
                        public void dismiss() {

                        }
                    });

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshDot();
                }
            }, 5000);
        }
        return mFloatMenu;
    }


    public void refreshDot() {
        for (FloatItem menuItem : itemList) {
            if (TextUtils.equals(menuItem.getTitle(), "我的")) {
                menuItem.dotNum = String.valueOf(8);
            }
        }
        mFloatMenu.setFloatItemList(itemList);
    }

    public void hideFloat() {
        if (mFloatMenu != null) {
            mFloatMenu.hide();
        }
    }

    public void destroyFloat() {
        if (mFloatMenu != null) {
            mFloatMenu.destoryFloat();
        }
        mFloatMenu = null;
    }
}
