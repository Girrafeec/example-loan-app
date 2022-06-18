package com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanItemViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState

class LoanDetailsFragment: Fragment(), View.OnClickListener {

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
    private lateinit var backButton: ImageButton

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
        backButton = requireActivity().findViewById(R.id.actionBarBackButton)

        subscribeObservers()

        backButton.setOnClickListener(this)

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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.actionBarBackButton -> {
                activity?.finish()
            }
        }
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

        val date = loanItemViewModel.getDateStringValue(dateTimeString = loan.loanIssueDate)
        val time = loanItemViewModel.getTimeStringValue(dateTimeString = loan.loanIssueDate)

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