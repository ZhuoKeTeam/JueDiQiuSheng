package cc.zkteam.juediqiusheng.exception;

/**
 * 自定义 异常
 *
 * Created by WangQing on 2017/5/8.
 */

public class ZKSharePreferencesException extends Exception {

    public ZKSharePreferencesException(String message) {
        super(message);
    }

    public ZKSharePreferencesException(String message, Throwable cause) {
        super(message, cause);
    }


}
