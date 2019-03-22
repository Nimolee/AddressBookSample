package com.nimolee.addressbooksample.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.MainFragment
import com.nimolee.addressbooksample.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProfileFragment : MainFragment() {
    private val _viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact = _viewModel.selectedContact ?: return
        profile_back.setOnClickListener { navigation.back() }
        profile_avatar.setImageBitmap(contact.photo)
        profile_name.editText?.setText(contact.name)
        profile_surname.editText?.setText(contact.surname)
        profile_email.editText?.setText(contact.email)
        profile_phone.editText?.setText(contact.phone)
        profile_gender.adapter = GenderSpinnerAdapter()
        profile_gender.setSelection(if (contact.gender) 0 else 1)
        profile_birthday.editText?.setText(contact.birthday.toString())
        profile_birthday_clicker.setOnClickListener {
            DatePickerDialog(contact.birthday) {
                contact.birthday = it
                profile_birthday.editText?.setText(it.toString())
            }.show(requireFragmentManager(), "")
        }
    }

    private class GenderSpinnerAdapter : BaseAdapter() {
        private val _genders = arrayOf("Male", "Female")

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var newConvertView = convertView
            if (newConvertView == null) {
                newConvertView =
                    LayoutInflater.from(parent?.context).inflate(R.layout.item_gender, parent, false)
            }
            (newConvertView as TextView).text = _genders[position]
            return newConvertView
        }

        override fun getItem(position: Int): Any = _genders[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = 2
    }
}