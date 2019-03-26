package com.nimolee.addressbooksample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nimolee.addressbooksample.data.Repository
import com.nimolee.addressbooksample.data.wrappers.Contact
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(repository: Repository) : ViewModel() {
    val randomUserLiveData: MutableLiveData<ArrayList<Contact>> = MutableLiveData()
    val savedUserLiveData: MutableLiveData<ArrayList<Contact>> = MutableLiveData()
    val bottomBarVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var selectedContact: Contact? = null
    var profileMode: Int? = null

    private val _repository = repository

    fun getRandomUsers() {
        GlobalScope.launch {
            randomUserLiveData.postValue(_repository.getRandomUsersAwait())
        }
    }

    fun saveContact(contact: Contact) {
        GlobalScope.launch {
            _repository.saveContact(contact)
            getSavedContacts()
        }
    }

    fun removeContact(contact: Contact) {
        GlobalScope.launch {
            _repository.removeContact(contact)
            getSavedContacts()
        }
    }

    fun getSavedContacts() {
        GlobalScope.launch {
            _repository.getSavedContacts().also { array ->
                array.sortBy {
                    "${it.name} ${it.surname}"
                }
                savedUserLiveData.postValue(array)
            }
        }
    }
}