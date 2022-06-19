package com.girrafeecstud.final_loan_app_zalessky.ui.loanactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.utils.LoanDetailsInfoConfig

class BankAddressesAdapter: RecyclerView.Adapter<BankAddressesAdapterViewHolder>() {

    private val bankAddresses = LoanDetailsInfoConfig.bankDepartmentsAdresses

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BankAddressesAdapterViewHolder {
        val layout = R.layout.bank_department_address_item
        val itemView = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return BankAddressesAdapterViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: BankAddressesAdapterViewHolder, position: Int) {
        holder.bind(stringId = bankAddresses.get(position))
    }

    override fun getItemCount(): Int {
        return bankAddresses.size
    }
}