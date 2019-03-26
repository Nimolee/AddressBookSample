package com.nimolee.addressbooksample.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SavedFragment : Fragment() {
    private val _viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.bottomBarVisibilityLiveData.postValue(true)
        _viewModel.savedUserLiveData.observe(this, Observer {
        })
        _viewModel.getSavedContacts()
    }
}