package fragments

import activities.MainActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep1Binding
import java.time.LocalDate
import java.time.Period

class RequestFormStep1Fragment : Fragment() {
    private lateinit var b: FragmentRequestFormStep1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentRequestFormStep1Binding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(buildString {
            append(App.BASE_URL)
            append(App.PLAN.company.logo)
        }).into(b.form.information.ivCompany)

        b.form.information.tvCompany.text = App.PLAN.company.name

        b.form.information.tvAgeLabel.text = App.CONTENT.inquiryAge

        val birthday = LocalDate.parse(App.BIRTHDAYS)
        val currentDate = LocalDate.now()
        val age = Period.between(birthday, currentDate).years
        b.form.information.tvAge.text = age.toString()

        b.form.information.tvInsuranceLabel.text = App.CONTENT.inquiryCover
        b.form.information.tvInsurance.text = buildString {
            append("$")
            append(App.INSURANCE_COVER)
        }

        b.form.information.tvDayLabel.text = App.CONTENT.inquiryDays
        val start = LocalDate.parse(App.START_AT)
        val end = LocalDate.parse(App.END_AT)
        val days = Period.between(start, end).days + 1
        b.form.information.tvDay.text = days.toString()

        b.form.cbCheck.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) R.color.green else R.color.dark_gray
            b.form.btNext.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), color)

            b.form.btNext.setOnClickListener {
                if (isChecked) {
                    (requireActivity() as MainActivity).showFragment(RequestFormStep2Fragment())
                }
            }
        }
    }
}
