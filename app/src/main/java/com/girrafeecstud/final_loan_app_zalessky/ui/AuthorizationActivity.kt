package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.AuthorizationViewModel
import com.google.android.material.tabs.TabLayout

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var loginTabLayout: TabLayout

    private lateinit var loginViewPager: ViewPager2

    private val authorizationViewModel: AuthorizationViewModel by viewModels {
        (applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUiValues()

        // TODO провайдить из массива или адаптера?
        loginTabLayout.addTab(loginTabLayout.newTab().setText("Sign in"))
        loginTabLayout.addTab(loginTabLayout.newTab().setText("Sign up"))
        loginTabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val loginFragmentsAdapter = LoginFragmentsAdapter(this)
        loginViewPager.adapter = loginFragmentsAdapter
        // make viewpager not swipable
        loginViewPager.isUserInputEnabled = false

        loginTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                loginViewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        authorizationViewModel.isUserAuthorized().observe(this, { isUserAuthorized ->
            when (isUserAuthorized) {
                true -> {
                    startMainActivity()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("tag log act", "dest")
    }

    private fun initUiValues() {
        loginTabLayout = findViewById(R.id.loginTabLay)
        loginViewPager = findViewById(R.id.loginViewPager)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}