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
    suspend fun inquiry(@Field("insurance_cover") insuranceCover: String, @Field("is_entry") isEntry: Int, @Field("birthdays") birthdays: String, @Field("start_at") startAt: String, @Field("end_at") endAt: String): Response<ResponseWrapper<Inquiry>>

    @OPTIONS("inquiry")
    suspend fun inquiryOptions(): Response<ResponseWrapper<InquiryOption>>
}