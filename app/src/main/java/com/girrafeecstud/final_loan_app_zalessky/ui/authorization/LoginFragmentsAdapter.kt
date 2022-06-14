package com.girrafeecstud.final_loan_app_zalessky.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*
import kotlin.collections.ArrayList

class LoginFragmentsAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val fragmentArrayList = ArrayList<Fragment>(Arrays.asList(LoginFragment(), RegistrationFragment()))

    override fun createFragment(position: Int): Fragment {
        return fragmentArrayList.get(position)
    }

    override fun getItemCount(): Int {
        return fragmentArrayList.size
    }
}