package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import helpers.ContextHelper
import helpers.UiHelper
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep1Binding
import viewmodels.StepViewModel

class RequestFormStep1Fragment(private val viewModel: StepViewModel) : Fragment() {
    private lateinit var b: FragmentRequestFormStep1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentRequestFormStep1Binding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiHelper.setContent(b, requireActivity())

        b.form.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            b.form.btNext.setBackgroundColor(ContextHelper.getColor(if (isChecked) R.color.green else R.color.dark_gray))
            b.form.btNext.setOnClickListener {
                if (isChecked) {
                    viewModel.step.value = 2
                }
            }
        }
    }
}
