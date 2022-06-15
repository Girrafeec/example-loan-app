package com.girrafeecstud.final_loan_app_zalessky.ui

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
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.RegistrationViewModel
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
        progressBar = requireActivity().findViewById(R.id.loginProgressBar)

        registrationBtn.setOnClickListener(this)

        registrationViewModel.getState().observe(viewLifecycleOwner, { state->
            when (state) {
                is MainState.IsLoading -> handleLoading(isLoading = state.isLoading)
                is MainState.SuccessResult -> handleSuccessResult(auth = state.data as Auth)
                is MainState.ErrorResult -> handleError(apiError = state.apiError)
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.registrationBtn -> registration()
        }
    }

    private fun registration() {
        if (!enterRegistrationName.text.isEmpty() && !enterRegistrationPassword.text.isEmpty())
            registrationViewModel.registration(
                userName = enterRegistrationName.text.toString(),
                userPassword = enterRegistrationPassword.text.toString()
            )
    }

    private fun handleSuccessResult(auth: Auth) {

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
        Toast.makeText(activity?.applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

}