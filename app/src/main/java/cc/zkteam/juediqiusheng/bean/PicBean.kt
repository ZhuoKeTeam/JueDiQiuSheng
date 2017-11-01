package cc.zkteam.juediqiusheng.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by kutear on 2017/11/1.
 */
class PicBean(
        @SerializedName("picUrl")
        val picUrl: String,
        @SerializedName("picSmallUrl")
        val picSmallUrl: String
)