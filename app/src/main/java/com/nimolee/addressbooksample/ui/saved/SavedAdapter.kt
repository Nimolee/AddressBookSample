package com.nimolee.addressbooksample.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.nimolee.addressbooksample.ui.MainViewModel
import com.nimolee.addressbooksample.ui.NavigationInterface
import com.nimolee.addressbooksample.ui.profile.ProfileFragment

class SavedAdapter(items: ArrayList<Contact>, navigation: NavigationInterface, viewModel: MainViewModel) :
    RecyclerView.Adapter<SavedViewHolder>() {
    private val _items = items
    private val _navigation = navigation
    private val _viewModel = viewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacts, parent, false)
        return SavedViewHolder(view)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.onBindView(_items[position])
        holder.itemView.setOnClickListener {
            _viewModel.selectedContact = _items[position]
            _viewModel.profileMode = ProfileFragment.MODE_SAVED
            _navigation.openSecondaryFragment(ProfileFragment())
        }
    }
}