package cc.zkteam.juediqiusheng.sharedpreferences;


import java.util.Map;

import cc.zkteam.juediqiusheng.exception.ZKSharePreferencesException;

/**
 * JMSharedPreferences interface
 *
 * Created by WangQing on 2017/5/8.
 */
public interface IZKSharedPreferences {

    void put(String key, Object object) throws ZKSharePreferencesException;

    Object get(String key, Object defaultObject) throws ZKSharePreferencesException;

    void remove(String key) throws ZKSharePreferencesException;

    void clean() throws ZKSharePreferencesException;

    boolean contain(String key) throws ZKSharePreferencesException;

    Map<String, ?> getAll() throws ZKSharePreferencesException;
}
