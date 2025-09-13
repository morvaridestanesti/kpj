package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import helpers.UiHelper
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep4Binding

class RequestFormStep4Fragment: Fragment() {
    private lateinit var b: FragmentRequestFormStep4Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentRequestFormStep4Binding.inflate(inflater,container,false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiHelper.setContent(b, requireActivity())
    }
}