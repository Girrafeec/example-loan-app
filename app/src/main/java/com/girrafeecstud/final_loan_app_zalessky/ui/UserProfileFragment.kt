package com.girrafeecstud.final_loan_app_zalessky.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.UserProfileViewModel

class UserProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var userProfileName: TextView

    private val userProfileViewModel: UserProfileViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileName = view.findViewById(R.id.userProfileNameTxt)
        val exitAccountBtn = view.findViewById<Button>(R.id.exitAccountBtn)

        exitAccountBtn.setOnClickListener(this)

        subscribeObservers()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.exitAccountBtn -> {
                showExitDialog()
            }
        }
    }

    private fun subscribeObservers() {
        userProfileViewModel.getUserName().observe(viewLifecycleOwner, { userName ->
            userProfileName.text = userName
        })
    }

    private fun showExitDialog() {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.exit_account_dialog_title))
            .setMessage(getString(R.string.exit_account_dialog_message))
            .setPositiveButton(getString(R.string.exit)) { dialog, _ ->
                dialog.dismiss()
                exitAccount()
            }
            .setNegativeButton(getString(R.string.cancel)) {dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun startAuthActivity() {
        val intent = Intent(activity, AuthorizationActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun exitAccount() {
        userProfileViewModel.exitAccount()
        startAuthActivity()
    }

}