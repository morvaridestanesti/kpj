package activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fragments.CalculatorFormFragment
import ir.ncis.kpjapp.ActivityEnhanced
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Base

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
                showFragment(CalculatorFormFragment())
            })
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(b.fragment.id, fragment).commit()
    }
}