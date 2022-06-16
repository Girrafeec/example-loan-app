package com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.PersonalData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRequestActivityViewModel @Inject constructor(

): ViewModel() {

    private val loan = MutableLiveData<Loan>()

    private val loanConditions = MutableLiveData<LoanConditions>()

    private val personalData = MutableLiveData<PersonalData>()

    private val chosenAmountValue = MutableLiveData<Double>()

    fun setLoanConditions(loanConditions: LoanConditions) {
        this.loanConditions.value = loanConditions
    }

    fun setLoan(loan: Loan) {
        this.loan.value = loan
    }

    fun setPersonalData(personalData: PersonalData) {
        this.personalData.value = personalData
    }

    fun setChosenAmountValue(amountValue: Double) {
        this.chosenAmountValue.value = amountValue
    }

    fun getLoanConditions(): LiveData<LoanConditions> {
        return loanConditions
    }

    fun getLoan(): LiveData<Loan> {
        return loan
    }

    fun getPersonalData(): LiveData<PersonalData> {
        return personalData
    }

    fun getChosenAmountValue(): LiveData<Double> {
        return chosenAmountValue
    }

    fun setNullValues() {
        loan.value = null
        loanConditions.value = null
        personalData.value = null
        chosenAmountValue.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("tag", "cleared")
    }

}
