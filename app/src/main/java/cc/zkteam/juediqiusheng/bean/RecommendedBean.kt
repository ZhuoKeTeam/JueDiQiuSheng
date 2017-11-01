package cc.zkteam.juediqiusheng.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Gzw on 2017/10/30 0030.
 */
data class RecommendedBean(
        @SerializedName("id")
        val id: String,
        @SerializedName("jid")
        val jId: String,
        @SerializedName("tjCategoryId_id")
        val categoryId: String,
        @SerializedName("tjName")
        val context: String,
        @SerializedName("tjPicUrl")
        val picUrl: String,
        @SerializedName("tjUrl")
        val newsSourceUrl: String,
        @SerializedName("tjDate")
        val date: String,
        @SerializedName("tjCollection")
        val collectionDate: String
)