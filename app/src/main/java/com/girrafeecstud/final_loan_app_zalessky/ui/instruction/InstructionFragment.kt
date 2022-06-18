package com.girrafeecstud.final_loan_app_zalessky.ui.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.utils.InstructionsConfig

class InstructionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instruction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val instructionImage = view.findViewById<ImageView>(R.id.instructionImg)
        val instructionTitle = requireActivity().findViewById<TextView>(R.id.instructionTitle)

        instructionImage.setImageResource(requireArguments().getInt(InstructionsConfig.IMG_ARG))

        instructionTitle.setText(
            requireActivity().getString(
                requireArguments().getInt(InstructionsConfig.STR_ARG)
            )
        )
    }
}