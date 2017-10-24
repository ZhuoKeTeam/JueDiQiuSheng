package cc.zkteam.juediqiusheng.exception;

/**
 * 必须处理 的异常，不处理，就 crash 给你看
 *
 * Created by WangQing on 2017/5/12.
 */
public class ZKBaseNullPointerException extends RuntimeException {

    public ZKBaseNullPointerException() {
    }

    public ZKBaseNullPointerException(String message) {
        super(message);
    }

    public ZKBaseNullPointerException(String message, Throwable cause) {
        super(message, cause);
    }
}
