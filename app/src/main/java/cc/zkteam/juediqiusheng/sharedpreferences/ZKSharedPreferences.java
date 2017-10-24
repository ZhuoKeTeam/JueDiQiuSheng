package cc.zkteam.juediqiusheng.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.CallSuper;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import java.util.Map;

import cc.zkteam.juediqiusheng.ZKBase;
import cc.zkteam.juediqiusheng.exception.ZKSharePreferencesException;

/**
 * 创建 一个 SharedPreferences 的基类，方便存取数据
 *
 * Created by WangQing on 2017/5/8.
 */
public abstract class ZKSharedPreferences implements IZKSharedPreferences {

    private Context context;
    public String sharedPreferencesFileName;

    public abstract String sharedPreferencesFileName();

    public ZKSharedPreferences() {
        this.context = ZKBase.getContext();
        this.sharedPreferencesFileName = sharedPreferencesFileName();
    }

    @CallSuper
    @Override
    public void put(String key, Object object) throws ZKSharePreferencesException {
        check();

        SharedPreferences.Editor editor = getEditor();

        try {
            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else {
                editor.putString(key, object.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveEditor(editor);
    }

    @Override
    public Object get(String key, Object defaultObject) throws ZKSharePreferencesException {
        check();

        SharedPreferences sp = getSharedPreferences();

        try {
            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(String key) throws ZKSharePreferencesException {
        check();

        SharedPreferences.Editor editor = getEditor();
        editor.remove(key);
        saveEditor(editor);
    }

    @Override
    public void clean() throws ZKSharePreferencesException {
        check();

        SharedPreferences.Editor editor = getEditor();
        editor.clear();
        saveEditor(editor);
    }

    @Override
    public boolean contain(String key) throws ZKSharePreferencesException {
        check();

        SharedPreferences sp = getSharedPreferences();
        return sp.contains(key);
    }

    @Override
    public Map<String, ?> getAll() throws ZKSharePreferencesException {
        check();

        SharedPreferences sp = getSharedPreferences();
        try {
            return sp.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测 条件是否匹配，如果不匹配 抛出异常
     *
     * @throws ZKSharePreferencesException 自定义异常
     */
    private void check() throws ZKSharePreferencesException {
        if (context == null)
            throw new ZKSharePreferencesException("Context is null!");

        if (TextUtils.isEmpty(sharedPreferencesFileName))
            throw new ZKSharePreferencesException("SharedPreferencesFileName is empty!");
    }

    /**
     * 获取 SharedPreferences
     * @return SharedPreferences
     */
    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取 SharedPreferences.Editor
     * @return SharedPreferences.Editor
     */
    private SharedPreferences.Editor getEditor() {
        SharedPreferences sp = getSharedPreferences();
        return sp.edit();
    }

    /**
     * 异步保存 editor，推荐使用
     * 如果有特殊需求，请 直接使用 commit()。
     * @param editor editor
     */
    private void saveEditor(SharedPreferences.Editor editor) {
        try {
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
