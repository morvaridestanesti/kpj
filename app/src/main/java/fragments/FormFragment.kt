package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import helpers.ContextHelper
import helpers.UiHelper
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.FragmentFormBinding
import viewmodels.StepViewModel

class FormFragment : Fragment() {
    private lateinit var b: FragmentFormBinding
    private val stepViewModel: StepViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentFormBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiHelper.setContent(b, requireActivity())

        observe()
    }

    private fun observe() {
        val colorGray = ContextHelper.getColor(R.color.gray)
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
                    b.cvStep1.strokeColor = colorGreen
                    b.cvStep2.strokeColor = colorGray
                    b.cvStep3.strokeColor = colorGray
                    b.cvStep4.strokeColor = colorGray
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
                    b.cvStep1.strokeColor = colorGray
                    b.cvStep2.strokeColor = colorGreen
                    b.cvStep3.strokeColor = colorGray
                    b.cvStep4.strokeColor = colorGray
                    b.tvStep1.setTextColor(colorBlack)
                    b.tvStep2.setTextColor(colorWhite)
                    b.tvStep3.setTextColor(colorBlack)
                    b.tvStep4.setTextColor(colorBlack)
                    childFragmentManager.beginTransaction().replace(b.formFragment.id, RequestFormStep2Fragment(stepViewModel)).commit()
                }

                3 -> {
                    b.tvStep1Label.setTextColor(colorBlack)
                    b.tvStep2Label.setTextColor(colorBlack)
                    b.tvStep3Label.setTextColor(colorGreen)
                    b.tvStep4Label.setTextColor(colorBlack)
                    b.cvStep1.setCardBackgroundColor(colorWhite)
                    b.cvStep2.setCardBackgroundColor(colorWhite)
                    b.cvStep3.setCardBackgroundColor(colorGreen)
                    b.cvStep4.setCardBackgroundColor(colorWhite)
                    b.cvStep1.strokeColor = colorGray
                    b.cvStep2.strokeColor = colorGray
                    b.cvStep3.strokeColor = colorGreen
                    b.cvStep4.strokeColor = colorGray
                    b.tvStep1.setTextColor(colorBlack)
                    b.tvStep2.setTextColor(colorBlack)
                    b.tvStep3.setTextColor(colorWhite)
                    b.tvStep4.setTextColor(colorBlack)
                    childFragmentManager.beginTransaction().replace(b.formFragment.id, RequestFormStep3Fragment(stepViewModel)).commit()
                }
            }
        }
    }
}