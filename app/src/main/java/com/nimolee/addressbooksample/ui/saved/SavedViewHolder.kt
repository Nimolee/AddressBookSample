package com.nimolee.addressbooksample.ui.saved

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nimolee.addressbooksample.data.wrappers.Contact
import kotlinx.android.synthetic.main.item_contacts.view.*

class SavedViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
    fun onBindView(item: Contact) {
        with(itemView) {
            val fullName = "${item.name} ${item.surname}"
            item_contact_name.text = fullName
            if (item.email.isNotBlank()) {
                item_contact_email.text = item.email
                item_contact_email.visibility = View.VISIBLE
            } else {
                item_contact_email.visibility = View.GONE
            }
            if (item.phone.isNotBlank()) {
                item_contact_phone.text = item.phone
                item_contact_phone.visibility = View.VISIBLE
            } else {
                item_contact_phone.visibility = View.GONE
            }
            item_contact_avatar.setImageBitmap(item.photo)
        }
    }
}