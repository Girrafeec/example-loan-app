package com.girrafeecstud.final_loan_app_zalessky.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
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

        loginBtn.setOnClickListener(this)

        // TODO тост выбрасывается в самом начале
        loginViewModel.getToken().observe(viewLifecycleOwner, {
            Toast.makeText(activity?.applicationContext, it, Toast.LENGTH_SHORT).show()
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
}