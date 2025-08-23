package fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentCalculatorFormBinding
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class CalculatorFormFragment : Fragment() {
    private lateinit var b: FragmentCalculatorFormBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentCalculatorFormBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        b.tvTitle.text = App.CONTENT.calculatorFormTitle
        b.tvInsuranceLabel.text = App.CONTENT.calculatorFormEntry
        b.spEntry.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, listOf(App.CONTENT.calculatorFormVisiting, App.CONTENT.calculatorFormTravel))
        b.tvBirthdayLabel.text = App.CONTENT.calculatorFormBirthday
        b.tvBirthday.text = currentDate
        b.tvInsuranceCoverLabel.text = App.CONTENT.calculatorFormCover
        lifecycleScope.launch {
            Inquiry.options({
                b.spInsuranceCover.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, it.insuranceCovers)
            })
        }
        b.tvStartLabel.text = App.CONTENT.calculatorFormStart
        b.tvStart.text = currentDate
        b.tvEndLabel.text = App.CONTENT.calculatorFormEnd
        b.tvEnd.text = currentDate
        b.btCheck.text = App.CONTENT.calculatorFormSubmit
        b.cvBirthday.setOnClickListener {
            showDatePicker { date ->
                b.tvBirthday.text = date
            }
        }
        b.cvStart.setOnClickListener {
            showDatePicker { date ->
                b.tvStart.text = date
            }
        }
        b.cvEnd.setOnClickListener {
            showDatePicker { date ->
                b.tvEnd.text = date
            }
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format(Locale.US,
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                onDateSelected(date)
            },
            year,
            month,
            day,
        )
        datePicker.show()
    }
}
