package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanConfirmationViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanRequestActivityViewModel
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

        // Enable activity apply loan request button
        listener.enableApplyLoanRequestButton()

        val loanIdLayout = view.findViewById<LinearLayout>(R.id.loanDetailsIdValueLinLay)
        val loanDateTimeLayout = view.findViewById<LinearLayout>(R.id.loanDetailsDateTimeValueLinLay)
        val loanStateLayout = view.findViewById<LinearLayout>(R.id.loanDetailsStateValueLinLay)

        // Make unnecessary layots gone
        loanIdLayout.visibility = View.GONE
        loanDateTimeLayout.visibility = View.GONE
        loanStateLayout.visibility = View.GONE

        progressBar = requireActivity().findViewById(R.id.requestActivityProgressBar)
        val applyLoanBtn = requireActivity().findViewById<Button>(R.id.applyLoanBtn)
        amountValue = view.findViewById(R.id.loanDetailsAmountValueTxt)
        periodValue = view.findViewById(R.id.loanDetailsPeriodValueTxt)
        percentValue = view.findViewById(R.id.loanDetailsPercentValueTxt)
        firstNameValue = view.findViewById(R.id.loanDetailsFirstNameValueTxt)
        lastNameValue = view.findViewById(R.id.loanDetailsLastNameValueTxt)
        phoneNumberValue = view.findViewById(R.id.loanDetailsPhoneNumberValueTxt)

        applyLoanBtn.setOnClickListener(this)

        getLoanRequestData()

        loanConfirmationViewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is MainState.IsLoading ->
                    handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> {
                    handleSuccess(loan = state.data as Loan)
                }
                is MainState.ErrorResult -> handleError(apiError =  state.apiError)
            }
        })

        // Open loan personal data fragment when press back
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openLoanPersonalDataFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.applyLoanBtn -> {
                applyLoan()
            }
        }
    }

    private fun getLoanRequestData() {
        val chosenAmountValue = loanRequestActivityViewModel.getChosenAmountValue().value
        val loanConditions = loanRequestActivityViewModel.getLoanConditions().value
        val personalData = loanRequestActivityViewModel.getPersonalData().value
        if (chosenAmountValue != null)
            amountValue.setText(chosenAmountValue.toString())
        if (loanConditions != null) {
            periodValue.setText(loanConditions.period.toString())
            percentValue.setText(loanConditions.percent.toString())
        }
        if (personalData != null) {
            firstNameValue.setText(personalData.firstName)
            lastNameValue.setText(personalData.lastName)
            phoneNumberValue.setText(personalData.phoneNumber)
        }
    }

    private fun applyLoan() {
        val chosenAmountValue = loanRequestActivityViewModel.getChosenAmountValue().value
        val loanConditions = loanRequestActivityViewModel.getLoanConditions().value
        val personalData = loanRequestActivityViewModel.getPersonalData().value
        if (loanConditions != null && personalData != null && chosenAmountValue != null)
            loanConfirmationViewModel.applyLoan(
                LoanRequest(
                    loanAmount = chosenAmountValue,
                    loanPeriod = loanConditions.period,
                    loanPercent = loanConditions.percent,
                    borrowerFirstName = personalData.firstName,
                    borrowerLastName = personalData.lastName,
                    borrowerPhoneNumber = personalData.phoneNumber
                )
        )
        // TODO выбрасывать ошибку и закрывать форму при пустых данных
    }

    private fun handleLoading(isLoading: Boolean) {
        when (isLoading) {
            false -> {
                view?.alpha = (1).toFloat()
                view?.isEnabled = !isLoading
                progressBar.alpha = (0).toFloat()
            }
            true -> {
                view?.alpha = (0).toFloat()
                view?.isEnabled = !isLoading
                progressBar.alpha = (1).toFloat()
            }
        }
    }

    //TODO придумать, как передавать результат в итоговый фрагмент
    private fun handleSuccess(loan: Loan)  {
        loanRequestActivityViewModel.setLoan(loan = loan)
        openSuccessFragment()
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

    private fun openSuccessFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.loanRequestContainer,
                LoanRequestSuccessFragment()
            )
            ?.commit()
    }

    private fun openLoanPersonalDataFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.loanRequestContainer,
                LoanPersonalDataFragment()
            )
            ?.commit()
    }

    interface LoanConfirmationFragmentListener {
        fun enableApplyLoanRequestButton()
    }

}