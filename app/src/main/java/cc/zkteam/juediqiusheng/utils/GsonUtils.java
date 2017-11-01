package cc.zkteam.juediqiusheng.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import cc.zkteam.juediqiusheng.waterfall.ParameterizedTypeImpl;
import cc.zkteam.juediqiusheng.waterfall.ResponseBean;

/**
 * Created by ztw on 2017/10/31.
 */

public class GsonUtils {

    /**
     * 解析result是object的情况
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> fromJsonObject(String json, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(ResponseBean.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }

    /**
     * 解析result是array的情况
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<List<T>> fromJsonArray(String json, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(ResponseBean.class, new Type[]{listType});
        return new Gson().fromJson(json, type);
    }
}
