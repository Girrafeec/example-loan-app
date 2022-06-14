package com.girrafeecstud.final_loan_app_zalessky.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan

class LoansAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private fun bindLoanItem(loan: Loan) {
        val loanDateTitle = itemView.findViewById<TextView>(R.id.loanItemTitleTxt)
        val loanState = itemView.findViewById<TextView>(R.id.loanItemLoanStateTxt)
        val loanAmountTitle = itemView.findViewById<TextView>(R.id.loanItemLoanAmountTxt)

        loanState.setText(loan.loanState.name)
        loanAmountTitle.setText(loan.loanAmount.toString())
    }

    fun bind(loan: Loan) {
        bindLoanItem(loan = loan)
    }

    interface OnLoanItemClickListener {
        fun onLoanItemBodyClickListener()
    }

}