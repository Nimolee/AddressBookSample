package com.nimolee.addressbooksample.ui.recomended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.nimolee.addressbooksample.R
import com.nimolee.addressbooksample.ui.MainFragment
import com.nimolee.addressbooksample.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_recommended.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RecommendedFragment : MainFragment() {
    private val _viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recommended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.bottomBarVisibilityLiveData.postValue(true)
        recommended_refresh.setOnRefreshListener {
            _viewModel.getRandomUsers()
        }
        _viewModel.randomUserLiveData.observe(this, Observer {
            recommended_refresh.isRefreshing = false
            recommended_recycler.adapter = RecommendedAdapter(it, navigation, _viewModel)
        })
        if (_viewModel.randomUserLiveData.value == null) {
            recommended_refresh.isRefreshing = true
            _viewModel.getRandomUsers()
        }
    }
}