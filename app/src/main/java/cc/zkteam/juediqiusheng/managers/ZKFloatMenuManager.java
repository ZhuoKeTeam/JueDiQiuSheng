package cc.zkteam.juediqiusheng.managers;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.BitmapFactory;
import com.yw.game.floatmenu.FloatItem;
import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;
import java.util.ArrayList;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.fragment.WebViewDialogFragment;
public class ZKFloatMenuManager {
    private ArrayList<FloatItem> itemList = new ArrayList<>();
    private String HOME = "闲聊么";
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
            itemList.clear();
            for (int i = 0; i < menuIcons.length; i++) {
                itemList.add(new FloatItem(MENU_ITEMS[i], 0x99000000, 0x99000000, BitmapFactory.decodeResource(mActivity.getResources(), menuIcons[i]), String.valueOf(i + 1)));
            }
            mFloatMenu = new FloatLogoMenu.Builder()
                    .withActivity(mActivity)
                    .logo(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.yw_game_logo))
                    .drawCicleMenuBg(true)
                    .backMenuColor(0xffe4e3e1)
                    .setBgDrawable(mActivity.getResources().getDrawable(R.drawable.yw_game_float_menu_bg))
                    .setFloatItems(itemList)
                    .defaultLocation(FloatLogoMenu.RIGHT)
                    .drawRedPointNum(false)
                    .showWithListener(new FloatMenuView.OnMenuClickListener() {
                        @Override
                        public void onItemClick(int position, String title) {
                            WebViewDialogFragment editNameDialog = WebViewDialogFragment.getInstance();
                            editNameDialog.show(fragmentManager, "PayDialog");
                        }
                        @Override
                        public void dismiss() {
                        }
                    });
        }
        return mFloatMenu;
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
