package cc.zkteam.juediqiusheng.bean
import com.google.gson.annotations.SerializedName
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
        val tjSourceUrl: String,
        @SerializedName("tjSourceUrl")
        val newsSourceUrl: String,
        @SerializedName("tjDate")
        val date: Long,
        @SerializedName("tjCollection")
        val collectionDate: Long
)