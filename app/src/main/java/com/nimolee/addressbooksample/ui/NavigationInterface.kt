package com.nimolee.addressbooksample.ui

import androidx.fragment.app.Fragment

interface NavigationInterface {
    fun openFragment(fragment: Fragment)

    fun openFragmentWithBackstack(fragment: Fragment)
}