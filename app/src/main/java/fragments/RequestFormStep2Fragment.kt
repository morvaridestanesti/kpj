package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.CalendarHelper
import helpers.UiHelper
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep2Binding
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry
import viewmodels.StepViewModel
import java.text.NumberFormat
import java.util.Locale

class RequestFormStep2Fragment(private val viewModel: StepViewModel) : Fragment() {
    private lateinit var b: FragmentRequestFormStep2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentRequestFormStep2Binding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiHelper.setContent(b, requireActivity())

        lifecycleScope.launch {
            Inquiry.getInquiryFormOptions({
                UiHelper.setupSpinner(b.form.spAmount, it.insuranceCovers.map { cover -> NumberFormat.getNumberInstance(Locale.US).format(cover) })
                UiHelper.setupSpinner(b.form.spProvince, it.provinces.map { province -> province.name })
            })
        }

        b.form.cvBirthdayBeneficiaries.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvBirthdayBeneficiaries.text.toString()) { b.form.tvBirthdayBeneficiaries.text = it } }
        b.form.cvInsuranceEffectiveDate.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvInsuranceEffectiveDate.text.toString()) { b.form.tvInsuranceEffectiveDate.text = it } }
        b.form.cvInsuranceEndDate.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvInsuranceEndDate.text.toString()) { b.form.tvInsuranceEndDate.text = it } }
        b.form.cvArrival.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvArrival.text.toString()) { b.form.tvArrival.text = it } }
        b.form.btNext.setOnClickListener { viewModel.step.value = 3 }
        b.form.btBack.setOnClickListener { viewModel.step.value = 1 }
    }
}