package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.LoginViewModel
import com.girrafeecstud.final_loan_app_zalessky.ui.dialog.ErrorDialogFragment

class LoginFragment :
    Fragment(),
    View.OnClickListener {

    private lateinit var enterLoginName: EditText
    private lateinit var enterLoginPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var loginBtn: Button

    private val loginViewModel: LoginViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val userName = this.arguments?.getString("USER_NAME")
        if (userName != null)
            loginViewModel.setUserName(userName = userName)

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterLoginName = view.findViewById<EditText>(R.id.loginNameEdtTxt)
        enterLoginPassword = view.findViewById<EditText>(R.id.loginPasswordEdtTxt)
        loginBtn = view.findViewById<Button>(R.id.loginBtn)
        progressBar = requireActivity().findViewById(R.id.loginProgressBar)
        val createAccountBtn = view.findViewById<Button>(R.id.createAccountBtn)

        loginBtn.setOnClickListener(this)
        createAccountBtn.setOnClickListener(this)

        subscribeObservers()

        // Close actviity and app when press back in login fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginBtn -> login()
            R.id.createAccountBtn -> openRegistrationFragment()
        }
    }

    private fun subscribeObservers() {
        loginViewModel.getUserName().observe(viewLifecycleOwner, { userName ->
            enterLoginName.setText(userName)
        })

        loginViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is MainState.IsLoading -> handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(token = state.data as String)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })
    }

    private fun openRegistrationFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.loginActivityContainer,
                RegistrationFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun login() {

        when (loginViewModel.isLoginUserNameValid(userName = enterLoginName.text.toString())) {
            false -> {
                enterLoginName.error = "Имя пользователя должно содержать минимум 3 символа включая буквы, цифры или специальные символы [-,.:;!?*@_]"
                return
            }
        }

        when (loginViewModel.isLoginPasswordValid(password = enterLoginPassword.text.toString())) {
            false -> {
                enterLoginPassword.error = "Пароль должен содержать минимум 8 символов включая буквы, цифры или специальные символы [-,.:;!?*@_]"
                return
            }
        }

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
                view?.alpha = (1).toFloat()
                view?.isEnabled = true
                progressBar.alpha = (0).toFloat()
            }
            true -> {
                view?.alpha = (0).toFloat()
                view?.isEnabled = false
                progressBar.alpha = (1).toFloat()
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
        ErrorDialogFragment(
            errorTitle = "Ошибка",
            errorMessage = errorMessage
        ).show(requireActivity().supportFragmentManager, "ErrorDialogFragmentTag")
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