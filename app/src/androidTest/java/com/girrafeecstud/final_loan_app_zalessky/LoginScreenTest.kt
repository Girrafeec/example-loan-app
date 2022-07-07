package com.girrafeecstud.final_loan_app_zalessky

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.girrafeecstud.final_loan_app_zalessky.annotation.TestCase
import com.girrafeecstud.final_loan_app_zalessky.screen.LoginScreen
import com.girrafeecstud.final_loan_app_zalessky.ui.AuthorizationActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest: KTestCase() {

    @Before
    fun setUp() {
    }

    @get:Rule
    val activeRule = ActivityScenarioRule(AuthorizationActivity::class.java)

    @Test
    @TestCase(name = "Login screen test 1", description = "Test if login fragment views are visible")
    fun openLoginFragment() {
        run {
            LoginScreen {
                enterLoginUserName {
                    isDisplayed()
                }
                enterLoginPassword {
                    isDisplayed()
                }
                loginButton {
                    isDisplayed()
                }
                createAccount {
                    isDisplayed()
                }
            }
        }
    }

    @Test
    @TestCase(name = "Login screen test 2", description = "Test clicking on create account btn")
    fun createAccount() {
        run {
            LoginScreen {
                createAccount {
                    click()
                }
            }
        }
    }

}