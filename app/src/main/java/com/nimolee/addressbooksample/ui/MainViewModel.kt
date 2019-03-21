package com.nimolee.addressbooksample.ui

import androidx.lifecycle.ViewModel
import com.nimolee.addressbooksample.data.Repository

class MainViewModel(repository: Repository) : ViewModel() {
    private val _repository = repository
}