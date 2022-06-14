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
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.LoginViewModel

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var enterLoginName: EditText
    private lateinit var enterLoginPassword: EditText
    private lateinit var loginLayout: LinearLayout
    private lateinit var loginProgressBar: ProgressBar
    private lateinit var loginBtn: Button

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
        loginBtn = view.findViewById<Button>(R.id.loginBtn)
        loginProgressBar = view.findViewById<ProgressBar>(R.id.loginProgressBar)
        loginLayout = view.findViewById<LinearLayout>(R.id.loginLinLay)

        loginBtn.setOnClickListener(this)

        loginViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is MainState.IsLoading -> handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(token = state.data as String)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
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

    private fun handleLoading(isLoading: Boolean) {
        when (isLoading) {
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
    }

    private fun handleError(apiError: ApiError) {

        var errorMessage = apiError.errorType.name

        when (apiError.errorType) {
            ApiErrorType.BAD_REQUEST_ERROR -> {

            }
            ApiErrorType.UNAUTHORIZED_ERROR -> {

            }
            ApiErrorType.RESOURCE_FORBIDDEN_ERROR -> {

            }
            ApiErrorType.NOT_FOUND_ERROR -> {

            }
            ApiErrorType.NO_CONNECTION_ERROR -> {

            }
            ApiErrorType.TIMEOUT_EXCEEDED_ERROR -> {

            }
            ApiErrorType.UNKNOWN_ERROR -> {

            }
        }
        Toast.makeText(activity?.applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccessResult(token: String) {
        loginViewModel.saveLoginData(
            userBearerToken = token,
            userName = enterLoginName.text.toString()
        )
        startMainActivity()
    }

}