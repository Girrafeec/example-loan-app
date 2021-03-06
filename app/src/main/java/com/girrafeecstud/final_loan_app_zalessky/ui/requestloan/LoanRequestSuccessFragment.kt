package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanRequestActivityViewModel

class LoanRequestSuccessFragment : Fragment(), View.OnClickListener {

    private lateinit var amountValue: TextView
    private lateinit var periodValue: TextView
    private lateinit var percentValue: TextView
    private lateinit var firstNameValue: TextView
    private lateinit var lastNameValue: TextView
    private lateinit var phoneNumberValue: TextView
    private lateinit var idValue: TextView
    private lateinit var stateValue: TextView
    private lateinit var dateTimeValue: TextView

    private lateinit var listener: LoanRequestSuccessFragmentListener

    private val loanRequestActivityViewModel: LoanRequestActivityViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is LoanRequestSuccessFragmentListener -> {
                listener = context
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan_request_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable activity ok button
        listener.enableOkButton()
        // Make action bar gone
        listener.disableActionBar()

        val okButton = requireActivity().findViewById<Button>(R.id.loanRequestOkBtn)
        amountValue = view.findViewById(R.id.loanDetailsAmountValueTxt)
        periodValue = view.findViewById(R.id.loanDetailsPeriodValueTxt)
        percentValue = view.findViewById(R.id.loanDetailsPercentValueTxt)
        firstNameValue = view.findViewById(R.id.loanDetailsFirstNameValueTxt)
        lastNameValue = view.findViewById(R.id.loanDetailsLastNameValueTxt)
        phoneNumberValue = view.findViewById(R.id.loanDetailsPhoneNumberValueTxt)
        idValue = view.findViewById(R.id.loanDetailsIdValueTxt)
        stateValue = view.findViewById(R.id.loanDetailsStateValueTxt)
        dateTimeValue = view.findViewById(R.id.loanDetailsDateTimeValueTxt)

        okButton.setOnClickListener(this)

        val loan = loanRequestActivityViewModel.getLoan().value
        if (loan != null)
            setLoanValues(loan = loan)

        // Finish activity when press back button
        val backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                clearViewModelValues()
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallback)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loanRequestOkBtn -> {
                clearViewModelValues()
                activity?.finish()
            }
        }
    }

    private fun setLoanValues(loan: Loan) {

        val date = loanRequestActivityViewModel.getDateStringValue(dateTimeString = loan.loanIssueDate)
        val time = loanRequestActivityViewModel.getTimeStringValue(dateTimeString = loan.loanIssueDate)

        amountValue.setText(
            activity?.getString(
                R.string.loan_amount_value,
                loan.loanAmount.toString()
            )
        )
        percentValue.setText(
            activity?.getString(
                R.string.loan_percent_value,
                loan.loanPercent.toString()
            )
        )

        // Get last digit and choose correct period name value
        var periodStringValue = when (loan.loanPeriod % 10) {
            1 -> {
                if (loan.loanPeriod % 100 == 11)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_1)
            }
            2 -> {
                if (loan.loanPeriod % 100 == 12)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_2)
            }
            3 -> {
                if (loan.loanPeriod % 100 == 13)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_2)
            }
            4 -> {
                if (loan.loanPeriod % 100 == 14)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_2)
            }
            else -> {
                activity?.getString(R.string.period_day_3)
            }
        }

        periodValue.setText(
            activity?.getString(
                R.string.loan_period_value,
                loan.loanPeriod.toString(),
                periodStringValue
            )
        )

        firstNameValue.setText(loan.borrowerFirstName)
        lastNameValue.setText(loan.borrowerLastName)
        phoneNumberValue.setText(loan.borrowerPhoneNumber)
        idValue.setText(loan.loanId.toString())

        dateTimeValue.setText(
            activity?.getString(
                R.string.date_time_value,
                date,
                time
            )
        )

        when (loan.loanState) {
            LoanState.REGISTERED -> {
                stateValue.setText(
                    activity?.getString(R.string.request_state_registered)
                )
            }
            LoanState.APPROVED -> {
                stateValue.setText(
                    activity?.getString(R.string.request_state_approved)
                )
            }
            LoanState.REJECTED -> {
                stateValue.setText(
                    activity?.getString(R.string.request_state_rejected)
                )
            }
        }
    }

    private fun clearViewModelValues() {
        loanRequestActivityViewModel.setNullValues()
    }

    interface LoanRequestSuccessFragmentListener {
        fun enableOkButton()
        fun disableActionBar()
    }
}