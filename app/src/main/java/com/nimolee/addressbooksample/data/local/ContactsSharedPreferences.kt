package com.nimolee.addressbooksample.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.nimolee.addressbooksample.tools.Constants.SHARED_PREFERENCE_NAME

class ContactsSharedPreferences(applicationContext: Context) {
    private val _prefs = applicationContext.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    private fun save(key: String, value: Any) {
        when (value) {
            is Boolean -> _prefs.edit { putBoolean(key, value) }
            is Int -> _prefs.edit { putInt(key, value) }
            is String -> _prefs.edit { putString(key, value) }
        }
    }

    private fun getString(key: String, defValue: String): String = _prefs.getString(key, defValue)

    private fun getBoolean(key: String, defValue: Boolean): Boolean = _prefs.getBoolean(key, defValue)

    private fun getInt(key: String, defValue: Int): Int = _prefs.getInt(key, defValue)

    var isNotificationEnabled: Boolean
        get() = getBoolean("preference_notification", true)
        set(value) = save("preference_notification", value)

    var notificationDelay: String
        get() = getString("preference_notification_delay", "15")
        set(value) = save("preference_notification_delay", value)

    var recommendedCount: String
        get() = getString("preference_recommended_count", "10")
        set(value) = save("preference_notification_delay", value)

    val language: String
        get() = getString("preference_language", "en")

}