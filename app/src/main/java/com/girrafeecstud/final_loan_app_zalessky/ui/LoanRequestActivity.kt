package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.girrafeecstud.final_loan_app_zalessky.R

class LoanRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_request)

        supportFragmentManager.beginTransaction().replace(R.id.loanRequestContainer, LoanConditionsFragment()).commit()
    }
}