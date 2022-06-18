package com.girrafeecstud.final_loan_app_zalessky.utils

import com.girrafeecstud.final_loan_app_zalessky.R
import java.util.*
import kotlin.collections.ArrayList

class InstructionsConfig {

    companion object {
        const val IMG_ARG = "IMG_ARG"
        const val STR_ARG = "STR_ARG"
        val instructionsImages = ArrayList<Int>(
            Arrays.asList(
                R.drawable.instruction_1,
                R.drawable.instruction_2,
                R.drawable.instruction_3,
                R.drawable.instruction_4,
                R.drawable.instruction_5
            )
        )
        val instructionTitles = ArrayList<Int>(
            Arrays.asList(
                R.string.choose_loan_conditions_title,
                R.string.enter_personal_data_title,
                R.string.check_loan_conditions_title,
                R.string.wait_loan_request_title,
                R.string.check_loans_title
            )
        )
    }

}