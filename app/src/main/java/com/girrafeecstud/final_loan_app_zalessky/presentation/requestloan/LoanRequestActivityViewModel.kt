package com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRequestActivityViewModel @Inject constructor(

): ViewModel() {

    private val loan = MutableLiveData<Loan>()

    private val loanAmountValue = MutableLiveData<Double>()
    private val loanPeriodValue = MutableLiveData<Int>()
    private val loanPercentValue = MutableLiveData<Double>()
    private val firstNameValue = MutableLiveData<String>()
    private val lastNameValue = MutableLiveData<String>()
    private val phoneNumberValue = MutableLiveData<String>()

    fun setLoan(loan: Loan) {
        this.loan.value = loan
    }

    fun setLoanAmountValue(amount: Double) {
        loanAmountValue.value = amount
    }

    fun setLoanPeriodValue(period: Int) {
        loanPeriodValue.value = period
    }

    fun setLoanPercentValue(percent: Double) {
        loanPercentValue.value = percent
    }

    fun setFirstNameValue(firstName: String) {
        firstNameValue.value = firstName
    }

    fun setLastNameValue(lastName: String) {
        lastNameValue.value = lastName
    }

    fun setPhoneNumberValue(phoneNumber: String) {
        phoneNumberValue.value = phoneNumber
    }

    fun getLoan(): LiveData<Loan> {
        return loan
    }

    fun getLoanAmountValue(): LiveData<Double> {
        return loanAmountValue
    }

    fun getLoanPeriodValue(): LiveData<Int> {
        return loanPeriodValue
    }

    fun getLoanPercentValue(): LiveData<Double> {
        return loanPercentValue
    }

    fun getFirstNameValue(): LiveData<String> {
        return firstNameValue
    }

    fun getLastNameValue(): LiveData<String> {
        return lastNameValue
    }

    fun getPhoneNumberValue(): LiveData<String> {
        return phoneNumberValue
    }

}
