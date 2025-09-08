package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.CalendarHelper
import ir.ncis.kpjapp.App
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

        b.form.tvTitle.text = App.CONTENT.inquiryStep2Title
        b.form.tvFamily.text = App.CONTENT.inquiryStep2Family
        b.form.spFamily.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, arrayOf(App.CONTENT.inquiryStep2No, App.CONTENT.inquiryStep2Yes))
        b.form.tvPeople.text = App.CONTENT.inquiryStep2People
        b.form.spPeople.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, arrayOf(1..10))
        b.form.tvAddress.text = App.CONTENT.inquiryStep2Cover
        lifecycleScope.launch {
            Inquiry.getInquiryFormOptions({
                b.form.spAmount.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, it.insuranceCovers.map { cover -> NumberFormat.getNumberInstance(Locale.US).format(cover) })
                b.form.spProvince.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, it.provinces.map { province -> province.name })
            })
        }
        b.form.tvAddress.text = App.CONTENT.inquiryStep2Address
        b.form.tvCity.text = App.CONTENT.inquiryStep2City
        b.form.tvProvince.text = App.CONTENT.inquiryStep2Province
        b.form.tvPostalCode.text = App.CONTENT.inquiryStep2PostalCode
        b.form.tvPhoneNumber.text = App.CONTENT.inquiryStep2Phone
        b.form.tvEmailAddress.text = App.CONTENT.inquiryStep2Email
        b.form.tvInsured.text = App.CONTENT.inquiryStep2Insured
        b.form.tvBeneficiaries.text = App.CONTENT.inquiryStep2Beneficiaries
        b.form.tvFirstNameBeneficiaries.text = App.CONTENT.inquiryStep2FirstName
        b.form.tvLastNameBeneficiaries.text = App.CONTENT.inquiryStep2LastName
        b.form.tvGenderBeneficiaries.text = App.CONTENT.inquiryStep2Gender
        b.form.spGenderBeneficiaries.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, arrayOf(App.CONTENT.inquiryStep2Male, App.CONTENT.inquiryStep2Female))
        b.form.tvBirthdayLabelBeneficiaries.text = App.CONTENT.inquiryStep2Birthday
        b.form.cvBirthdayBeneficiaries.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvBirthdayBeneficiaries.text.toString()) { b.form.tvBirthdayBeneficiaries.text = it } }
        b.form.tvPolicy.text = App.CONTENT.inquiryStep2Alert
        b.form.tvPolicyDate.text = App.CONTENT.inquiryStep2Policy
        b.form.tvInsuranceEffectiveDateLabel.text = App.CONTENT.inquiryStep2Start
        b.form.cvInsuranceEffectiveDate.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvInsuranceEffectiveDate.text.toString()) { b.form.tvInsuranceEffectiveDate.text = it } }
        b.form.tvInsuranceEndDateLabel.text = App.CONTENT.inquiryStep2End
        b.form.cvInsuranceEndDate.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvInsuranceEndDate.text.toString()) { b.form.tvInsuranceEndDate.text = it } }
        b.form.tvArrivalLabel.text = App.CONTENT.inquiryStep2Arrival
        b.form.cvArrival.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvArrival.text.toString()) { b.form.tvArrival.text = it } }
        b.form.btNext.setOnClickListener {
                viewModel.step.value = 3
        }
        b.form.btBack.setOnClickListener {
                viewModel.step.value = 1
        }
    }
}