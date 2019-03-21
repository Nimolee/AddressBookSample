package com.nimolee.addressbooksample.data

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

    private fun getString(key: String): String? = _prefs.getString(key, null)

    private fun getBoolean(key: String): Boolean = _prefs.getBoolean(key, false)
}