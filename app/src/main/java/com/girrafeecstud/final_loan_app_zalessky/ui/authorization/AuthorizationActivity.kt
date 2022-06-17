package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.AuthorizationViewModel
import com.girrafeecstud.final_loan_app_zalessky.ui.dialog.ErrorDialogFragment
import com.google.android.material.tabs.TabLayout

class AuthorizationActivity :
    AppCompatActivity(),
    ErrorDialogFragment.ErrorDialogListener {

    private val authorizationViewModel: AuthorizationViewModel by viewModels {
        (applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUiValues()

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.loginActivityContainer,
                LoginFragment()
            )
            .commit()

        subscribeObservers()
    }

    // Just dismiss alert dialog when press ok
    override fun errorOk() {
    }

    private fun subscribeObservers() {
        authorizationViewModel.isUserAuthorized().observe(this, { isUserAuthorized ->
            when (isUserAuthorized) {
                true -> {
                    startMainActivity()
                }
            }
        })
    }

    private fun initUiValues() {}

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}