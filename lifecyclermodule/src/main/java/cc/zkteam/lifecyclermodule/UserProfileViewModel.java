package cc.zkteam.lifecyclermodule;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * UserProfileViewModel
 * Created by WangQing on 2017/11/8.
 */

public class UserProfileViewModel extends ViewModel {
    private String userId;
//    private User user;
    private LiveData<User> user;
    private UserRepository userReps;

    @Inject
    public UserProfileViewModel(UserRepository userReps) {
        this.userReps = userReps;
    }

    public void init(int userId) {
        if (this.userId != null) {
            return;
        }
//        this.userId = userId;
        user = userReps.getUser(userId);
    }

    public LiveData<User> getUser() {
        return user;
    }
}
