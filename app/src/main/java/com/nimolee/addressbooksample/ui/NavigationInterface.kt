package com.nimolee.addressbooksample.ui

import androidx.fragment.app.Fragment

interface NavigationInterface {
    fun openSecondaryFragment(fragment: Fragment)

    fun back()
}