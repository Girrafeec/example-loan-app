package com.girrafeecstud.final_loan_app_zalessky.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.girrafeecstud.final_loan_app_zalessky.R

class ExitAccountDialogFragment: DialogFragment() {

    interface ExitAccountDialogListener {
        fun exitAccount()
        fun cancelExitAccountProcess()
    }

    private lateinit var listener: ExitAccountDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is ExitAccountDialogListener -> {
                listener = context
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(getString(R.string.exit_account_dialog_title))
            .setMessage(getString(R.string.exit_account_dialog_message))
            .setPositiveButton(getString(R.string.exit)) { _, _ ->
                listener.exitAccount()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                listener.cancelExitAccountProcess()
            }
            .create()
    }
}