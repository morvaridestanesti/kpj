package helpers

import androidx.core.content.ContextCompat
import ir.ncis.kpjapp.App

object ContextHelper {
    fun getColor(colorResId: Int): Int = ContextCompat.getColor(App.ACTIVITY, colorResId)
}