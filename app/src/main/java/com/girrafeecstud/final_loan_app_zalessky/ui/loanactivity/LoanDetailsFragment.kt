package com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanItemViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState

class LoanDetailsFragment: Fragment() {

    private lateinit var amountValue: TextView
    private lateinit var periodValue: TextView
    private lateinit var percentValue: TextView
    private lateinit var firstNameValue: TextView
    private lateinit var lastNameValue: TextView
    private lateinit var phoneNumberValue: TextView
    private lateinit var idValue: TextView
    private lateinit var stateValue: TextView
    private lateinit var dateTimeValue: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshLayout: SwipeRefreshLayout

    private val loanItemViewModel: LoanItemViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var id = this.arguments?.getLong("LOAN_ID")
        if (id == null)
            id = 0
        loanItemViewModel.setLoanId(loanId = id)
        return inflater.inflate(R.layout.layout_loan_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loanItemViewModel.loadLocalLoanData()
        loanItemViewModel.loadRemoteLoanData()

        progressBar = requireActivity().findViewById(R.id.loanActivityProressBar)
        amountValue = view.findViewById(R.id.loanDetailsAmountValueTxt)
        periodValue = view.findViewById(R.id.loanDetailsPeriodValueTxt)
        percentValue = view.findViewById(R.id.loanDetailsPercentValueTxt)
        firstNameValue = view.findViewById(R.id.loanDetailsFirstNameValueTxt)
        lastNameValue = view.findViewById(R.id.loanDetailsLastNameValueTxt)
        phoneNumberValue = view.findViewById(R.id.loanDetailsPhoneNumberValueTxt)
        idValue = view.findViewById(R.id.loanDetailsIdValueTxt)
        stateValue = view.findViewById(R.id.loanDetailsStateValueTxt)
        dateTimeValue = view.findViewById(R.id.loanDetailsDateTimeValueTxt)
        refreshLayout = requireActivity().findViewById(R.id.refreshLoanDetailsLayout)

        subscribeObservers()

        refreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                loanItemViewModel.loadRemoteLoanData()
            }
        })

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun subscribeObservers() {

        loanItemViewModel.getLoan().observe(viewLifecycleOwner, { loan ->
            handleSuccessResult(loan = loan)
        })

        loanItemViewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is MainState.IsLoading -> handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(loan = state.data as Loan)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })
    }

    private fun handleSuccessResult(loan: Loan) {
        amountValue.setText(loan.loanAmount.toString())
        percentValue.setText(loan.loanPercent.toString())
        periodValue.setText(loan.loanPeriod.toString())
        firstNameValue.setText(loan.borrowerFirstName)
        lastNameValue.setText(loan.borrowerLastName)
        phoneNumberValue.setText(loan.borrowerPhoneNumber)
        idValue.setText(loan.loanId.toString())
        dateTimeValue.setText(loan.loanIssueDate)
        stateValue.setText(loan.loanState.name)
    }

    private fun handleLoading(isLoading: Boolean) {
        when (isLoading) {
            false -> {
                view?.alpha = (1).toFloat()
                view?.isEnabled = !isLoading
                progressBar.alpha = (0).toFloat()
                refreshLayout.isRefreshing = false
            }
            true -> {
                view?.alpha = (0).toFloat()
                view?.isEnabled = !isLoading
                progressBar.alpha = (1).toFloat()
            }
        }
    }

    private fun handleError(apiError: ApiError) {

        var errorMessage = apiError.errorType.name

        when (apiError.errorType) {
            ApiErrorType.BAD_REQUEST_ERROR -> {

            }
            ApiErrorType.UNAUTHORIZED_ERROR -> {

            }
            ApiErrorType.RESOURCE_FORBIDDEN_ERROR -> {

            }
            ApiErrorType.NOT_FOUND_ERROR -> {

            }
            ApiErrorType.NO_CONNECTION_ERROR -> {

            }
            ApiErrorType.TIMEOUT_EXCEEDED_ERROR -> {

            }
            ApiErrorType.UNKNOWN_ERROR -> {

            }
        }
        Toast.makeText(activity?.applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

}