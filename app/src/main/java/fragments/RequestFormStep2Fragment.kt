package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.CalendarHelper
import helpers.UiHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep2Binding
import ir.ncis.kpjapp.databinding.LayoutPersonInformationBinding
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry
import retrofit.models.Province
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

        lateinit var provinces: List<Province>

        lifecycleScope.launch {
            Inquiry.getInquiryFormOptions({
                provinces = it.provinces
                UiHelper.setupSpinner(b.form.spAmount, it.insuranceCovers.map { cover -> NumberFormat.getNumberInstance(Locale.US).format(cover) })
                UiHelper.setupSpinner(b.form.spProvince, it.provinces.map { province -> province.name })
            })
        }

        b.form.vgBeneficiary.cvBirthday.setOnClickListener { CalendarHelper.showDatePicker(b.form.vgBeneficiary.tvBirthday.text.toString()) { b.form.vgBeneficiary.tvBirthday.text = it } }
        b.form.cvInsuranceEffectiveDate.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvInsuranceEffectiveDate.text.toString()) { b.form.tvInsuranceEffectiveDate.text = it } }
        b.form.cvInsuranceEndDate.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvInsuranceEndDate.text.toString()) { b.form.tvInsuranceEndDate.text = it } }
        b.form.cvArrival.setOnClickListener { CalendarHelper.showDatePicker(b.form.tvArrival.text.toString()) { b.form.tvArrival.text = it } }
        b.form.btNext.setOnClickListener {
            App.DTO.insuranceCover = b.form.spAmount.selectedItem.toString().replace(Regex("[^0-9]"), "").toInt()
            App.DTO.address = b.form.etAddress.editableText.toString()
            App.DTO.city = b.form.etCity.editableText.toString()
            App.DTO.provinceId = provinces[b.form.spProvince.selectedItemPosition].id
            App.DTO.postalCode = b.form.etPostalCode.editableText.toString()
            App.DTO.phone = b.form.etPhoneNumber.editableText.toString()
            App.DTO.email = b.form.etEmailAddress.editableText.toString()
            val firstNames = mutableListOf<String>()
            val lastNames = mutableListOf<String>()
            val genders = mutableListOf<String>()
            val birthdays = mutableListOf<String>()
            for (i in 0 until b.form.vgInsuredPeopleInformation.childCount) {
                val layout = LayoutPersonInformationBinding.bind(b.form.vgInsuredPeopleInformation.getChildAt(i))
                firstNames += layout.etFirstName.editableText.toString()
                lastNames += layout.etLastName.editableText.toString()
                genders += if (layout.spGender.selectedItemPosition == 0) "Male" else "Female"
                birthdays += layout.tvBirthday.text.toString()
            }
            firstNames += b.form.vgBeneficiary.etFirstName.editableText.toString()
            lastNames += b.form.vgBeneficiary.etLastName.editableText.toString()
            genders += if (b.form.vgBeneficiary.spGender.selectedItemPosition == 0) "Male" else "Female"
            birthdays += b.form.vgBeneficiary.tvBirthday.text.toString()
            App.DTO.firstNames = firstNames.joinToString(",")
            App.DTO.lastNames = lastNames.joinToString(",")
            App.DTO.genders = genders.joinToString(",")
            App.DTO.birthdays = birthdays.joinToString(",")
            App.DTO.startedAt = b.form.tvInsuranceEffectiveDate.text.toString()
            App.DTO.endedAt = b.form.tvInsuranceEndDate.text.toString()
            App.DTO.arrivedAt = b.form.tvArrival.text.toString()
            viewModel.step.value = 3
        }
        b.form.btBack.setOnClickListener { viewModel.step.value = 1 }

        addPersonLayout(1)

        b.form.spPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, int: Long) {
                addPersonLayout(parent?.getItemAtPosition(position).toString().toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun addPersonLayout(number: Int) {
        b.form.vgInsuredPeopleInformation.removeAllViews()
        (0 until number).forEach { i ->
            b.form.vgInsuredPeopleInformation.addView(getLayoutPersonInformation())
        }
    }

    private fun getLayoutPersonInformation(): View {
        val binding = LayoutPersonInformationBinding.inflate(layoutInflater)
        binding.spGender.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, listOf(App.CONTENT.inquiryStep2Male, App.CONTENT.inquiryStep2Female))
        binding.cvBirthday.setOnClickListener { CalendarHelper.showDatePicker(binding.tvBirthday.text.toString()) { binding.tvBirthday.text = it } }
        return binding.root
    }
}