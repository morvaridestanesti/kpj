package retrofit.models

import com.google.gson.annotations.SerializedName

data class Price(
    val id: Int,
    @SerializedName("plan_id")
    val planId: Int,
    @SerializedName("age_from")
    val ageFrom: Int,
    @SerializedName("age_to")
    val ageTo: Int?,
    @SerializedName("insurance_cover")
    val insuranceCover: Int,
    @SerializedName("daily_cost")
    val dailyCost: Double?,
    @SerializedName("is_entry")
    val isEntry: Boolean,
)
