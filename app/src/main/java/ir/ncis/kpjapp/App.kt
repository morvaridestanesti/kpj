package ir.ncis.kpjapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import helpers.KeyHelper

class App : Application() {
    companion object {
        lateinit var TOKEN: String
        lateinit var ACTIVITY: ActivityEnhanced
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        TOKEN = Hawk.get(KeyHelper.TOKEN, "")
    }
}