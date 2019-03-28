package com.nimolee.addressbooksample.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.tools.Constants
import com.nimolee.addressbooksample.ui.MainActivity
import com.nimolee.addressbooksample.ui.MainActivity.Companion.FAB_HIDED_MODE
import com.nimolee.addressbooksample.ui.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var preference_notification: SwitchPreference
    private lateinit var preference_notification_delay: ListPreference
    private lateinit var preference_recommended_count: ListPreference
    private lateinit var preference_language: ListPreference

    private val _viewModel: MainViewModel by sharedViewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = Constants.SHARED_PREFERENCE_NAME
        setPreferencesFromResource(R.xml.settings, rootKey)
        _viewModel.fabExtendLiveData.postValue(FAB_HIDED_MODE)
        preference_notification = findPreference("preference_notification") as SwitchPreference
        preference_notification_delay = findPreference("preference_notification_delay") as ListPreference
        preference_recommended_count = findPreference("preference_recommended_count") as ListPreference
        preference_language = findPreference("preference_language") as ListPreference
        preference_language.setOnPreferenceChangeListener { _, _ ->
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
            true
        }
        preference_notification.setOnPreferenceChangeListener { _, newValue ->
            newValue as Boolean
            preference_notification_delay.isEnabled = newValue
            true
        }
        preference_notification_delay.setOnPreferenceChangeListener { preference, newValue ->
            preference.summary =
                getString(R.string.settings_notification_delay_summary) +
                        newValue +
                        getString(R.string.settings_time_utils)
            true
        }
        preference_recommended_count.setOnPreferenceChangeListener { preference, newValue ->
            preference.summary = newValue.toString() + getString(R.string.settings_recommended_count_summary)
            true
        }
        preference_notification_delay.isEnabled = _viewModel.isNotificationEnabled
        validateSummary()
    }

    private fun validateSummary(): Boolean {
        preference_notification_delay.summary =
            getString(R.string.settings_notification_delay_summary) +
                    _viewModel.notificationDelay +
                    getString(R.string.settings_time_utils)
        preference_recommended_count.summary =
            _viewModel.recommendedCount + getString(R.string.settings_recommended_count_summary)
        return true
    }
}