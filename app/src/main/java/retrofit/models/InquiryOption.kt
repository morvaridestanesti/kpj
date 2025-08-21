package retrofit.models

import com.google.gson.annotations.SerializedName

data class InquiryOption(
    @SerializedName("insurance_covers")
    val insuranceCovers: List<Int>,
    val countries: List<Country>,
    val provinces: List<Province>,
    val cities: List<City>,
)