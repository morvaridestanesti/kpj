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
            val birthdays = App.DTO.birthdays.split(",").dropLast(1)
            val handlers = mutableListOf<Deferred<Any>>()
            val supportedPlans = mutableMapOf<String, List<SupportedPlan>>()
            birthdays.forEach { birthday ->
                handlers += async(Dispatchers.IO) {
                    Inquiry.getSupportedPlans(
                        App.DTO.insuranceCover,
                        if (App.DTO.isEntry) 1 else 0,
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