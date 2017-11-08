package cc.zkteam.juediqiusheng.module.waterfall;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ztw on 2017/10/30.
 */

public class ItemBean {

    private int id;
    private String picId;
    @SerializedName("picCategoryId_id")
    private Long picCategoryId;
    private String picUrl;
    private String picTinyUrl;
    private String picSmallUrl;
    private String picZKUrl;
    private String picName;
    private Long picCollection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public Long getPicCategoryId() {
        return picCategoryId;
    }

    public void setPicCategoryId(Long picCategoryId) {
        this.picCategoryId = picCategoryId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicTinyUrl() {
        return picTinyUrl;
    }

    public void setPicTinyUrl(String picTinyUrl) {
        this.picTinyUrl = picTinyUrl;
    }

    public String getPicSmallUrl() {
        return picSmallUrl;
    }

    public void setPicSmallUrl(String picSmallUrl) {
        this.picSmallUrl = picSmallUrl;
    }

    public String getPicZKUrl() {
        return picZKUrl;
    }

    public void setPicZKUrl(String picZKUrl) {
        this.picZKUrl = picZKUrl;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Long getPicCollection() {
        return picCollection;
    }

    public void setPicCollection(Long picCollection) {
        this.picCollection = picCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBean itemBean = (ItemBean) o;

        return id == itemBean.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
