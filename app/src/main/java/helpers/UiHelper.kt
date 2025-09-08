package helpers

import adapters.AdapterRecyclerSupportedPlanCompany
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.squareup.picasso.Picasso
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentCalculatorFormBinding
import ir.ncis.kpjapp.databinding.FragmentCalculatorResultBinding
import ir.ncis.kpjapp.databinding.FragmentFormBinding
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep1Binding
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep2Binding
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

object UiHelper {
    fun setContent(b: ViewBinding, context: Context) {
        when (b) {
            is FragmentCalculatorFormBinding   -> {
                val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                b.form.tvTitle.text = App.CONTENT.calculatorFormTitle
                b.form.tvInsuranceLabel.text = App.CONTENT.calculatorFormEntry
                setupSpinner(b.form.spEntry, listOf(App.CONTENT.calculatorFormVisiting, App.CONTENT.calculatorFormTravel))
                b.form.tvBirthdayLabel.text = App.CONTENT.calculatorFormBirthday
                b.form.tvInsuranceCoverLabel.text = App.CONTENT.calculatorFormCover
                b.form.tvStartLabel.text = App.CONTENT.calculatorFormStart
                b.form.tvStart.text = currentDate
                b.form.tvEndLabel.text = App.CONTENT.calculatorFormEnd
                b.form.tvEnd.text = currentDate
                b.form.btCheck.text = App.CONTENT.calculatorFormSubmit
            }

            is FragmentCalculatorResultBinding -> {
                b.rvSupportedPlans.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                b.rvSupportedPlans.adapter = AdapterRecyclerSupportedPlanCompany(App.SUPPORTED_PLANS, CalendarHelper.daysBetween(App.START_AT, App.END_AT))
                b.form.tvTitle.text = App.CONTENT.calculatorFormTitle
                b.form.tvInsuranceLabel.text = App.CONTENT.calculatorFormEntry
                setupSpinner(b.form.spEntry, listOf(App.CONTENT.calculatorFormVisiting, App.CONTENT.calculatorFormTravel))
                b.form.tvBirthdayLabel.text = App.CONTENT.calculatorFormBirthday
                b.form.tvBirthday.text = App.BIRTHDAYS
                b.form.tvInsuranceCoverLabel.text = App.CONTENT.calculatorFormCover
                b.form.tvStartLabel.text = App.CONTENT.calculatorFormStart
                b.form.tvStart.text = App.START_AT
                b.form.tvEndLabel.text = App.CONTENT.calculatorFormEnd
                b.form.tvEnd.text = App.END_AT
                b.form.btCheck.text = App.CONTENT.calculatorFormSubmit
            }

            is FragmentFormBinding             -> {
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
            }

            is FragmentRequestFormStep1Binding -> {
                b.form.tvTitle.text = App.CONTENT.inquiryStep1Title
                b.form.tvDescription.text = App.CONTENT.inquiryStep1Description
                b.form.cbAgree.text = App.CONTENT.inquiryStep1Agreement
                b.form.btNext.text = App.CONTENT.inquiryNext
            }

            is FragmentRequestFormStep2Binding -> {
                b.form.tvTitle.text = App.CONTENT.inquiryStep2Title
                b.form.tvFamily.text = App.CONTENT.inquiryStep2Family
                setupSpinner(b.form.spFamily, listOf(App.CONTENT.inquiryStep2No, App.CONTENT.inquiryStep2Yes))
                b.form.tvPeople.text = App.CONTENT.inquiryStep2People
                setupSpinner(b.form.spPeople, listOf(1..10).map { it.toString() })
                b.form.tvAddress.text = App.CONTENT.inquiryStep2Cover
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
                setupSpinner(b.form.spGenderBeneficiaries, listOf(App.CONTENT.inquiryStep2Male, App.CONTENT.inquiryStep2Female))
                b.form.tvBirthdayLabelBeneficiaries.text = App.CONTENT.inquiryStep2Birthday
                b.form.tvPolicy.text = App.CONTENT.inquiryStep2Alert
                b.form.tvPolicyDate.text = App.CONTENT.inquiryStep2Policy
                b.form.tvInsuranceEffectiveDateLabel.text = App.CONTENT.inquiryStep2Start
                b.form.tvInsuranceEndDateLabel.text = App.CONTENT.inquiryStep2End
                b.form.tvArrivalLabel.text = App.CONTENT.inquiryStep2Arrival
            }
        }
    }

    fun setupSpinner(spinner: Spinner, items: List<String>) {
        spinner.adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, items)
    }
}