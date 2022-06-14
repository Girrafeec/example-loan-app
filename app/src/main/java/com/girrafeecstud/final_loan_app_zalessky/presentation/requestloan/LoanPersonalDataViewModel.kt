package com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoanPersonalDataViewModel @Inject constructor(

) : ViewModel() {

    private val firstName = MutableLiveData<String>()
    private val secondName = MutableLiveData<String>()
    private val phoneNumber = MutableLiveData<String>()

    fun setFirstName(firstNameString: String) {
        firstName.value = firstNameString
    }

    fun setSecondName(secondNameString: String) {
        secondName.value = secondNameString
    }
    fun setPhoneNumber(phoneNumberString: String) {
        phoneNumber.value = phoneNumberString
    }

    fun getFirstName(): LiveData<String> {
        return firstName
    }

    fun getSecondName(): LiveData<String> {
        return secondName
    }
    fun getPhoneNumber(): LiveData<String> {
        return phoneNumber
    }

}