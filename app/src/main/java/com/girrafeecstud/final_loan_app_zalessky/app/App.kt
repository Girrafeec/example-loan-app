package com.girrafeecstud.final_loan_app_zalessky.app

import android.app.Application
import com.girrafeecstud.final_loan_app_zalessky.di.AppComponent
import com.girrafeecstud.final_loan_app_zalessky.di.AppModule
import com.girrafeecstud.final_loan_app_zalessky.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}