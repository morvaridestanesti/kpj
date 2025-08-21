package ir.ncis.kpjapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class ActivityEnhanced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.ACTIVITY = this
    }

    override fun onResume() {
        super.onResume()
        App.ACTIVITY = this
    }
}