package com.girrafeecstud.final_loan_app_zalessky.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.girrafeecstud.final_loan_app_zalessky.R

class ErrorDialogFragment(
    private val errorTitle: String,
    private val errorMessage: String
): DialogFragment() {

    interface ErrorDialogListener {
        fun errorOk()
    }

    private lateinit var listener: ErrorDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is ErrorDialogListener -> {
                listener = context
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(errorTitle)
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                listener.errorOk()
            }
            .create()
    }
}