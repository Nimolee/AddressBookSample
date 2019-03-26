package com.nimolee.addressbooksample.ui

import androidx.fragment.app.Fragment

interface NavigationInterface {
    fun openMainFragment(fragmentId: Int)

    fun openSecondaryFragment(fragment: Fragment)

    fun back()
}