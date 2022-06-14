package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanPersonalDataViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanRequestActivityViewModel
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanRequestActivityConfig
import kotlinx.android.synthetic.*

class LoanPersonalDataFragment : Fragment(), View.OnClickListener {

    //TODO придумать что-то с заполнением значений при запуске фрагмента на тот случай, если значения уже заполнялись

    private lateinit var enterFirstName: EditText
    private lateinit var enterLastName: EditText
    private lateinit var enterPhoneNumber: EditText
    private lateinit var continueLoanRequestButton: Button

    private lateinit var listener: LoanConditionsFragment.LoanConditionsFragmentListener

    private val loanPersonalDataViewModel: LoanPersonalDataViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    private val loanRequestActivityViewModel: LoanRequestActivityViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is LoanConditionsFragment.LoanConditionsFragmentListener -> {
                listener = context
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loan_personal_data_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable activity continue loan request button
        listener.enableContinueLoanRequestButton()

        enterFirstName = view.findViewById(R.id.loanPersonalDateEnterFirstNameEdtTxt)
        enterLastName = view.findViewById(R.id.loanPersonalDateEnterLastNameEdtTxt)
        enterPhoneNumber = view.findViewById(R.id.loanPersonalDateEnterPhoneNumberEdtTxt)
        continueLoanRequestButton = requireActivity().findViewById(R.id.loanRequestContinueBtn)

        continueLoanRequestButton.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loanRequestContinueBtn -> {
                savePersonalDataValues()
                openLoanConfirmationFragment()
            }
        }
    }

    private fun savePersonalDataValues() {
        loanRequestActivityViewModel.setFirstNameValue(firstName = enterFirstName.text.toString())
        loanRequestActivityViewModel.setLastNameValue(lastName = enterLastName.text.toString())
        loanRequestActivityViewModel.setPhoneNumberValue(phoneNumber = enterPhoneNumber.text.toString())
    }

    private fun openLoanConfirmationFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.loanRequestContainer,
                LoanConfirmationFragment()
            )
            ?.addToBackStack(null)
            ?.commit()
    }

}