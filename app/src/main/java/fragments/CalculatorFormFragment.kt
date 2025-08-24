package fragments

import activities.MainActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.CalendarHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentCalculatorFormBinding
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
            Inquiry.getInquiryFormOptions({
                b.spInsuranceCover.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, it.insuranceCovers.map { cover -> NumberFormat.getNumberInstance(Locale.US).format(cover) })
            })
        }
        b.tvStartLabel.text = App.CONTENT.calculatorFormStart
        b.tvStart.text = currentDate
        b.tvEndLabel.text = App.CONTENT.calculatorFormEnd
        b.tvEnd.text = currentDate
        b.btCheck.text = App.CONTENT.calculatorFormSubmit
        b.cvBirthday.setOnClickListener { CalendarHelper.showDatePicker(b.tvBirthday.text.toString()) { b.tvBirthday.text = it } }
        b.cvStart.setOnClickListener { CalendarHelper.showDatePicker(b.tvStart.text.toString()) { b.tvStart.text = it } }
        b.cvEnd.setOnClickListener { CalendarHelper.showDatePicker(b.tvEnd.text.toString()) { b.tvEnd.text = it } }

        b.btCheck.setOnClickListener {
            val insuranceCover = b.spInsuranceCover.selectedItem.toString().replace(",", "").toInt()
            val isEntry = if (b.spEntry.selectedItem.toString() == App.CONTENT.calculatorFormVisiting) 1 else 0
            val birthdays = b.tvBirthday.text.toString()
            val startAt = b.tvStart.text.toString()
            val endAt = b.tvEnd.text.toString()
            lifecycleScope.launch {
                Inquiry.getSupportedPlans(
                    insuranceCover,
                    isEntry,
                    birthdays,
                    startAt,
                    endAt,
                    {
                        App.SUPPORTED_PLANS = it
                        (requireActivity() as MainActivity).showFragment(CalculateResultFragment())
                    }
                )
            }
        }
    }
}
