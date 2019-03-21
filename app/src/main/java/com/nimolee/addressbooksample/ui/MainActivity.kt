package com.nimolee.addressbooksample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.recomended.RecomendedFragment
import com.nimolee.addressbooksample.ui.saved.SavedFragment
import com.nimolee.addressbooksample.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity(), NavigationInterface {
    private val _viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_menu_saved_contacts -> openFragment(SavedFragment())
                R.id.main_menu_recommended_contacts -> openFragment(RecomendedFragment())
                R.id.main_menu_settings -> openFragment(SettingsFragment())
            }
            true
        }
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit()
    }

    override fun openFragmentWithBackstack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit()
    }
}
