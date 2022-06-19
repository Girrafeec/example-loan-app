package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.girrafeecstud.final_loan_app_zalessky.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity :
    AppCompatActivity() {

    private lateinit var mainBottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUiValues()

        // Start with home fragment
        loadFragment(fragment = HomeFragment())
        mainBottomNavigationView.selectedItemId = R.id.homeMainMenuItem

        mainBottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.homeMainMenuItem -> {
                        loadFragment(fragment = HomeFragment())
                        return true
                    }
                    R.id.loansMainMenuItem -> {
                        loadFragment(fragment = LoansFragment())
                        return true
                    }
                    R.id.userProfileMainMenuItem -> {
                        loadFragment(fragment = UserProfileFragment())
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onBackPressed() {
        if (mainBottomNavigationView.selectedItemId == R.id.homeMainMenuItem)
            super.onBackPressed()
        else
            mainBottomNavigationView.selectedItemId = R.id.homeMainMenuItem
    }

    private fun initUiValues() {
        mainBottomNavigationView = findViewById(R.id.mainActivityBottomNavView)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.mainActivityFragmentContainer,
                fragment
            )
            .commit()
    }

}