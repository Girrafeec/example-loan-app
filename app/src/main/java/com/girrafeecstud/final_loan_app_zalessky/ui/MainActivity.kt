package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mainBottomNavigationView: BottomNavigationView

    private val mainViewModel: MainViewModel by viewModels {
        (applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUiValues()

        // start with home fragment
        supportFragmentManager.beginTransaction().replace(R.id.mainActivityFragmentContainer, HomeFragment()).commit()

        // Default bottom navigation view selected item - home
        mainBottomNavigationView.selectedItemId = R.id.homeMainMenuItem
        mainBottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.homeMainMenuItem -> {
                        supportFragmentManager.beginTransaction().replace(R.id.mainActivityFragmentContainer, HomeFragment()).commit()
                        return true
                    }
                    R.id.loansMainMenuItem -> {
                        supportFragmentManager.beginTransaction().replace(R.id.mainActivityFragmentContainer, LoansFragment()).commit()
                        return true
                    }
                    R.id.userProfileMainMenuItem -> {
                        supportFragmentManager.beginTransaction().replace(R.id.mainActivityFragmentContainer, UserProfileFragment()).commit()
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun initUiValues() {
        mainBottomNavigationView = findViewById(R.id.mainActivityBottomNavView)
    }
}