package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.UiHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep3Binding
import ir.ncis.kpjapp.databinding.LayoutPersonInfoBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry
import retrofit.models.SupportedPlan
import viewmodels.StepViewModel

class RequestFormStep3Fragment(private val viewModel: StepViewModel) : Fragment() {
    private lateinit var b: FragmentRequestFormStep3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentRequestFormStep3Binding.inflate(inflater, container, false)
        return (b.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiHelper.setContent(b, requireActivity())

        lifecycleScope.launch {
            val firstNames = App.DTO.firstNames.split(",").dropLast(1)
            val lastNames = App.DTO.lastNames.split(",").dropLast(1)
            val genders = App.DTO.genders.split(",").dropLast(1)
            val birthdays = App.DTO.birthdays.split(",").dropLast(1)
            val handlers = mutableListOf<Deferred<Any>>()
            val supportedPlans = mutableMapOf<String, List<SupportedPlan>>()
            b.form.vgInsured.removeAllViews()
            (0 until firstNames.size).forEach { i ->
                val binding = LayoutPersonInfoBinding.inflate(layoutInflater)
                binding.tvFullNameLabel.text = buildString {
                    append(App.CONTENT.inquiryStep3FullName)
                    append(":")
                }
                binding.tvGenderLabel.text = buildString {
                    append(App.CONTENT.inquiryStep3Gender)
                    append(":")
                }
                binding.tvBirthdayLabel.text = buildString {
                    append(App.CONTENT.inquiryStep3Birthday)
                    append(":")
                }
                binding.tvFullName.text = buildString {
                    append(firstNames[i])
                    append(" ")
                    append(lastNames[i])
                }
                binding.tvGender.text = genders[i]
                binding.tvBirthday.text = birthdays[i]
                b.form.vgInsured.addView(binding.root)
            }
            val beneficiaryFirstName = App.DTO.firstNames.split(",").last()
            val beneficiaryLastName = App.DTO.lastNames.split(",").last()
            val beneficiaryGender = App.DTO.genders.split(",").last()
            val beneficiaryBirthday = App.DTO.birthdays.split(",").last()
            b.form.beneficiary.tvFullName.text = buildString {
                append(beneficiaryFirstName)
                append(" ")
                append(beneficiaryLastName)
            }
            b.form.beneficiary.tvGender.text = beneficiaryGender
            b.form.beneficiary.tvBirthday.text = beneficiaryBirthday
            b.form.tvInsuranceEffectiveDate.text = App.DTO.startedAt
            b.form.tvInsuranceEndDate.text = App.DTO.endedAt
            b.form.tvArrival.text = App.DTO.arrivedAt
            birthdays.forEach { birthday ->
                handlers += async(Dispatchers.IO) {
                    Inquiry.getSupportedPlans(
                        App.DTO.insuranceCover,
                        App.DTO.isEntry,
                        birthday,
                        App.DTO.startedAt,
                        App.DTO.endedAt,
                        { supportedPlans.put(birthday, it) }
                    )
                }
            }
            handlers.awaitAll()
            Toast.makeText(requireActivity(), supportedPlans.size.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}