package com.nimolee.addressbooksample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nimolee.addressbooksample.data.Repository
import com.nimolee.addressbooksample.data.wrappers.Contact
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(repository: Repository) : ViewModel() {
    val randomUserLiveData: MutableLiveData<ArrayList<Contact>> = MutableLiveData()

    private val _repository = repository

    fun getRandomUsers() {
        GlobalScope.launch {
            randomUserLiveData.postValue(_repository.getRandomUsersAwait())
        }
    }
}