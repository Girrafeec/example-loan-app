package com.girrafeecstud.final_loan_app_zalessky.ui.instruction

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.girrafeecstud.final_loan_app_zalessky.R

class LoanRequestInstructionActivity : FragmentActivity(), View.OnClickListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var nextBtn: Button
    private lateinit var prevBtn: Button
    private lateinit var finishBtn: Button

    private val instructionsAdapter = LoanRequestInstructionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_request_instruction)

        nextBtn = findViewById(R.id.nextInstructionItemBtn)
        prevBtn = findViewById(R.id.prevInstructionItemBtn)
        finishBtn = findViewById(R.id.finishInstructionsBtn)

        nextBtn.setOnClickListener(this)
        prevBtn.setOnClickListener(this)
        finishBtn.setOnClickListener(this)

        viewPager = findViewById(R.id.instructionsLayoutViewPager)
        viewPager.adapter = instructionsAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position < 1) {
                    prevBtn.visibility = View.INVISIBLE
                    prevBtn.isEnabled = false
                }
                if (position > 0) {
                    prevBtn.visibility = View.VISIBLE
                    prevBtn.isEnabled = true
                }
                if ( position < ((viewPager.adapter as LoanRequestInstructionsAdapter).itemCount -1)) {
                    finishBtn.visibility = View.INVISIBLE
                    finishBtn.isEnabled = false
                    nextBtn.visibility = View.VISIBLE
                    nextBtn.isEnabled = true
                }
                if ( position == ((viewPager.adapter as LoanRequestInstructionsAdapter).itemCount-1)) {
                    finishBtn.visibility = View.VISIBLE
                    finishBtn.isEnabled = true
                    nextBtn.visibility = View.INVISIBLE
                    nextBtn.isEnabled = false
                }
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.nextInstructionItemBtn -> {
                viewPager.currentItem = viewPager.currentItem +1
            }
            R.id.prevInstructionItemBtn -> {
                viewPager.currentItem = viewPager.currentItem -1
            }
            R.id.finishInstructionsBtn -> {
                finish()
            }
        }
    }
}