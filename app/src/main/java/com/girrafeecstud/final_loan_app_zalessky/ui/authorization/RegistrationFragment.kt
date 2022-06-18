package com.girrafeecstud.final_loan_app_zalessky.ui

import android.app.AlertDialog
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
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlin.math.log

class RegistrationFragment : Fragment(), View.OnClickListener {

    private lateinit var enterRegistrationName: EditText
    private lateinit var enterRegistrationPassword: EditText
    private lateinit var progressBar: ProgressBar

    private val registrationViewModel: RegistrationViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterRegistrationName = view.findViewById<EditText>(R.id.registrationNameEdtTxt)
        enterRegistrationPassword = view.findViewById<EditText>(R.id.registrationPasswordEdtTxt)
        val registrationBtn = view.findViewById<Button>(R.id.registrationBtn)
        val authorizeBtn = view.findViewById<Button>(R.id.openLoginFragmentBtn)
        progressBar = requireActivity().findViewById(R.id.loginProgressBar)

        registrationBtn.setOnClickListener(this)
        authorizeBtn.setOnClickListener(this)

        subscribeObservers()

        // Open login fragment when press back in registration fragment
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openLoginFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.registrationBtn -> registration()
            R.id.openLoginFragmentBtn -> openLoginFragment()
        }
    }

    private fun subscribeObservers() {
        registrationViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is MainState.IsLoading -> handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(auth = state.data as Auth)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })
    }

    private fun registration() {

        when (registrationViewModel.isUserNameValid(userName = enterRegistrationName.text.toString())) {
            false -> {
                enterRegistrationName.error = requireActivity().resources.getString(R.string.username_validation_error)
                return
            }
        }

        when (registrationViewModel.isLoginPasswordValid(password = enterRegistrationPassword.text.toString())) {
            false -> {
                enterRegistrationPassword.error = requireActivity().resources.getString(R.string.password_validation_error)
                return
            }
        }

        registrationViewModel.registration(
            userName = enterRegistrationName.text.toString(),
            userPassword = enterRegistrationPassword.text.toString()
        )
    }

    private fun handleSuccessResult(auth: Auth) {

        // TODO вынести в отдельный класс с конфигом в каталоге utils
        val bundle = Bundle()
        bundle.putString("USER_NAME", auth.userName)

        val loginFragment = LoginFragment()
        loginFragment.arguments = bundle

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.loginActivityContainer,
                loginFragment
            )
            .commit()
    }

    private fun openLoginFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.loginActivityContainer,
                LoginFragment()
            )
            .commit()
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

        var errorMessage = ""
        var errorTitle = ""

        when (apiError.errorType) {
            ApiErrorType.BAD_REQUEST_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.bad_request_registration_error_title)
                errorMessage = requireActivity().resources.getString(R.string.bad_request_registration_error_message)
            }
            ApiErrorType.UNAUTHORIZED_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
            ApiErrorType.RESOURCE_FORBIDDEN_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
            ApiErrorType.NOT_FOUND_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
            ApiErrorType.NO_CONNECTION_ERROR -> {
                Toast.makeText(
                    activity?.applicationContext,
                    activity?.resources?.getString(R.string.no_connection_error),
                    Toast.LENGTH_SHORT)
                    .show()
                return
            }
            ApiErrorType.TIMEOUT_EXCEEDED_ERROR -> {
                Toast.makeText(
                    activity?.applicationContext,
                    activity?.resources?.getString(R.string.connection_timeout_error),
                    Toast.LENGTH_SHORT)
                    .show()
                return
            }
            ApiErrorType.UNKNOWN_ERROR -> {
                errorTitle = requireActivity().resources.getString(R.string.default_error_title)
                errorMessage = requireActivity().resources.getString(R.string.default_error_message)
            }
        }
        showErrorDialog(errorTitle = errorTitle, errorMessage = errorMessage)
    }

    private fun showErrorDialog(errorTitle: String, errorMessage: String) {
        AlertDialog.Builder(context)
            .setTitle(errorTitle)
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                dialog.dismiss() })
            .show()
    }

}