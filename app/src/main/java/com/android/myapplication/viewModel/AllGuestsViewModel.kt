package com.android.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.myapplication.model.GuestModel
import com.android.myapplication.repository.GuestRepository

class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository(application.applicationContext)

    private val listAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listAllGuests


    fun getAll() {
        listAllGuests.value = repository.getAll()
    }

    fun getPresent() {
        listAllGuests.value = repository.getPresent()
    }

    fun getAbsent() {
        listAllGuests.value = repository.getAbsent()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}