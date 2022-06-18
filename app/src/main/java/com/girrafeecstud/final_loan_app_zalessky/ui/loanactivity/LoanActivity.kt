package com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.girrafeecstud.final_loan_app_zalessky.R

class LoanActivity : AppCompatActivity() {

    private lateinit var actiobBarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)

        actiobBarTitle = findViewById(R.id.actionBarTitle)

        // TODO ВЫНЕСТИ КОНСТАНТУ ОТДЕЛЬНО В КОНФИГ
        val loanId = intent.getLongExtra("LOAN_ID", 0)
        val actionBarTitleValue = intent.getStringExtra("ACTION_BAR_TITLE")

        actiobBarTitle.setText(actionBarTitleValue)

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