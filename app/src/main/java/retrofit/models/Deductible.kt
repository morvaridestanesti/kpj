package retrofit.models

import com.google.gson.annotations.SerializedName

data class Deductible(
    val id: Int,
    val slug: String,
    val amount: String,
    val discount: String,
    @SerializedName("amount_value")
    val amountValue: Int,
    @SerializedName("discount_value")
    val discountValue: Int
)
