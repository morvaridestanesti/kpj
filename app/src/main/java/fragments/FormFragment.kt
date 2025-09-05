package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import helpers.ContextHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.FragmentFormBinding
import viewmodels.StepViewModel
import java.time.LocalDate
import java.time.Period

class FormFragment : Fragment() {
    private lateinit var b: FragmentFormBinding
    private val stepViewModel: StepViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentFormBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(buildString {
            append(App.BASE_URL)
            append(App.PLAN.company.logo)
        }).into(b.information.ivCompany)

        b.information.tvCompany.text = App.PLAN.company.name

        b.information.tvAgeLabel.text = App.CONTENT.inquiryAge

        val birthday = LocalDate.parse(App.BIRTHDAYS)
        val currentDate = LocalDate.now()
        val age = Period.between(birthday, currentDate).years
        b.information.tvAge.text = age.toString()

        b.information.tvInsuranceLabel.text = App.CONTENT.inquiryCover
        b.information.tvInsurance.text = buildString {
            append("$")
            append(App.INSURANCE_COVER)
        }

        b.information.tvDayLabel.text = App.CONTENT.inquiryDays
        val start = LocalDate.parse(App.START_AT)
        val end = LocalDate.parse(App.END_AT)
        val days = Period.between(start, end).days + 1
        b.information.tvDay.text = days.toString()

        b.tvStep1Label.text = App.CONTENT.inquiryStep1Title
        b.tvStep2Label.text = App.CONTENT.inquiryStep2Title
        b.tvStep3Label.text = App.CONTENT.inquiryStep3Title
        b.tvStep4Label.text = App.CONTENT.inquiryStep4Title

        observe()
    }

    private fun observe() {
        val colorGreen = ContextHelper.getColor(R.color.green)
        val colorBlack = ContextHelper.getColor(R.color.black)
        val colorWhite = ContextHelper.getColor(R.color.white)

        stepViewModel.step.observe(requireActivity()) {
            when (it) {
                1 -> {
                    b.tvStep1Label.setTextColor(colorGreen)
                    b.tvStep2Label.setTextColor(colorBlack)
                    b.tvStep3Label.setTextColor(colorBlack)
                    b.tvStep4Label.setTextColor(colorBlack)
                    b.cvStep1.setCardBackgroundColor(colorGreen)
                    b.cvStep2.setCardBackgroundColor(colorWhite)
                    b.cvStep3.setCardBackgroundColor(colorWhite)
                    b.cvStep4.setCardBackgroundColor(colorWhite)
                    b.tvStep1.setTextColor(colorWhite)
                    b.tvStep2.setTextColor(colorBlack)
                    b.tvStep3.setTextColor(colorBlack)
                    b.tvStep4.setTextColor(colorBlack)
                    childFragmentManager.beginTransaction().replace(b.formFragment.id, RequestFormStep1Fragment(stepViewModel)).commit()
                }

                2 -> {
                    b.tvStep1Label.setTextColor(colorBlack)
                    b.tvStep2Label.setTextColor(colorGreen)
                    b.tvStep3Label.setTextColor(colorBlack)
                    b.tvStep4Label.setTextColor(colorBlack)
                    b.cvStep1.setCardBackgroundColor(colorWhite)
                    b.cvStep2.setCardBackgroundColor(colorGreen)
                    b.cvStep3.setCardBackgroundColor(colorWhite)
                    b.cvStep4.setCardBackgroundColor(colorWhite)
                    b.tvStep1.setTextColor(colorBlack)
                    b.tvStep2.setTextColor(colorWhite)
                    b.tvStep3.setTextColor(colorBlack)
                    b.tvStep4.setTextColor(colorBlack)
                    childFragmentManager.beginTransaction().replace(b.formFragment.id, RequestFormStep2Fragment()).commit()
                }
            }
        }
    }
}