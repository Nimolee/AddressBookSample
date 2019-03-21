package com.nimolee.addressbooksample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nimolee.addressbooksample.data.Repository
import com.nimolee.addressbooksample.data.network.entity.RandomMeEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(repository: Repository) : ViewModel() {
    val randomUserLiveData: MutableLiveData<RandomMeEntity> = MutableLiveData()

    private val _repository = repository

    fun getRandomUsers() {
        GlobalScope.launch {
            randomUserLiveData.postValue(_repository.getRandomUsersAwait())
        }
    }
}