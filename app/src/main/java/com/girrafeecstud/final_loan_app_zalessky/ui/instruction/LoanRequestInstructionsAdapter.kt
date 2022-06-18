package com.girrafeecstud.final_loan_app_zalessky.ui.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.girrafeecstud.final_loan_app_zalessky.utils.InstructionsConfig

class LoanRequestInstructionsAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private var instructionImages = InstructionsConfig.instructionsImages
    private var instructionTitles = InstructionsConfig.instructionTitles

    override fun createFragment(position: Int): Fragment {

        val instructionFragment = InstructionFragment()
        instructionFragment.arguments = Bundle().apply {
            putInt(InstructionsConfig.IMG_ARG, instructionImages.get(position))
            putInt(InstructionsConfig.STR_ARG, instructionTitles.get(position))
        }

        return instructionFragment
    }

    override fun getItemCount(): Int {
        return instructionImages.size
    }

}