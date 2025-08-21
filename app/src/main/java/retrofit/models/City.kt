package retrofit.models

import com.google.gson.annotations.SerializedName

data class City(
    val id: Int,
    @SerializedName("province_id")
    val provinceId: Int,
    val name: String
)
