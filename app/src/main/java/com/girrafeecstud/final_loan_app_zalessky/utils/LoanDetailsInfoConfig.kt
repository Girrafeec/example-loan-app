package com.girrafeecstud.final_loan_app_zalessky.utils

import com.girrafeecstud.final_loan_app_zalessky.R
import java.util.*
import kotlin.collections.ArrayList

class LoanDetailsInfoConfig {

    companion object {
        val bankDepartmentsAdresses = ArrayList<Int>(
            Arrays.asList(
                R.string.bank_department_address_1,
                R.string.bank_department_address_2,
                R.string.bank_department_address_3,
                R.string.bank_department_address_4,
                R.string.bank_department_address_5
            )
        )
    }

}