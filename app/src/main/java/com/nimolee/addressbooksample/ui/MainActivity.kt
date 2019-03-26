package com.nimolee.addressbooksample.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.profile.ProfileFragment
import com.nimolee.addressbooksample.ui.recomended.RecommendedFragment
import com.nimolee.addressbooksample.ui.saved.SavedFragment
import com.nimolee.addressbooksample.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity(), NavigationInterface {
    companion object {
        private const val BOTTOM_SELECTED_ID = "bottom_selected_id"
    }

    private val _viewModel: MainViewModel by viewModel()

    //Lifecycle methods=================================================================================================
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
        main_bottom_navigation.selectedItemId = R.id.main_menu_saved_contacts
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

    override fun onSaveInstanceState(outState: Bundle?) {
        val itemId = main_bottom_navigation.selectedItemId
        outState?.putInt(BOTTOM_SELECTED_ID, itemId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        main_bottom_navigation.selectedItemId =
            savedInstanceState?.getInt(BOTTOM_SELECTED_ID) ?: R.id.main_menu_saved_contacts
        if (_viewModel.selectedContact != null) {
            openSecondaryFragment(ProfileFragment())
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    //NavigationInterface implementation================================================================================
    override fun openSecondaryFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }

    //Local methods=====================================================================================================
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}
