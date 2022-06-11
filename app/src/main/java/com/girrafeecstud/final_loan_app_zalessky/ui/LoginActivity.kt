package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.girrafeecstud.final_loan_app_zalessky.R
import com.google.android.material.tabs.TabLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var loginTabLayout: TabLayout

    private lateinit var loginViewPager: ViewPager2

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
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("tag log act", "dest")
    }

    private fun initUiValues() {
        loginTabLayout = findViewById(R.id.loginTabLay)
        loginViewPager = findViewById(R.id.loginViewPager)
    }

}