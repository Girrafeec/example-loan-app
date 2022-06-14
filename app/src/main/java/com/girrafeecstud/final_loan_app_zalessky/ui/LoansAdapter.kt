package com.girrafeecstud.final_loan_app_zalessky.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import java.util.*
import kotlin.collections.ArrayList

class LoansAdapter(
    listener: LoansAdapterViewHolder.OnLoanItemClickListener
): RecyclerView.Adapter<LoansAdapterViewHolder>() {

    private var loans = ArrayList<Loan>()

    private val listener: LoansAdapterViewHolder.OnLoanItemClickListener

    init {
        this.listener = listener
    }

    private companion object {
        const val LOAN_VIEW_TYPE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoansAdapterViewHolder {
        val layout = when(viewType) {
            LOAN_VIEW_TYPE -> R.layout.loans_list_item
            else -> throw IllegalArgumentException("Invalid type")
        }

        val itemView = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return LoansAdapterViewHolder(itemView = itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (loans.get(position)) {
            is Loan -> LOAN_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid type")
        }
    }

    override fun onBindViewHolder(holder: LoansAdapterViewHolder, position: Int) {
        holder.bind(loan = loans.get(position), listener = listener)
    }

    override fun getItemCount(): Int {
        return loans.size
    }

    fun refreshLoansList(loans: List<Loan>) {
        this.loans = ArrayList(loans)
        notifyDataSetChanged()
    }
}