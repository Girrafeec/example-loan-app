package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoginViewModel

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var enterLoginName: EditText
    private lateinit var enterLoginPassword: EditText

    private val loginViewModel: LoginViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterLoginName = view.findViewById<EditText>(R.id.loginNameEdtTxt)
        enterLoginPassword = view.findViewById<EditText>(R.id.loginPasswordEdtTxt)
        val loginBtn = view.findViewById<Button>(R.id.loginBtn)
        val loginProgressBar = view.findViewById<ProgressBar>(R.id.loginProgressBar)
        val loginLayout = view.findViewById<LinearLayout>(R.id.loginLinLay)

        loginBtn.setOnClickListener(this)

        // Login result
        loginViewModel.getLoginResult().observe(viewLifecycleOwner, { loginResult ->
            when (loginResult) {
                is ApiResult.Success -> {
                    loginViewModel.setUserAuthorizedStatusWithToken(userBearerToken = loginResult.data.toString())
                    startMainActivity()
                }
                is ApiResult.Error -> {
                    Toast.makeText(activity?.applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Cconnecting status
        loginViewModel.getConnectiongStatus().observe(viewLifecycleOwner, { isConnecting ->
            when (isConnecting) {
                false -> {
                    loginLayout.alpha = (1).toFloat()
                    loginLayout.isEnabled = true
                    loginProgressBar.alpha = (0).toFloat()
                }
                true -> {
                    loginLayout.alpha = (0).toFloat()
                    loginLayout.isEnabled = false
                    loginProgressBar.alpha = (1).toFloat()
                }
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginBtn -> login()
        }
    }

    private fun login() {
        if (!enterLoginName.text.isEmpty() && !enterLoginPassword.text.isEmpty())
            loginViewModel.login(
                userName = enterLoginName.text.toString(),
                userPassword = enterLoginPassword.text.toString()
            )
    }

    private fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}