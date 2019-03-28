package com.nimolee.addressbooksample.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.MainActivity.Companion.FAB_EXTENDED_MODE
import com.nimolee.addressbooksample.ui.MainActivity.Companion.FAB_SHRINK_MODE
import com.nimolee.addressbooksample.ui.MainFragment
import com.nimolee.addressbooksample.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_saved.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SavedFragment : MainFragment() {
    private val _viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.bottomBarVisibilityLiveData.postValue(true)
        _viewModel.savedUserLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                saved_recycler.visibility = View.VISIBLE
                saved_placeholder_image.visibility = View.GONE
                saved_placeholder_text.visibility = View.GONE
                saved_recycler.adapter = SavedAdapter(it, navigation, _viewModel)
            } else {
                saved_recycler.visibility = View.GONE
                saved_placeholder_image.visibility = View.VISIBLE
                saved_placeholder_text.visibility = View.VISIBLE
            }
        })
        if (_viewModel.savedUserLiveData.value == null) {
            _viewModel.getSavedContacts()
        }
        saved_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    _viewModel.fabExtendLiveData.postValue(FAB_SHRINK_MODE)
                } else {
                    _viewModel.fabExtendLiveData.postValue(FAB_EXTENDED_MODE)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
}