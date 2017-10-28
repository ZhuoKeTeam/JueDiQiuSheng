package cc.zkteam.juediqiusheng.bean;

import java.util.List;

/**
 * CodeBean base 的信息
 * Created by WangQing on 2017/10/27.
 */

public class BaseBean<T> {
    private int code;
    private String message;
    private List<T> result;

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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
