package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.AuthorizationViewModel

class StartActivity : AppCompatActivity() {

    private val authorizationViewModel: AuthorizationViewModel by viewModels {
        (applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        authorizationViewModel.isUserAuthorized().observe(this, { isUserAuthorized ->
            when (isUserAuthorized) {
                true -> {
                    startMainActivity()
                }
                false -> {
                    startAuthActivity()
                }
            }
        })
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startAuthActivity() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }

}