package ir.ncis.kpjapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import helpers.KeyHelper
import retrofit.models.BaseContent

class App : Application() {
    companion object {
        lateinit var TOKEN: String
        lateinit var ACTIVITY: ActivityEnhanced

        lateinit var CONTENT: BaseContent
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        TOKEN = Hawk.get(KeyHelper.TOKEN, "")
    }
}