package com.android.myapplication.repository


import android.content.Context
import com.android.myapplication.model.GuestModel

//cuida das alteracoes de dados do banco
class GuestRepository(context: Context) {

    private val guestDataBase = GuestDataBase.getDataBase(context).guestDAO()

    fun insert(guest: GuestModel): Boolean {
        return guestDataBase.insert(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return guestDataBase.update(guest) > 0
    }


    fun delete(id: Int) {
        val guest = get(id)
        guestDataBase.delete(guest)
    }


    fun getAll(): List<GuestModel> {
        return guestDataBase.getAll()
    }


    fun get(id: Int): GuestModel {
        return guestDataBase.get(id)
    }


    fun getPresent(): List<GuestModel> {
        return guestDataBase.getPresent()
    }


    fun getAbsent(): List<GuestModel> {
        return guestDataBase.getAbsent()
    }
}
