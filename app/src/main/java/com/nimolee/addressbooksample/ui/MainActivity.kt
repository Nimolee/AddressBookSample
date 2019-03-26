package com.nimolee.addressbooksample.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.recomended.RecommendedFragment
import com.nimolee.addressbooksample.ui.saved.SavedFragment
import com.nimolee.addressbooksample.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity(), NavigationInterface {
    companion object {
        const val FRAGMENT_SAVED = 0
        const val FRAGMENT_RECOMMENDED = 1
        const val FRAGMENT_SETTINGS = 2
    }


    private val _viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_menu_saved_contacts -> openFragment(SavedFragment())
                R.id.main_menu_recommended_contacts -> openFragment(RecommendedFragment())
                R.id.main_menu_settings -> openFragment(SettingsFragment())
            }
            true
        }
        _viewModel.bottomBarVisibilityLiveData.observe(this, Observer {
            main_bottom_navigation.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    override fun openMainFragment(fragmentId: Int) {
        when (fragmentId) {
            FRAGMENT_SAVED -> {
                if (main_bottom_navigation.selectedItemId != R.id.main_menu_saved_contacts)
                    main_bottom_navigation.selectedItemId = R.id.main_menu_saved_contacts
            }
            FRAGMENT_RECOMMENDED -> {
                if (main_bottom_navigation.selectedItemId != R.id.main_menu_recommended_contacts)
                    main_bottom_navigation.selectedItemId = R.id.main_menu_recommended_contacts
            }
            FRAGMENT_SETTINGS -> {
                if (main_bottom_navigation.selectedItemId != R.id.main_menu_settings)
                    main_bottom_navigation.selectedItemId = R.id.main_menu_settings
            }
            else -> error("Unknown fragmentId = $fragmentId")
        }
    }

    override fun openSecondaryFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }
}
