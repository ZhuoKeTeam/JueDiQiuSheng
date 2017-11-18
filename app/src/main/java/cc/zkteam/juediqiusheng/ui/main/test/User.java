package cc.zkteam.juediqiusheng.ui.main.test;

import javax.inject.Inject;

/**
 * Created by WangQing on 2017/11/19.
 */

public class User {

    @Inject
    public User() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
