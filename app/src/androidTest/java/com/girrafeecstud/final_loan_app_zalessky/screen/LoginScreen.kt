package com.girrafeecstud.final_loan_app_zalessky.screen

import com.girrafeecstud.final_loan_app_zalessky.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen

object LoginScreen: Screen<LoginScreen>() {

    val enterLoginUserName = KEditText{ withId(R.id.loginNameEdtTxt) }
    val enterLoginPassword = KEditText{ withId(R.id.loginPasswordEdtTxt) }
    val loginButton = KEditText{ withId(R.id.loginBtn) }
    val createAccount = KEditText{ withId(R.id.createAccountBtn) }

}