package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        (applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.getResult().observe(this, {
            print(it)
        })
    }
}