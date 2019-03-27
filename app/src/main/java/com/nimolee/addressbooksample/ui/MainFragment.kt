package com.nimolee.addressbooksample.ui

import android.content.Context
import androidx.fragment.app.Fragment

abstract class MainFragment : Fragment() {
    lateinit var navigation: NavigationInterface


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationInterface)
            navigation = context
        else error("No navigation attached")
    }
}