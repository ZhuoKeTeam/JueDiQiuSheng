package cc.zkteam.lifecyclermodule;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * UserDao
 * Created by WangQing on 2017/11/8.
 */
@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void save(User user);

    // TODO: 2017/11/8 what?  --> test
    @Query("SELECT * FROM user WHERE id = :second")
    boolean hasUser(int second);

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> load(int userId);
}
