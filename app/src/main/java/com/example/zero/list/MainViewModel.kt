package com.example.zero.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zero.Client
import com.example.zero.list.data.ClientRepository

class MainViewModel(
    private val repository: ClientRepository
) : ViewModel() {
    private val _isOrderByName = MutableLiveData(true)
    val isOrderByName: LiveData<Boolean> = _isOrderByName

    private val _clientsMutableLiveData = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> = _clientsMutableLiveData

    fun getClients() {
        _clientsMutableLiveData.value = repository.getClient()
    }

    fun getClientsOrdered() {
        val clientsValue = _clientsMutableLiveData.value
        val isOrderByNameValue = _isOrderByName.value
        if (clientsValue != null && isOrderByNameValue != null) {
            _clientsMutableLiveData.value =
                if (isOrderByNameValue)
                    clientsValue.sortedBy { it.name }
                else
                    clientsValue.sortedByDescending { it.counter }
        }
    }


    fun changeOrder() {
        _isOrderByName.value = _isOrderByName.value?.not()
    }
}