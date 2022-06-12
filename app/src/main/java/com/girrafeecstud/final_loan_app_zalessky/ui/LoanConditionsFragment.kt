package com.girrafeecstud.final_loan_app_zalessky.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanConditionsViewModel
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanConditionsConfig
import org.w3c.dom.Text

class LoanConditionsFragment : Fragment(), View.OnClickListener {

    private lateinit var amountSeekBar: SeekBar
    private lateinit var periodSeekBar: SeekBar

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

        amountSeekBar = view.findViewById(R.id.loanConditionsAmountSeekBar)
        periodSeekBar = view.findViewById(R.id.loanConditionsPeriodSeekBar)
        val loanPerCent = view.findViewById<TextView>(R.id.loanConditionsPerCentValueTxt)
        val loanAmount = view.findViewById<TextView>(R.id.loanConditionsAmountValueTxt)
        val loanPeriod = view.findViewById<TextView>(R.id.loanConditionsPeriodValueTxt)
        val continueButton = view.findViewById<Button>(R.id.loanConditionsContinueBtn)

        continueButton.setOnClickListener(this)

        amountSeekBar.incrementProgressBy(LoanConditionsConfig.AMOUNT_SEEK_BAR_STEP_SIZE)
        amountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                loanAmount.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        loanConditionsViewModel.getLoanConditionsRequestResult().observe(viewLifecycleOwner, { loanConditionsResult ->
            when (loanConditionsResult) {
                is ApiResult.Success -> {
                    //amountSeekBar.min = LoanConditionsConfig.MIN_AMOUNT_VALUE
                    Log.i("tag l c fr", loanConditionsResult.data.toString())
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
}