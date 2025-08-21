package retrofit.calls

import androidx.lifecycle.lifecycleScope
import ir.ncis.kpjapp.App
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.models.BaseContent

object Base {
    suspend fun content(onSuccess: (content: BaseContent) -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            val response = ApiClient.API.baseContent()
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
                        App.ACTIVITY.lifecycleScope.launch { content(onSuccess, onError) }
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