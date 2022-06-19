package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.girrafeecstud.final_loan_app_zalessky.R

class LoanRequestActivity :
    AppCompatActivity(),
    LoanConditionsFragment.LoanConditionsFragmentListener,
    LoanConfirmationFragment.LoanConfirmationFragmentListener,
    LoanRequestSuccessFragment.LoanRequestSuccessFragmentListener {

    private lateinit var continueBtn: Button
    private lateinit var applyLoanBtn: Button
    private lateinit var okButton: Button
    private lateinit var actionBar: ConstraintLayout

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
        okButton = findViewById(R.id.loanRequestOkBtn)
        actionBar = findViewById(R.id.loanRequestActionBar)
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

    override fun enableOkButton() {
        continueBtn.visibility = View.INVISIBLE
        continueBtn.isEnabled = false
        applyLoanBtn.visibility = View.INVISIBLE
        applyLoanBtn.isEnabled = false
        okButton.visibility = View.VISIBLE
        okButton.isEnabled = true
    }

    override fun disableActionBar() {
        actionBar.visibility = View.GONE
    }
}