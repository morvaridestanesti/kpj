package retrofit

import retrofit.models.BaseContent
import retrofit.models.Inquiry
import retrofit.models.InquiryOption
import retrofit.models.ResponseWrapper
import retrofit.models.Session
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
    suspend fun inquiry(@Query("insurance_cover") insuranceCover: String, @Query("is_entry") isEntry: Int,@Query("birthdays") birthdays: String, @Query("start_at") startAt: String, @Query("end_at") endAt: String): Response<ResponseWrapper<Inquiry>>

    @OPTIONS("inquiry")
    suspend fun inquiryOptions(): Response<ResponseWrapper<InquiryOption>>
}