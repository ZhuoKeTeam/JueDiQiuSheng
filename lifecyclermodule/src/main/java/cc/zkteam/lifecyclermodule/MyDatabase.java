package cc.zkteam.lifecyclermodule;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * MyDatabase
 * Created by WangQing on 2017/11/8.
 */

@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
