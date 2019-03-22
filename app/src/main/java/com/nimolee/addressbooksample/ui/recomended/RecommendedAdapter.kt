package com.nimolee.addressbooksample.ui.recomended

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.nimolee.addressbooksample.ui.NavigationInterface

class RecommendedAdapter(items: ArrayList<Contact>, navigation: NavigationInterface) :
    RecyclerView.Adapter<RecommendedViewHolder>() {
    private val _items = items
    private val _navigation = navigation

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacts, parent, false)
        return RecommendedViewHolder(view)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.onBindView(_items[position])
        holder.itemView.setOnClickListener {
            //TODO: open info.
        }
    }
}