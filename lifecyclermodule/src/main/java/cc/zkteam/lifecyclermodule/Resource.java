package cc.zkteam.lifecyclermodule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static cc.zkteam.lifecyclermodule.Status.ERROR;
import static cc.zkteam.lifecyclermodule.Status.LOADING;
import static cc.zkteam.lifecyclermodule.Status.SUCCESS;

/**
 * Resource
 * a generic class that describes a data with a status
 * Created by WangQing on 2017/11/8.
 */

public class Resource<T> {
    @NonNull
    public final String status;

    @Nullable public final T data;
    @Nullable public final String message;

    public Resource(@NonNull String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
