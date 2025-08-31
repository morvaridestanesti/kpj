package fragments

import activities.MainActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.FragmentRequestFormStep1Binding

class RequestFormStep1Fragment : Fragment() {
    private lateinit var b: FragmentRequestFormStep1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentRequestFormStep1Binding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.form.cbCheck.setOnCheckedChangeListener { _, isChecked ->
            b.form.btNext.isEnabled = isChecked

            val color = if (isChecked) R.color.green else R.color.gray
            b.form.btNext.backgroundTintList = AppCompatResources.getColorStateList(requireContext(),color)
        }

        b.form.btNext.setOnClickListener {
            (requireActivity() as MainActivity).showFragment(RequestFormStep2Fragment())
        }
    }
}
