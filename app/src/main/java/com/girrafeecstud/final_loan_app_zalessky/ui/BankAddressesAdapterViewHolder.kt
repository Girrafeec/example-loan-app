package com.girrafeecstud.final_loan_app_zalessky.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.girrafeecstud.final_loan_app_zalessky.R

class BankAddressesAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private fun bindAdderssItem(stringId: Int) {
        val bankAddress = itemView.findViewById<TextView>(R.id.bankAddressTxt)
        bankAddress.text = itemView.context.resources.getString(stringId)
    }

    fun bind(stringId: Int) {
        bindAdderssItem(stringId = stringId)
    }

}
