package com.nimolee.addressbooksample.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.data.local.ContactsSharedPreferences
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.nimolee.addressbooksample.data.wrappers.Date
import com.nimolee.addressbooksample.ui.profile.ProfileFragment
import com.nimolee.addressbooksample.ui.recomended.RecommendedFragment
import com.nimolee.addressbooksample.ui.saved.SavedFragment
import com.nimolee.addressbooksample.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.ENGLISH

class MainActivity : FragmentActivity(), NavigationInterface {
    companion object {
        private const val BOTTOM_SELECTED_ID = "bottom_selected_id"
        const val FAB_EXTENDED_MODE = 0
        const val FAB_SHRINK_MODE = 1
        const val FAB_HIDED_MODE = 2
    }

    private val _viewModel: MainViewModel by viewModel()

    override fun attachBaseContext(newBase: Context?) {
        val context = newBase ?: return
        val locale = Locale(ContactsSharedPreferences(context).language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = res.configuration
        config.setLocale(locale)
        super.attachBaseContext(context.createConfigurationContext(config))
    }

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
        _viewModel.fabExtendLiveData.observe(this, Observer {
            when (it) {
                FAB_EXTENDED_MODE -> main_extendable_fab.extend()
                FAB_SHRINK_MODE -> main_extendable_fab.shrink()
            }
            when (it) {
                FAB_HIDED_MODE -> main_extendable_fab.hide()
                else -> main_extendable_fab.show()
            }
        })
        main_extendable_fab.setOnClickListener {
            fun getCurrentDate(pattern: String): String {
                return SimpleDateFormat(pattern, ENGLISH).format(Calendar.getInstance().time)
            }
            _viewModel.selectedContact = Contact(
                0,
                "",
                "",
                true,
                "",
                "",
                Date(getCurrentDate("yyyy"), getCurrentDate("MM"), getCurrentDate("dd")),
                null
            )
            openSecondaryFragment(ProfileFragment())
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        val itemId = main_bottom_navigation.selectedItemId
        outState.putInt(BOTTOM_SELECTED_ID, itemId)
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
            .setCustomAnimations(
                android.R.animator.fade_in, android.R.animator.fade_out
            )
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
            .setCustomAnimations(
                android.R.animator.fade_in, android.R.animator.fade_out
            )
            .commit()
    }
}
