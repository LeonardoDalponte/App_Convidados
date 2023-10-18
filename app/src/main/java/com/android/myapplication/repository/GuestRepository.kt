package com.android.myapplication.repository

import android.content.ContentValues
import android.content.Context
import com.android.myapplication.constants.DataBaseConstants
import com.android.myapplication.model.GuestModel

//cuida das alteracoes de dados do banco
class GuestRepository private constructor(context: Context) {

    private val GuestDataBase = GuestDataBase(context)

    //Singleton
    companion object {
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }

    }


    fun insert(guest: GuestModel): Boolean {
        try {

            //writableDatabase para insercao pois, write simula gravar do tipo escrever ou inserir
            val db = GuestDataBase.writableDatabase


            val presence = if (guest.precense) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)


            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            return true

        } catch (e: Exception) {
            return false
        }


    }


    fun update(guest: GuestModel): Boolean {
        return try {

            val db = GuestDataBase.writableDatabase

            val presence = if (guest.precense) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)


            //selection é como se fosse id igual a um valor que n se sabe ainda
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"

            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true

        } catch (e: Exception) {
            false
        }
    }


    fun delete(id: Int): Boolean {
        return try {

            val db = GuestDataBase.writableDatabase


            //selection é como se fosse id igual a um valor que n se sabe ainda
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"

            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true

        } catch (e: Exception) {
            false
        }
    }


    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {

            val db = GuestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
                )

            //0,    1,     2
            //id, name, presence

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val precence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))


                    list.add(GuestModel(id, name, precence == 1))
                }
            }
            //temos que fechar o cursor depois de usalo
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }


    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null

        try {

            val db = GuestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            //0,    1,     2
            //id, name, presence

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {


                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val precence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))


                    guest = GuestModel(id, name, precence == 1)
                }
            }
            //temos que fechar o cursor depois de usalo
            cursor.close()

        } catch (e: Exception) {
            return guest
        }
        return guest
    }


    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = GuestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf("1")

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

           //val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null) tambem funciona




            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val precence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))


                    list.add(GuestModel(id, name, precence == 1))

                }
            }
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }


    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {

            val db = GuestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf("0")

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )


            //  val cursor = db.rawQuery("SELECT id,name,presence FROM Guest WHERE presence = 0", null) tambem funciona


            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val precence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))


                    list.add(GuestModel(id, name, precence == 1))

                }
            }
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }
}

