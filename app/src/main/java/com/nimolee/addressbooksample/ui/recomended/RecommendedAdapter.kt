package com.nimolee.addressbooksample.ui.recomended

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.data.wrappers.Contact
import com.nimolee.addressbooksample.ui.MainViewModel
import com.nimolee.addressbooksample.ui.NavigationInterface
import com.nimolee.addressbooksample.ui.profile.ProfileFragment

class RecommendedAdapter(items: ArrayList<Contact>, navigation: NavigationInterface, viewModel: MainViewModel) :
    RecyclerView.Adapter<RecommendedViewHolder>() {
    private val _items = items
    private val _navigation = navigation
    private val _viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacts, parent, false)
        return RecommendedViewHolder(view)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.onBindView(_items[position])
        holder.itemView.setOnClickListener {
            _viewModel.selectedContact = _items[position]
            _viewModel.profileMode = ProfileFragment.MODE_RECOMMENDED
            _navigation.openFragmentWithBackstack(ProfileFragment())
        }
    }
}