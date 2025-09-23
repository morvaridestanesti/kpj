package retrofit

import retrofit.models.BaseContent
import retrofit.models.InquiryOption
import retrofit.models.ResponseWrapper
import retrofit.models.Session
import retrofit.models.SupportedPlan
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.OPTIONS
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("auth/check")
    suspend fun authCheck(): Response<ResponseWrapper<Void?>>

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun authLogin(@Field("username") username: String, @Field("password") password: String): Response<ResponseWrapper<Session>>

    @PATCH("auth/refresh")
    suspend fun authRefresh(): Response<ResponseWrapper<Session>>

    @GET("base/content")
    suspend fun baseContent(): Response<ResponseWrapper<BaseContent>>

    @GET("inquiry")
    suspend fun inquiryGetSupportedPlans(@Query("insurance_cover") insuranceCover: Int, @Query("is_entry") isEntry: Int, @Query("birthdays") birthdays: String, @Query("start_at") startAt: String, @Query("end_at") endAt: String): Response<ResponseWrapper<List<SupportedPlan>>>

    @OPTIONS("inquiry")
    suspend fun inquiryFormOptions(): Response<ResponseWrapper<InquiryOption>>

    @FormUrlEncoded
    @POST("inquiry")
    suspend fun submit(
        @Field("address") address: String,
        @Field("arrived_at") arrivedAt: String,
        @Field("birthdays") birthdays: String,
        @Field("card_cvv") cardCvv: String,
        @Field("card_expiration") cardExpiration: String,
        @Field("card_name") cardName: String,
        @Field("card_number") cardNumber: String,
        @Field("city") city: String,
        @Field("deductivble_ids") deductibleIds: String,
        @Field("email") email: String,
        @Field("ended_at") endedAt: String,
        @Field("first_names") firstNames: String,
        @Field("genders") genders: String,
        @Field("insurance_cover") insuranceCover: Int,
        @Field("is_entry") isEntry: Int,
        @Field("last_names") lastNames: String,
        @Field("message") message: String,
        @Field("phone") phone: String,
        @Field("postal_code") postalCode: String,
        @Field("price_ids") priceIds: String,
        @Field("province_id") provinceId: Int,
        @Field("started_at") startedAt: String,
        @Field("transfer_password") transferPassword: String,
    ): Response<ResponseWrapper<Void?>>
}