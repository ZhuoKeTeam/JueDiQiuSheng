package cc.zkteam.lifecyclermodule;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * UserCache
 * Created by WangQing on 2017/11/8.
 */

public class UserCache {
    @SuppressLint("UseSparseArrays")
    private Map<Integer, MutableLiveData<User>> map = new HashMap<>();

    public LiveData<User> get(int userId) {
        return map.get(userId);
    }

    public void put(int userId, MutableLiveData<User> data) {
        map.put(userId, data);
    }
}
