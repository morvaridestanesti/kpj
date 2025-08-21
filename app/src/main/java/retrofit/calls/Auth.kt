package retrofit.calls

import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import helpers.KeyHelper
import ir.ncis.kpjapp.App
import kotlinx.coroutines.launch
import retrofit.ApiClient

object Auth {
    suspend fun check(onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            val response = ApiClient.API.authCheck()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        onSuccess()
                    } else {
                        onError?.invoke(Exception(body.message ?: "خطای ناشناخته"))
                    }
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                if (response.code() == 401) {
                    refresh({
                        App.ACTIVITY.lifecycleScope.launch { check(onSuccess, onError) }
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

    suspend fun login(username: String, password: String, onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            val response = ApiClient.API.authLogin(username, password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.result != null) {
                    Hawk.put(KeyHelper.TOKEN, body.result.token)
                    Hawk.put(KeyHelper.REFRESH, body.result.refresh)
                    App.TOKEN = body.result.token
                    onSuccess()
                } else {
                    onError?.invoke(Exception(body?.message ?: "خطای ناشناخته"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "خطای ناشناخته"
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun refresh(onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null) {
        try {
            App.TOKEN = Hawk.get(KeyHelper.REFRESH, "")
            val response = ApiClient.API.authRefresh()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.result != null) {
                    Hawk.put(KeyHelper.TOKEN, body.result.token)
                    Hawk.put(KeyHelper.REFRESH, body.result.refresh)
                    App.TOKEN = body.result.token
                    onSuccess()
                } else {
                    onError?.invoke(Exception(body?.message ?: "خطای ناشناخته"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "خطای ناشناخته"
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}