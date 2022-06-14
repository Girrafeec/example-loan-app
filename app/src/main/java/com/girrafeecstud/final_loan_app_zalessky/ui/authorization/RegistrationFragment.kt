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
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.RegistrationViewModel

class RegistrationFragment : Fragment(), View.OnClickListener {

    private lateinit var enterRegistrationName: EditText
    private lateinit var enterRegistrationPassword: EditText

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
        val registrationProgressBar = view.findViewById<ProgressBar>(R.id.registrationProgressBar)
        val registrationLayout = view.findViewById<LinearLayout>(R.id.registrationLinLay)

        registrationBtn.setOnClickListener(this)

        registrationViewModel.getRegistrationResult().observe(viewLifecycleOwner, { registrationResult ->
            when (registrationResult) {
                is ApiResult.Success -> {

                }
                is ApiResult.Error -> {
                    Toast.makeText(activity?.applicationContext, "Registration error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        registrationViewModel.getConnectionStatus().observe(viewLifecycleOwner, { isConnecting ->
            when (isConnecting) {
                false -> {
                    registrationLayout.alpha = (1).toFloat()
                    registrationLayout.isEnabled = true
                    registrationProgressBar.alpha = (0).toFloat()
                }
                true -> {
                    registrationLayout.alpha = (0).toFloat()
                    registrationLayout.isEnabled = false
                    registrationProgressBar.alpha = (1).toFloat()
                }
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

}