package cc.zkteam.juediqiusheng.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by kutear on 10/29/17.
 */
data class PicCategoryBean(
        @SerializedName("id")
        val id: String,
        @SerializedName("picCategoryName")
        val picCategoryName: String,
        @SerializedName("picCategoryUrl")
        val picCategoryUrl: String,
        @SerializedName("picCategoryPic")
        val picCategoryPic: String,
        @SerializedName("picCategoryCollection")
        val picCategoryCollection: String

)