package retrofit.calls

import androidx.lifecycle.lifecycleScope
import ir.ncis.kpjapp.App
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.models.InquiryOption
import retrofit.models.SupportedPlan

object Inquiry {
    suspend fun getSupportedPlans(onSuccess: (supportedPlans: List<SupportedPlan>) -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            val response = ApiClient.API.inquiryGetSupportedPlans(App.INSURANCE_COVER, App.IS_ENTRY, App.BIRTHDAYS, App.START_AT, App.END_AT)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        onSuccess(body.result)
                    } else {
                        onError?.invoke(Exception(body.message ?: "خطای ناشناخته"))
                    }
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                if (response.code() == 401) {
                    Auth.refresh({
                        App.ACTIVITY.lifecycleScope.launch { getSupportedPlans(onSuccess, onError) }
                    })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "خطای ناشناخته"
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun getInquiryFormOptions(onSuccess: (options: InquiryOption) -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            val response = ApiClient.API.inquiryFormOptions()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        onSuccess(body.result)
                    } else {
                        onError?.invoke(Exception(body.message ?: "خطای ناشناخته"))
                    }
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                if (response.code() == 401) {
                    Auth.refresh({
                        App.ACTIVITY.lifecycleScope.launch { getInquiryFormOptions(onSuccess, onError) }
                    })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "خطای ناشناخته"
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}