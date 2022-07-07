package com.girrafeecstud.final_loan_app_zalessky

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.girrafeecstud.final_loan_app_zalessky.annotation.TestCase
import com.girrafeecstud.final_loan_app_zalessky.screen.LoansListScreen
import com.girrafeecstud.final_loan_app_zalessky.ui.LoansFragment
import com.girrafeecstud.final_loan_app_zalessky.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoansListScreenTest: KTestCase() {

    @get:Rule
    val activeRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        openLoansFragment()
    }

    private fun openLoansFragment() {
        activeRule.scenario.onActivity { activity ->
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainActivityFragmentContainer, LoansFragment())
                .commit()
        }
    }

    @Test
    @TestCase(name = "Loans list screen test 1", description = "Test opening LoansFragment")
    fun openLoansFragmentTest() {
        run {
            LoansListScreen {
                loansRecView {
                    isDisplayed()
                }
            }
        }
    }

}