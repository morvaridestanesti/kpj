package ir.ncis.kpjapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import helpers.KeyHelper
import retrofit.models.BaseContent
import retrofit.models.SupportedPlan

class App : Application() {
    companion object {
        const val BASE_URL = "https://kpjadmin.film-buff.ir/uploads/"
        lateinit var TOKEN: String
        lateinit var ACTIVITY: ActivityEnhanced

        lateinit var CONTENT: BaseContent
        lateinit var SUPPORTED_PLANS: List<SupportedPlan>
        lateinit var PLAN : SupportedPlan

        var INSURANCE_COVER: Int = 0
        var IS_ENTRY: Int = 1
        lateinit var BIRTHDAYS: String
        lateinit var START_AT: String
        lateinit var END_AT: String
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        TOKEN = Hawk.get(KeyHelper.TOKEN, "")
    }
}