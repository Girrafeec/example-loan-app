package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.PersonalData
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanPersonalDataViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanRequestActivityViewModel

class LoanPersonalDataFragment : Fragment(), View.OnClickListener {

    private lateinit var enterFirstName: EditText
    private lateinit var enterLastName: EditText
    private lateinit var enterPhoneNumber: EditText
    private lateinit var continueLoanRequestButton: Button
    private lateinit var backButton: ImageButton

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
        backButton = requireActivity().findViewById(R.id.actionBarBackButton)

        continueLoanRequestButton.setOnClickListener(this)
        backButton.setOnClickListener(this)

        val personalData = loanRequestActivityViewModel.getPersonalData().value
        if (personalData != null) {
            setPersonalDataValues(personalData = personalData)
        }

        // Open loan conditions fragment when press back
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (isInputCorrect()) {
                    true -> {
                        savePersonalDataValues()
                    }
                }
                openLoanConditionsFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loanRequestContinueBtn -> {
                when (isInputCorrect()) {
                    true -> {
                        savePersonalDataValues()
                        openLoanConfirmationFragment()
                    }
                }
            }
            R.id.actionBarBackButton -> {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun isInputCorrect(): Boolean {
        when (loanPersonalDataViewModel.isFirstNameValid(firstName =  enterFirstName.text.toString())) {
            false -> {
                enterFirstName.error = requireActivity().resources.getString(R.string.first_name_validation_error)
                return false
            }
        }

        when (loanPersonalDataViewModel.isLastNameValid(lastName =  enterLastName.text.toString())) {
            false -> {
                enterLastName.error = requireActivity().resources.getString(R.string.last_name_validation_error)
                return false
            }
        }

        when (loanPersonalDataViewModel.isPhoneNumberValid(phoneNumber =  enterPhoneNumber.text.toString())) {
            false -> {
                enterPhoneNumber.error = requireActivity().resources.getString(R.string.phone_number_validation_error)
                return false
            }
        }

        return true
    }

    private fun savePersonalDataValues() {
        loanRequestActivityViewModel.setPersonalData(personalData = PersonalData(
            firstName = enterFirstName.text.toString(),
            lastName = enterLastName.text.toString(),
            phoneNumber = enterPhoneNumber.text.toString()
        ))
    }

    private fun setPersonalDataValues(personalData: PersonalData) {
        enterFirstName.setText(personalData.firstName)
        enterLastName.setText(personalData.lastName)
        enterPhoneNumber.setText(personalData.phoneNumber)
    }

    private fun openLoanConfirmationFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.loanRequestContainer,
                LoanConfirmationFragment()
            )
            ?.commit()
    }

    private fun openLoanConditionsFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.loanRequestContainer,
                LoanConditionsFragment()
            )
            ?.commit()
    }

}