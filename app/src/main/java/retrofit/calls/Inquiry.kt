package retrofit.calls

import androidx.lifecycle.lifecycleScope
import dtos.DataCollection
import ir.ncis.kpjapp.App
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.models.InquiryOption
import retrofit.models.SupportedPlan

object Inquiry {
    suspend fun getSupportedPlans(insuranceCover: Int, isEntry: Int, birthDay: String, startAt: String, endAt: String, onSuccess: (supportedPlans: List<SupportedPlan>) -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            val response = ApiClient.API.inquiryGetSupportedPlans(insuranceCover, isEntry, birthDay, startAt, endAt)
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
                        App.ACTIVITY.lifecycleScope.launch { getSupportedPlans(insuranceCover, isEntry, birthDay, startAt, endAt, onSuccess, onError) }
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

    suspend fun submit(dto: DataCollection, onSuccess: () -> Unit, onError: ((Exception) -> Unit)) {
        try {
            val response = ApiClient.API.submit(
                dto.address,
                dto.arrivedAt,
                dto.birthdays,
                dto.cardCVV,
                dto.cardExpiration,
                dto.cardName,
                dto.cardNumber,
                dto.city,
                dto.deductibleIds,
                dto.email,
                dto.endedAt,
                dto.firstNames,
                dto.genders,
                dto.insuranceCover,
                dto.isEntry,
                dto.lastNames,
                dto.message,
                dto.phone,
                dto.postalCode,
                dto.priceIds,
                dto.provinceId,
                dto.startedAt,
                dto.transferPassword
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    onSuccess()
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                if (response.code() == 401) {
                    Auth.refresh({
                        App.ACTIVITY.lifecycleScope.launch { submit(dto, onSuccess, onError) }
                    })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}