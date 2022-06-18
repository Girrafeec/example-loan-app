package com.girrafeecstud.final_loan_app_zalessky.ui

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LoansAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private fun bindLoanItem(loan: Loan, listener: OnLoanItemClickListener) {

        val dataStringValue = LocalDateTime
            .parse(
                loan.loanIssueDate,
                DateTimeFormatter.ISO_ZONED_DATE_TIME
            )
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

        val parent = itemView.findViewById<ConstraintLayout>(R.id.loanItemParentConLay)
        val loanDateTitle = itemView.findViewById<TextView>(R.id.loanItemTitleTxt)
        val loanState = itemView.findViewById<TextView>(R.id.loanItemLoanStateTxt)
        val loanAmountTitle = itemView.findViewById<TextView>(R.id.loanItemLoanAmountTxt)

        loanAmountTitle.setText(itemView.context.resources.getString(R.string.loan_amount_value, loan.loanAmount.toString()))
        loanDateTitle.setText(itemView.context.resources.getString(R.string.loan_date_value, dataStringValue))

        when (loan.loanState) {
            LoanState.APPROVED -> {
                loanState.setTextColor(itemView.context.resources.getColor(R.color.dark_green))
                loanState.setText(itemView.context.resources.getString(R.string.loan_state_approved))
            }
            LoanState.REJECTED -> {
                loanState.setTextColor(itemView.context.resources.getColor(R.color.red_dark))
                loanState.setText(itemView.context.resources.getString(R.string.loan_state_rejected))
            }
            LoanState.REGISTERED -> {
                loanState.setText(itemView.context.resources.getString(R.string.loan_state_registereed))
            }
        }

        parent.setOnClickListener { listener.onLoanItemBodyClickListener(
            actionBarTitle = loanDateTitle.text.toString(),
            loanId = loan.loanId) }
    }

    fun bind(loan: Loan, listener: OnLoanItemClickListener) {
        bindLoanItem(loan = loan, listener = listener)
    }

    interface OnLoanItemClickListener {
        fun onLoanItemBodyClickListener(actionBarTitle: String, loanId: Long)
    }

}