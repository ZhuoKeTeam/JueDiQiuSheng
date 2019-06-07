package cc.zkteam.juediqiusheng.utils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import static cc.zkteam.juediqiusheng.Constant.ZKTEAM_USER_LIFE_COUNT_FILE_NAME;
import static cc.zkteam.juediqiusheng.Constant.ZKTEAM_USER_LIFE_COUNT_INIT;
import static cc.zkteam.juediqiusheng.Constant.ZKTEAM_USER_LIFE_COUNT_KEY;
import static cc.zkteam.juediqiusheng.Constant.ZKTEAM_USER_LIFE_SPEND_ONE;

public class ZKSP {

    public static void init() {
        put(ZKTEAM_USER_LIFE_COUNT_INIT);
    }

    /**
     * 是否存活
     * @return 是否
     */
    public static boolean isLiving() {
        int oldCount = get();
        return oldCount >= 10;
    }


    public static int get() {
        return getFile().getInt(ZKTEAM_USER_LIFE_COUNT_KEY, 0);
    }

    public static void put(int newCount) {
        getFile().put(ZKTEAM_USER_LIFE_COUNT_KEY, newCount);
    }

    public static void reset() {
        ToastUtils.showShort("已经为您自动添加30点生命值，请放心下载和查看高清福利图吧");
        init();
    }

    public static void save() {
        int oldCount = get();
        getFile().put(ZKTEAM_USER_LIFE_COUNT_KEY, oldCount - ZKTEAM_USER_LIFE_SPEND_ONE);
    }

    private static SPUtils getFile() {
        return SPUtils.getInstance(ZKTEAM_USER_LIFE_COUNT_FILE_NAME);
    }



}
