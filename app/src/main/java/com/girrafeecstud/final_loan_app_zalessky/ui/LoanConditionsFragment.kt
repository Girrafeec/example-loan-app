package com.girrafeecstud.final_loan_app_zalessky.ui

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
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanConditionsConfig

class LoanConditionsFragment : Fragment(), View.OnClickListener {

    private lateinit var amountSeekBar: SeekBar
    private lateinit var progressBar: ProgressBar
    private lateinit var mainScrollView: ScrollView
    private lateinit var loanAmount: TextView
    private lateinit var loanPeriod: TextView
    private lateinit var loanPercent: TextView

    private val loanConditionsViewModel: LoanConditionsViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loan_conditions_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainScrollView = view.findViewById(R.id.loanConditionsScrollView)
        progressBar = view.findViewById(R.id.loanConditionsProgressBar)
        amountSeekBar = view.findViewById(R.id.loanConditionsAmountSeekBar)
        loanPercent = view.findViewById(R.id.loanConditionsPercentValueTxt)
        loanAmount = view.findViewById(R.id.loanConditionsAmountValueTxt)
        loanPeriod = view.findViewById(R.id.loanConditionsPeriodValueTxt)
        val continueButton = view.findViewById<Button>(R.id.loanConditionsContinueBtn)

        continueButton.setOnClickListener(this)

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
                is LoanConditionsViewModel.LoanConditionsFragmentState.IsLoading -> handleLoading(state.isLoading)
                is LoanConditionsViewModel.LoanConditionsFragmentState.SuccessResult -> handleSuccess(state.loanConditions)
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

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loanConditionsContinueBtn -> TODO()
        }
    }

    private fun handleLoading(isLoaging: Boolean) {
        when (isLoaging) {
            false -> {
                progressBar.alpha = (0).toFloat()
                mainScrollView.alpha = (1).toFloat()
                mainScrollView.isEnabled = !isLoaging
            }
            true -> {
                progressBar.alpha = (1).toFloat()
                mainScrollView.alpha = (0).toFloat()
                mainScrollView.isEnabled = !isLoaging
            }
        }
    }

    private fun handleSuccess(loanConditions: LoanConditions) {
        amountSeekBar.max = loanConditions.maxAmount
        amountSeekBar.progress = loanConditions.maxAmount
        loanAmount.setText(loanConditions.maxAmount.toString())
        loanPeriod.setText(loanConditions.period.toString())
        loanPercent.setText(loanConditions.percent.toString() + " " + activity?.getString(R.string.percent_sign))
    }

    private fun handleError() {
        TODO()
    }
}