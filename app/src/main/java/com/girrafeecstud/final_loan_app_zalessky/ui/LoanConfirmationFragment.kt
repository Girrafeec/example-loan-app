package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanConfirmationViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanRequestActivityViewModel
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanRequestActivityConfig
import kotlinx.android.synthetic.main.activity_loan_request.*

class LoanConfirmationFragment : Fragment(), View.OnClickListener {

    private lateinit var amountValue: TextView
    private lateinit var periodValue: TextView
    private lateinit var percentValue: TextView
    private lateinit var firstNameValue: TextView
    private lateinit var lastNameValue: TextView
    private lateinit var phoneNumberValue: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var listener: LoanConfirmationFragmentListener

    private val loanConfirmationViewModel: LoanConfirmationViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    private val loanRequestActivityViewModel: LoanRequestActivityViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is LoanConfirmationFragmentListener -> {
                listener = context
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loan_confirmation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener.enableApplyLoanRequestButton()

        progressBar = requireActivity().findViewById(R.id.requestActivityProgressBar)
        val applyLoanBtn = requireActivity().findViewById<Button>(R.id.applyLoanBtn)
        amountValue = view.findViewById(R.id.loanRequestAmountValueTxt)
        periodValue = view.findViewById(R.id.loanRequestPeriodValueTxt)
        percentValue = view.findViewById(R.id.loanRequestPercentValueTxt)
        firstNameValue = view.findViewById(R.id.loanRequestFirstNameValueTxt)
        lastNameValue = view.findViewById(R.id.loanRequestLastNameValueTxt)
        phoneNumberValue = view.findViewById(R.id.loanRequestPhoneNumberValueTxt)

        applyLoanBtn.setOnClickListener(this)

        getLoanRequestData()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.applyLoanBtn -> {
                applyLoan()
            }
        }
    }

    private fun getLoanRequestData() {
        amountValue.setText(loanRequestActivityViewModel.getLoanAmountValue().value.toString())
        periodValue.setText(loanRequestActivityViewModel.getLoanPeriodValue().value.toString())
        percentValue.setText(loanRequestActivityViewModel.getLoanPercentValue().value.toString())
        firstNameValue.setText(loanRequestActivityViewModel.getFirstNameValue().value)
        lastNameValue.setText(loanRequestActivityViewModel.getLastNameValue().value)
        phoneNumberValue.setText(loanRequestActivityViewModel.getPhoneNumberValue().value)
    }

    private fun applyLoan() {
        loanConfirmationViewModel.applyLoan(
            LoanRequest(
                loanAmount = amountValue.text.toString().toDouble(),
                loanPeriod = periodValue.text.toString().toInt(),
                loanPercent = percentValue.text.toString().toDouble(),
                borrowerFirstName = firstNameValue.text.toString(),
                borrowerLastName = lastNameValue.text.toString(),
                borrowerPhoneNumber = phoneNumberValue.text.toString()
            )
        )
    }

    interface LoanConfirmationFragmentListener {
        fun enableApplyLoanRequestButton()
    }

}