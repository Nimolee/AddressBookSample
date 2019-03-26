package com.nimolee.addressbooksample.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.MainFragment
import com.nimolee.addressbooksample.ui.MainViewModel
import com.nimolee.addressbooksample.ui.saved.SavedFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProfileFragment : MainFragment() {
    companion object {
        const val MODE_SAVED = 0
        const val MODE_RECOMMENDED = 1
    }

    private val _viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.bottomBarVisibilityLiveData.postValue(false)
        val contact = _viewModel.selectedContact ?: return
        val mode = _viewModel.profileMode ?: return
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
        profile_make_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${contact.phone}")
            startActivity(intent)
        }
        profile_send_email.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", contact.email, null
                )
            )
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
        when (mode) {
            MODE_SAVED -> {
                profile_special.setImageResource(R.drawable.ic_delete_white_24dp)
            }
            MODE_RECOMMENDED -> {
                profile_special.setImageResource(R.drawable.ic_person_add_white_24dp)
                profile_special.setOnClickListener {
                    _viewModel.saveContact(contact)
                    navigation.openFragment(SavedFragment())
                }
                profile_edit_mode.visibility = View.GONE
            }
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