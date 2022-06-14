package com.girrafeecstud.final_loan_app_zalessky.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.girrafeecstud.final_loan_app_zalessky.utils.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val context: Context
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailiable())
            throw NoNetworkException("NO_NETWORK_ERROR")

        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailiable(): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}