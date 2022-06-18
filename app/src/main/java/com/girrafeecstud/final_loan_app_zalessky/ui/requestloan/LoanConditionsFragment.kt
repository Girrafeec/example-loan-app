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
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanConditionsViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanRequestActivityViewModel
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanConditionsConfig

class LoanConditionsFragment : Fragment(), View.OnClickListener {

    private lateinit var amountSeekBar: SeekBar
    private lateinit var progressBar: ProgressBar
    private lateinit var loanAmount: TextView
    private lateinit var loanPeriod: TextView
    private lateinit var loanPercent: TextView
    private lateinit var continueLoanRequestButton: Button
    private lateinit var backButton: ImageButton

    // TODO DI
    private lateinit var listener: LoanConditionsFragmentListener

    private val loanConditionsViewModel: LoanConditionsViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    private val loanRequestActivityViewModel: LoanRequestActivityViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is LoanConditionsFragmentListener -> {
                listener = context
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loan_conditions_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable activity continue loan request button
        listener.enableContinueLoanRequestButton()

        progressBar = requireActivity().findViewById(R.id.requestActivityProgressBar)
        amountSeekBar = view.findViewById(R.id.loanConditionsAmountSeekBar)
        loanPercent = view.findViewById(R.id.loanConditionsPercentValueTxt)
        loanAmount = view.findViewById(R.id.loanConditionsAmountValueTxt)
        loanPeriod = view.findViewById(R.id.loanConditionsPeriodValueTxt)
        continueLoanRequestButton = requireActivity().findViewById(R.id.loanRequestContinueBtn)
        backButton = requireActivity().findViewById(R.id.actionBarBackButton)

        continueLoanRequestButton.setOnClickListener(this)
        backButton.setOnClickListener(this)

        // If we already have loan condiitons in request activity view model, we do not make new request
        val loanConditions = loanRequestActivityViewModel.getLoanConditions().value
        Log.i("tag", loanConditions.toString())
        when (loanConditions) {
            null -> {
                loanConditionsViewModel.loadLoanConditions()
            }
            else -> {
                setLoanConditionsValues(loanConditions = loanConditions)
            }
        }

        subscribeObservers()

        // Finish activity when press back button
        val backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                loanRequestActivityViewModel.setNullValues()
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallback)

        amountSeekBar.incrementProgressBy(LoanConditionsConfig.AMOUNT_SEEK_BAR_STEP_SIZE)
        amountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                // TODO как-то преобразовать логику иначе
                var amount = progress / 100
                amount = amount * 100

                loanAmount.setText(
                    activity?.getString(
                        R.string.loan_amount_value,
                        amount.toString()
                    )
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loanRequestContinueBtn -> {
                saveChosenAmountValue()
                openLoanPersonalDataFragment()
            }
            R.id.actionBarBackButton -> {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun subscribeObservers() {
        // If we already have chosen amount value, we set it again
        loanRequestActivityViewModel.getChosenAmountValue().observe(viewLifecycleOwner, { amountValue ->
            if (amountValue != null)
                amountSeekBar.progress = amountValue.toInt()
        })

        loanConditionsViewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is MainState.IsLoading -> handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> handleSuccess(loanConditions = state.data as LoanConditions)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })
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

    private fun handleSuccess(loanConditions: LoanConditions) {
        saveLoanConditionValues(
            loanAmount = loanConditions.maxAmount,
            loanPeriod = loanConditions.period,
            loanPercent = loanConditions.percent
        )
        setLoanConditionsValues(loanConditions = loanConditions)
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

    private fun setLoanConditionsValues(loanConditions: LoanConditions) {
        amountSeekBar.max = loanConditions.maxAmount.toInt()
        amountSeekBar.progress = loanConditions.maxAmount.toInt()

        loanAmount.setText(
            activity?.getString(
                R.string.loan_amount_value,
                loanConditions.maxAmount.toInt().toString()
            )
        )

        loanPercent.setText(
            activity?.getString(
                R.string.loan_percent_value,
                loanConditions.percent.toString()
            )
        )

        // Get last digit and choose correct period name value
        var periodStringValue = when (loanConditions.period % 10) {
            1 -> {
                if (loanConditions.period % 100 == 11)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_1)
            }
            2 -> {
                if (loanConditions.period % 100 == 12)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_2)
            }
            3 -> {
                if (loanConditions.period % 100 == 13)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_2)
            }
            4 -> {
                if (loanConditions.period % 100 == 14)
                    activity?.getString(R.string.period_day_3)
                else
                    activity?.getString(R.string.period_day_2)
            }
            else -> {
                activity?.getString(R.string.period_day_3)
            }
        }

        loanPeriod.setText(
            activity?.getString(
                R.string.loan_period_value,
                loanConditions.period.toString(),
                periodStringValue
            )
        )
    }

    private fun saveChosenAmountValue() {
        val chosenAmountValue = (amountSeekBar.progress / 100) * 100
        loanRequestActivityViewModel.setChosenAmountValue(amountValue = chosenAmountValue.toDouble())
    }

    private fun saveLoanConditionValues(loanAmount: Double, loanPeriod: Int, loanPercent: Double) {
        loanRequestActivityViewModel.setLoanConditions(loanConditions = LoanConditions(
            maxAmount = loanAmount,
            percent = loanPercent,
            period = loanPeriod
        ))
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

    interface LoanConditionsFragmentListener {
        fun enableContinueLoanRequestButton()
    }
}