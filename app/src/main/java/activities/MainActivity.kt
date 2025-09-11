package activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fragments.RequestFormStep2Fragment
import ir.ncis.kpjapp.ActivityEnhanced
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Base
import viewmodels.StepViewModel

class MainActivity : ActivityEnhanced() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        if (App.TOKEN.isEmpty()) {
            lifecycleScope.launch {
                Auth.login(
                    username = getString(R.string.username),
                    password = getString(R.string.password),
                    onSuccess = { setupUi() }
                )
            }
        } else {
            setupUi()
        }
    }

    private fun setupUi() {
        lifecycleScope.launch {
            Base.content({
                App.CONTENT = it
                val viewModel: StepViewModel by viewModels()
                showFragment(RequestFormStep2Fragment(viewModel))
            })
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(b.fragment.id, fragment).commit()
    }
}