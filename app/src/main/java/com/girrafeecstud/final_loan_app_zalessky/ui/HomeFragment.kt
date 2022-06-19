package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.ui.instruction.LoanRequestInstructionActivity

class HomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getLoanBtn = view.findViewById<Button>(R.id.getLoanBtn)
        val instr = view.findViewById<Button>(R.id.instructionsBtn)

        instr.setOnClickListener(this)
        getLoanBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.getLoanBtn -> {
                val intent = Intent(activity, LoanRequestActivity::class.java)
                startActivity(intent)
            }
            R.id.instructionsBtn -> {
                val intent = Intent(activity, LoanRequestInstructionActivity::class.java)
                startActivity(intent)
            }
        }
    }

}