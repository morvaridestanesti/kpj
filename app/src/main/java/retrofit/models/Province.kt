package retrofit.models

import com.google.gson.annotations.SerializedName

data class Province(
    val id: Int,
    @SerializedName("country_id")
    val countryId: Int,
    val name: String
)
