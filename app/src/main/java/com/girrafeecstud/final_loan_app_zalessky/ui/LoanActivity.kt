package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.girrafeecstud.final_loan_app_zalessky.R

class LoanActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)

        val loanId = intent.getLongExtra("LOAN_ID", 0)

        val fragmentBundle = Bundle()
        fragmentBundle.putLong("LOAN_ID", loanId)
        val loanDetailsFragment = LoanDetailsFragment()
        loanDetailsFragment.arguments = fragmentBundle

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.loanActivityContainer,
                loanDetailsFragment
            )
            .commit()
    }
}