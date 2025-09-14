package activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fragments.FormFragment
import ir.ncis.kpjapp.ActivityEnhanced
import ir.ncis.kpjapp.App
import ir.ncis.kpjapp.R
import ir.ncis.kpjapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Base
import retrofit.calls.Inquiry

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
                // TODO: Remove these lines and uncomment the last line
                App.INSURANCE_COVER = 50000
                App.IS_ENTRY = 1
                App.BIRTHDAYS = "1982-11-28"
                App.START_AT = "2026-01-01"
                App.END_AT = "2026-01-01"
                App.DTO.apply {
                    address = "Test Address"
                    arrivedAt = "2026-01-01"
                    birthdays = "1982-11-28,1955-07-04,2010-01-01"
                    city = "Toronto"
                    email = "test@mysite.com"
                    endedAt = "2026-01-31"
                    firstNames = "Mohammad,Morvarid,FR"
                    genders = "Male,Female,Female"
                    insuranceCover = 50000
                    isEntry = 1
                    lastNames = "Shahraki,Estanesti,Sh"
                    phone = "12345"
                    postalCode = "67890"
                    provinceId = 1
                    startedAt = "2026-01-01"
                }
                lifecycleScope.launch {
                    Inquiry.getSupportedPlans(
                        App.INSURANCE_COVER,
                        App.IS_ENTRY,
                        App.BIRTHDAYS,
                        App.START_AT,
                        App.END_AT,
                        { plans ->
                            App.SUPPORTED_PLANS = plans
                            App.PLAN = plans[0]
                            showFragment(FormFragment())
                        })
                }
                // showFragment(CalculatorFormFragment())
            })
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(b.fragment.id, fragment).commit()
    }
}