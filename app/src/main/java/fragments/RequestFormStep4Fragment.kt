package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import helpers.UiHelper
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep4Binding
import kotlinx.coroutines.launch
import retrofit.calls.Inquiry

class RequestFormStep4Fragment: Fragment() {
    private lateinit var b: FragmentRequestFormStep4Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentRequestFormStep4Binding.inflate(inflater,container,false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiHelper.setContent(b, requireActivity())

        lifecycleScope.launch {
            val deductibleIds = App.DTO.deductibleIds.split(",").toMutableList()
            App.DTO.deductibleIds = (deductibleIds + deductibleIds.last()).joinToString(",")
            val priceIds = App.DTO.priceIds.split(",").toMutableList()
            App.DTO.priceIds = (priceIds + priceIds.last()).joinToString(",")
            Inquiry.submit(

            )
        }
    }
}