package com.android.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.myapplication.model.GuestModel
import com.android.myapplication.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = GuestRepository(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guests: LiveData<GuestModel> = guestModel

    private val _saveGuest = MutableLiveData<String>()
    val saveGuest: LiveData<String> = _saveGuest


    fun save(guest: GuestModel) {
        if (guest.id == 0) {
            if (repository.insert(guest)) {
                _saveGuest.value = "Inserção feita com sucesso!"
            } else {
                _saveGuest.value = "Falha"
            }
        } else {
            if (repository.update(guest)) {
                _saveGuest.value = "Atualização feita com sucesso!"
            } else {
                _saveGuest.value = "Falha"
            }

        }
    }

    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }
}

