package helpers

import android.app.DatePickerDialog
import ir.ncis.kpjapp.App
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import kotlin.math.absoluteValue

object CalendarHelper {
    fun showDatePicker(selectedDate: String? = null, onDateSelected: ((String) -> Unit)? = null) {
        val calendar = Calendar.getInstance()
        var year: Int
        var month: Int
        var day: Int
        if (selectedDate.isNullOrBlank()) {
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
        } else {
            val date = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            year = date.year
            month = date.monthValue - 1
            day = date.dayOfMonth
        }

        val datePicker = DatePickerDialog(
            App.ACTIVITY,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format(
                    Locale.US,
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                onDateSelected?.invoke(date)
            },
            year,
            month,
            day,
        )
        datePicker.show()
    }

    fun daysBetween(start: String, end: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDate = LocalDate.parse(start, formatter)
        val endDate = LocalDate.parse(end, formatter)
        return ChronoUnit.DAYS.between(startDate, endDate).absoluteValue.toInt() + 1
    }
}