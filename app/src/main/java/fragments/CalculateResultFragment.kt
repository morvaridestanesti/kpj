package fragments

import activities.MainActivity
import adapters.AdapterRecyclerSupportedPlanCompany
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import helpers.CalendarHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentCalculatorResultBinding
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry
import java.text.NumberFormat
import java.util.Locale

class CalculateResultFragment : Fragment() {
    private lateinit var b: FragmentCalculatorResultBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentCalculatorResultBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.rvSupportedPlans.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        b.rvSupportedPlans.adapter = AdapterRecyclerSupportedPlanCompany(App.SUPPORTED_PLANS, CalendarHelper.daysBetween(App.START_AT, App.END_AT))

        b.form.tvTitle.text = App.CONTENT.calculatorFormTitle
        b.form.tvInsuranceLabel.text = App.CONTENT.calculatorFormEntry
        b.form.spEntry.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, listOf(App.CONTENT.calculatorFormVisiting, App.CONTENT.calculatorFormTravel))
        b.form.tvBirthdayLabel.text = App.CONTENT.calculatorFormBirthday
        b.form.tvBirthday.text = App.BIRTHDAYS
        b.form.tvInsuranceCoverLabel.text = App.CONTENT.calculatorFormCover
        lifecycleScope.launch {
            Inquiry.getInquiryFormOptions({
                b.form.spInsuranceCover.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, it.insuranceCovers.map { cover -> NumberFormat.getNumberInstance(Locale.US).format(cover) })
            })
        }
        b.form.tvStartLabel.text = App.CONTENT.calculatorFormStart
        b.form.tvStart.text = App.START_AT
        b.form.tvEndLabel.text = App.CONTENT.calculatorFormEnd
        b.form.tvEnd.text = App.END_AT
        b.form.btCheck.text = App.CONTENT.calculatorFormSubmit
        b.form.cvBirthday.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvBirthday.text.toString()) { b.form.tvBirthday.text = it } }
        b.form.cvStart.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvStart.text.toString()) { b.form.tvStart.text = it } }
        b.form.cvEnd.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvEnd.text.toString()) { b.form.tvEnd.text = it } }

        b.form.btCheck.setOnClickListener {
            App.INSURANCE_COVER = b.form.spInsuranceCover.selectedItem.toString().replace(",", "").toInt()
            App.IS_ENTRY = if (b.form.spEntry.selectedItem.toString() == App.CONTENT.calculatorFormVisiting) 1 else 0
            App.BIRTHDAYS = b.form.tvBirthday.text.toString()
            App.START_AT = b.form.tvStart.text.toString()
            App.END_AT = b.form.tvEnd.text.toString()
            lifecycleScope.launch {
                Inquiry.getSupportedPlans({
                    App.SUPPORTED_PLANS = it
                    b.rvSupportedPlans.adapter = AdapterRecyclerSupportedPlanCompany(it, CalendarHelper.daysBetween(App.START_AT, App.END_AT))
                })
            }
        }
    }
}