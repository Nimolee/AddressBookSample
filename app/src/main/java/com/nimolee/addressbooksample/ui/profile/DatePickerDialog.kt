package com.nimolee.addressbooksample.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.data.wrappers.Date

class DatePickerDialog(date: Date, callback: (date: Date) -> Unit) : DialogFragment() {
    private val _callback = callback
    private val _date = date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_date_picker, null)
        view as DatePicker
        view.init(_date.year.toInt(), _date.month.toInt(), _date.day.toInt(), null)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Ok") { _, _ ->
                val year = "${view.year}"
                val month = if (view.month < 10) "0${view.month}" else "${view.month}"
                val day = if (view.dayOfMonth < 10) "0${view.dayOfMonth}" else "${view.dayOfMonth}"
                _callback.invoke(Date(year, month, day))
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}