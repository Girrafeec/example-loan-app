package com.girrafeecstud.final_loan_app_zalessky.ui

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan

class LoansAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private fun bindLoanItem(loan: Loan, listener: OnLoanItemClickListener) {
        val parent = itemView.findViewById<ConstraintLayout>(R.id.loanItemParentConLay)
        val loanDateTitle = itemView.findViewById<TextView>(R.id.loanItemTitleTxt)
        val loanState = itemView.findViewById<TextView>(R.id.loanItemLoanStateTxt)
        val loanAmountTitle = itemView.findViewById<TextView>(R.id.loanItemLoanAmountTxt)

        loanState.setText(loan.loanState.name)
        loanAmountTitle.setText(loan.loanAmount.toString() + " Р")

        parent.setOnClickListener { listener.onLoanItemBodyClickListener(loanId = loan.loanId) }
    }

    fun bind(loan: Loan, listener: OnLoanItemClickListener) {
        bindLoanItem(loan = loan, listener = listener)
    }

    interface OnLoanItemClickListener {
        fun onLoanItemBodyClickListener(loanId: Long)
    }

}