package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
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

    private lateinit var listener: UserProfileFragmentListener

    private val userProfileViewModel: UserProfileViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is UserProfileFragmentListener ->
                listener = context
        }
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
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.exitAccountBtn -> {
                userProfileViewModel.exitAccount()
                startAuthActivity()
            }
        }
    }

    private fun startAuthActivity() {
        val intent = Intent(activity, AuthorizationActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    interface UserProfileFragmentListener {
        fun enableBottomNavigationViewUserProfileItem()
    }
}