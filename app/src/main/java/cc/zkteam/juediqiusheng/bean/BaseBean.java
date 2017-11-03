package cc.zkteam.juediqiusheng.bean;

/**
 * CodeBean base 的信息
 * Created by WangQing on 2017/10/27.
 */

public class BaseBean<T> {
    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
