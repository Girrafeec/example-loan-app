package com.girrafeecstud.final_loan_app_zalessky.ui.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import kotlin.collections.ArrayList

class LoanRequestInstructionsAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val instructions = ArrayList<Int>(Arrays.asList(1,2,3,4,5))

    override fun createFragment(position: Int): Fragment {

        val instructionFragment = InstructionFragment()
        instructionFragment.arguments = Bundle().apply {
            putInt("ARG", instructions.get(position))
        }

        return instructionFragment
    }

    override fun getItemCount(): Int {
        return instructions.size
    }


}