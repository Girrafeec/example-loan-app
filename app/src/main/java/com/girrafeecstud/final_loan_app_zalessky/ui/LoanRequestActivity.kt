package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanRequestActivityViewModel

class LoanRequestActivity :
    AppCompatActivity(),
    LoanConditionsFragment.LoanConditionsFragmentListener,
    LoanConfirmationFragment.LoanConfirmationFragmentListener {

    private lateinit var continueBtn: Button
    private lateinit var applyLoanBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_request)
        initUiValues()

        supportFragmentManager.beginTransaction().replace(
            R.id.loanRequestContainer,
            LoanConditionsFragment()).commit()
    }

    private fun initUiValues() {
        continueBtn = findViewById(R.id.loanRequestContinueBtn)
        applyLoanBtn = findViewById(R.id.applyLoanBtn)
    }

    override fun enableContinueLoanRequestButton() {
        continueBtn.visibility = View.VISIBLE
        continueBtn.isEnabled = true
        applyLoanBtn.visibility = View.INVISIBLE
        applyLoanBtn.isEnabled = false
    }

    override fun enableApplyLoanRequestButton() {
        continueBtn.visibility = View.INVISIBLE
        continueBtn.isEnabled = false
        applyLoanBtn.visibility = View.VISIBLE
        applyLoanBtn.isEnabled = true
    }
}