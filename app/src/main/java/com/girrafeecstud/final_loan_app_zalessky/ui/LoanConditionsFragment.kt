package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanConditionsViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanRequestActivityViewModel
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanConditionsConfig

class LoanConditionsFragment : Fragment(), View.OnClickListener {

    private lateinit var amountSeekBar: SeekBar
    private lateinit var progressBar: ProgressBar
    private lateinit var loanAmount: TextView
    private lateinit var loanPeriod: TextView
    private lateinit var loanPercent: TextView
    private lateinit var continueLoanRequestButton: Button

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
        Log.i("tag", "fragm created")
        return inflater.inflate(R.layout.loan_conditions_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener.enableContinueLoanRequestButton()

        progressBar = requireActivity().findViewById(R.id.requestActivityProgressBar)
        amountSeekBar = view.findViewById(R.id.loanConditionsAmountSeekBar)
        loanPercent = view.findViewById(R.id.loanConditionsPercentValueTxt)
        loanAmount = view.findViewById(R.id.loanConditionsAmountValueTxt)
        loanPeriod = view.findViewById(R.id.loanConditionsPeriodValueTxt)
        continueLoanRequestButton = requireActivity().findViewById(R.id.loanRequestContinueBtn)

        continueLoanRequestButton.setOnClickListener(this)

        amountSeekBar.incrementProgressBy(LoanConditionsConfig.AMOUNT_SEEK_BAR_STEP_SIZE)
        amountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                // TODO как-то преобразовать логику иначе
                var amount = progress / 100
                amount = amount * 100

                loanAmount.setText(amount.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        loanConditionsViewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is LoanConditionsViewModel.LoanConditionsFragmentState.IsLoading ->
                    handleLoading(isLoading = state.isLoading)
                is LoanConditionsViewModel.LoanConditionsFragmentState.SuccessResult ->
                    handleSuccess(loanConditions = state.loanConditions)
            }
        })

        loanConditionsViewModel.getLoanConditionsRequestResult().observe(viewLifecycleOwner, { loanConditionsResult ->
            when (loanConditionsResult) {
                is ApiResult.Success -> {
                    Toast.makeText(activity?.applicationContext, loanConditionsResult.data.toString(), Toast.LENGTH_LONG).show()
                }
                is ApiResult.Error -> {
                    Toast.makeText(activity?.applicationContext, loanConditionsResult.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        loanConditionsViewModel.getChosenAmountValue().observe(viewLifecycleOwner, { amountValue ->
            if (amountValue != null)
                amountSeekBar.progress = amountValue.toInt()
        })

    }

    override fun onDestroyView() {
        Log.i("tag", "Fragment B View Destroyed")

        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i("tag", "Fragment B Destroyed")

        super.onDestroy()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loanRequestContinueBtn -> {
                saveChosenAmountValue()
                saveLoanConditionValues()
                openLoanPersonalDataFragment()
            }
        }
    }

    // TODO если в строках есть другие символы, то программа летит - решить!!!
    private fun saveLoanConditionValues() {
        loanRequestActivityViewModel.setLoanAmountValue(amount = loanAmount.text.toString().toDouble())
        loanRequestActivityViewModel.setLoanPeriodValue(period = loanPeriod.text.toString().toInt())
        loanRequestActivityViewModel.setLoanPercentValue(percent = loanPercent.text.toString().toDouble())
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
        amountSeekBar.max = loanConditions.maxAmount.toInt()
        amountSeekBar.progress = loanConditions.maxAmount.toInt()
        loanAmount.setText(loanConditions.maxAmount.toInt().toString())
        loanPeriod.setText(loanConditions.period.toString())
        loanPercent.setText(loanConditions.percent.toString())
    }

    private fun handleError() {
        TODO()
    }

    private fun saveChosenAmountValue() {
        loanConditionsViewModel.setChosenAmountValue(amountValue = amountSeekBar.progress.toDouble())
    }

    private fun openLoanPersonalDataFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.loanRequestContainer,
                LoanPersonalDataFragment()
            )
            ?.addToBackStack(null)
            ?.commit()
    }

    interface LoanConditionsFragmentListener {
        fun enableContinueLoanRequestButton()
    }
}