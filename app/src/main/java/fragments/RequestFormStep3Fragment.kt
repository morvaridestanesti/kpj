package fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.ContextHelper
import helpers.UiHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
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
import java.util.Locale

class RequestFormStep3Fragment(private val viewModel: StepViewModel) : Fragment() {
    private lateinit var b: FragmentRequestFormStep3Binding
    private val allCheckBoxes = mutableListOf<CheckBox>()

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
            b.form.vgPlans.removeAllViews()
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
            supportedPlans.forEach { birthdays, plan ->
                b.form.vgPlans.addView(createPlanSelectionCardView(requireActivity(), plan.find { supportedPlan -> supportedPlan.company.id == App.PLAN.company.id }!!))
            }
            b.form.btNext.setOnClickListener { viewModel.step.value = 4 }
            b.form.btBack.setOnClickListener { viewModel.step.value = 2 }
        }
    }

    private fun createPlanSelectionCardView(context: Context, supportedPlan: SupportedPlan): CardView {
        val colorBlack = ContextHelper.getColor(R.color.black)
        val colorGray = ContextHelper.getColor(R.color.gray)
        val colorGreen = ContextHelper.getColor(R.color.green)
        val colorWhite = ContextHelper.getColor(R.color.white)
        val dimen2dp = (2 * App.ACTIVITY.resources.displayMetrics.density).toInt()
        val dimen4dp = (4 * App.ACTIVITY.resources.displayMetrics.density).toInt()
        val dimen8dp = (8 * App.ACTIVITY.resources.displayMetrics.density).toInt()
        val dimen16dp = (16 * App.ACTIVITY.resources.displayMetrics.density).toInt()

        val cardView = CardView(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(dimen4dp, dimen8dp, dimen4dp, dimen8dp)
            }
            elevation = 2f
            radius = dimen16dp.toFloat()
            setCardBackgroundColor(colorGray)
        }

        val checkBoxes = mutableListOf<CheckBox>()

        val tableLayout = TableLayout(context).apply {
            layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        }

        val headerRow = TableLayout(context).apply {
            setBackgroundColor(colorGray)
            layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        }
        val columns = supportedPlan
            .plans
            .map { it.plan.name }
            .toMutableList()
        columns.add(0, App.CONTENT.inquiryStep3Deductible)
        columns.forEachIndexed { index, title ->
            headerRow.addView(TextView(context).apply {
                text = title
                setBackgroundColor(colorGreen)
                setPadding(0, dimen16dp, 0, dimen16dp)
                setTextColor(colorWhite)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f).apply {
                    if (index < columns.size - 1) setMargins(0, 0, dimen2dp, 0)
                }
                gravity = Gravity.CENTER
            })
        }
        tableLayout.addView(headerRow)

        supportedPlan.deductibles.forEach { deductible ->
            val tabelRow = TableRow(context).apply {
                setBackgroundColor(colorGray)
                layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            }

            tabelRow.addView(TextView(context).apply {
                text = buildString {
                    append("$")
                    append(deductible.amountValue)
                }
                gravity = Gravity.CENTER
                layoutParams = TableRow.LayoutParams(0, ActionBar.LayoutParams.MATCH_PARENT, 1f).apply {
                    setMargins(0, 0, dimen2dp, 0)
                }
                setBackgroundColor(colorWhite)
                setTextColor(colorBlack)
                setPadding(0, dimen16dp, 0, dimen16dp)
            })

            supportedPlan.plans.forEachIndexed { index, currentPlan ->
                val dailyCost = currentPlan.prices[0].dailyCost!! * (1 - deductible.discountValue / 100F)
                val totalCost = currentPlan.prices[0].total!! * (1 - deductible.discountValue / 100F)
                CheckBox(context)
                    .apply {
                        text = buildString {
                            append(String.format(Locale.US, "$%.2f", dailyCost))
                            append("\n")
                            append(String.format(Locale.US, "$%02f", totalCost))
                        }
                        buttonTintList = ColorStateList.valueOf(colorGreen)
                        gravity = Gravity.CENTER
                        layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f).apply {
                            if (index < columns.size - 1) setMargins(0, 0, dimen2dp, 0)
                        }
                        setBackgroundColor(colorWhite)
                        setTextColor(colorBlack)
                        setPadding(0, dimen16dp, 0, dimen16dp)
                        tag = buildString {
                            append(deductible.id)
                            append("-")
                            append(currentPlan.prices[0].id)
                        }
                    }
                    .also {
                        allCheckBoxes += it
                        checkBoxes += it
                        tabelRow.addView(it)
                    }
            }

            tableLayout.addView(tabelRow)
        }

        cardView.addView(tableLayout)

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { button, fromUser ->
                if (button.isChecked && fromUser) {
                    checkBoxes.filter { it != checkBox }.forEach { it.isChecked = false }
                }
                val deductibleIds = mutableListOf<Int>()
                val priceIds = mutableListOf<Int>()
                allCheckBoxes.filter { it.isChecked }.forEach {
                    val ids = (it.tag as String).split("-").toList()
                    deductibleIds += ids[0].toInt()
                    priceIds += ids[1].toInt()
                }
                App.DTO.deductibleIds = deductibleIds.joinToString { "," }
                App.DTO.priceIds = priceIds .joinToString { "," }
            }
        }
        return cardView
    }
}
