package cc.zkteam.juediqiusheng.bean

import com.google.gson.annotations.SerializedName
class PicBean(
        @SerializedName("picUrl")
        val picUrl: String,
        @SerializedName("picSmallUrl")
        val picSmallUrl: String
)