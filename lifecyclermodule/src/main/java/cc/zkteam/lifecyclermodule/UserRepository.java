package cc.zkteam.lifecyclermodule;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**UserRepository
 * Created by WangQing on 2017/11/8.
 */
@Singleton
public class UserRepository {
    private final int FRESH_TIMEOUT = 3; //秒

    private Webservice webservice;
    private UserDao userDao;
    private Executor executor;
    private UserCache userCache;

    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUserDB(int userId) {
        refreshUserDB(userId);
        return userDao.load(userId);
    }

    public LiveData<User> getUser(int userId) {
        refreshUserDB(userId);
        return userDao.load(userId);
    }

    private void refreshUserDB(int userId) {
        executor.execute(()->{
            boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
            if (!userExists) {
                Response<User> response = null;
                try {
                    response = webservice.getUser(userId).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response != null) {
                    userDao.save(response.body());
                }
            }
        });
    }


    public LiveData<User> getUserForNetWork(int userId) {

        LiveData<User> cached = userCache.get(userId);
        if (cached != null)
            return cached;

        final MutableLiveData<User> data = new MutableLiveData<>();
        userCache.put(userId, data);

        webservice.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return data;
    }
}
