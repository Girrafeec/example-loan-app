package com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.utils.BundleConfig

class LoanActivity : AppCompatActivity() {

    private lateinit var actiobBarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)

        actiobBarTitle = findViewById(R.id.actionBarTitle)

        val loanId = intent.getLongExtra(BundleConfig.LOAN_ID_BUNDLE, 0)
        val actionBarTitleValue = intent.getStringExtra(BundleConfig.ACTION_BAR_TITLE_BUNDLE)

        actiobBarTitle.setText(actionBarTitleValue)

        val fragmentBundle = Bundle()
        fragmentBundle.putLong(BundleConfig.LOAN_ID_BUNDLE, loanId)
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