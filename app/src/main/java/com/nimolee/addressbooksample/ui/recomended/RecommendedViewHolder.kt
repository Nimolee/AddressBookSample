package com.nimolee.addressbooksample.ui.recomended

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nimolee.addressbooksample.data.network.entity.ResultsItem
import kotlinx.android.synthetic.main.item_contacts.view.*

class RecommendedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun onBindView(item: ResultsItem) {
        with(itemView) {
            val fullName = "${item.name.first.toCapitalize()} ${item.name.last.toCapitalize()}"
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
        }
        //TODO: Load image
    }

    private fun String.toCapitalize(): String {
        return replaceFirst(first(), first().toUpperCase())
    }
}