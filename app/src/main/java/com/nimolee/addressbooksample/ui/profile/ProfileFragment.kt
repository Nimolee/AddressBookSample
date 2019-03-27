package com.nimolee.addressbooksample.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.snackbar.Snackbar
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.nimolee.addressbooksample.data.wrappers.Date
import com.nimolee.addressbooksample.ui.MainActivity.Companion.FAB_HIDED_MODE
import com.nimolee.addressbooksample.ui.MainFragment
import com.nimolee.addressbooksample.ui.MainViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
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
        mainSetup(contact)
        when (contact.id) {
            null -> setupRecommended(contact)
            0 -> setupNew(contact)
            else -> setupSaved(contact)
        }
        _viewModel.fabExtendLiveData.postValue(FAB_HIDED_MODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            profile_avatar.setImageURI(result.uri)
        }
    }

    private fun mainSetup(contact: Contact) {
        _viewModel.bottomBarVisibilityLiveData.postValue(false)
        if (contact.photo != null) {
            profile_avatar.setImageBitmap(contact.photo)
        }
        profile_name.editText?.setText(contact.name)
        profile_surname.editText?.setText(contact.surname)
        profile_email.editText?.setText(contact.email)
        profile_phone.editText?.setText(contact.phone)
        profile_gender.adapter = GenderSpinnerAdapter()
        profile_gender.setSelection(if (contact.gender) 0 else 1)
        profile_birthday.editText?.setText(contact.birthday.toString())
        profile_back.setOnClickListener {
            _viewModel.selectedContact = null
            navigation.back()
        }
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
        profile_camera.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(512, 512)
                .setAspectRatio(1, 1)
                .start(requireContext(), this)
        }
    }

    private fun setupSaved(contact: Contact) {
        profile_special.setImageResource(R.drawable.ic_delete_white_24dp)
        profile_special.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Are you sure?")
                .setMessage("This action can't be undone.")
                .setPositiveButton("Yes") { _, _ ->
                    _viewModel.removeContact(contact)
                    _viewModel.selectedContact = null
                    navigation.back()
                }
                .setNegativeButton("No", null)
                .show()
        }
        profile_edit_mode.setOnClickListener {
            saveContact(contact, it)
        }
    }

    private fun validateFields(view: View): Boolean {
        var result = true
        val name = profile_name.editText?.text.toString()
        val surname = profile_surname.editText?.text.toString()
        if (name.isBlank() && surname.isBlank()) {
            Snackbar.make(view, "Please, enter valid name.", Snackbar.LENGTH_LONG).show()
            result = false
        }
        return result
    }

    private fun setupRecommended(contact: Contact) {
        profile_special.setImageResource(R.drawable.ic_person_add_white_24dp)
        profile_special.setOnClickListener {
            _viewModel.saveContact(contact)
            _viewModel.selectedContact = null
            navigation.back()
        }
        profile_edit_mode.visibility = View.GONE
        profile_name.isEnabled = false
        profile_surname.isEnabled = false
        profile_gender.isEnabled = false
        profile_birthday_clicker.isEnabled = false
        profile_birthday.isEnabled = false
        profile_phone.isEnabled = false
        profile_email.isEnabled = false
        profile_camera.isEnabled = false
    }

    private fun setupNew(contact: Contact) {
        profile_make_call.visibility = View.GONE
        profile_send_email.visibility = View.GONE
        profile_edit_mode.visibility = View.GONE
        profile_special.setOnClickListener {
            saveContact(contact, it)
        }
    }

    private fun saveContact(contact: Contact, view: View) {
        if (validateFields(view)) {
            val datePattern = Regex("(.+)-(.+)-(.+)")
            val dateRes = datePattern.find(profile_birthday.editText?.text.toString())
                ?: error(profile_birthday.editText?.text.toString())
            val result = Contact(
                contact.id,
                profile_name.editText?.text.toString(),
                profile_surname.editText?.text.toString(),
                profile_gender.selectedItemPosition == 0,
                profile_email.editText?.text.toString(),
                profile_phone.editText?.text.toString(),
                Date(dateRes.groupValues[1], dateRes.groupValues[2], dateRes.groupValues[3]),
                profile_avatar.drawable.toBitmap()
            )
            if (result.id != 0) {
                _viewModel.updateContact(result)
            } else {
                _viewModel.saveContact(result)
            }
            _viewModel.selectedContact = null
            navigation.back()
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