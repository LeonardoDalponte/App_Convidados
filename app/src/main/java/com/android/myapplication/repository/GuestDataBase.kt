package com.android.myapplication.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.myapplication.model.GuestModel

//cuida da conexao com banco
@Database(entities = [GuestModel::class], version = 1)
abstract class GuestDataBase() : RoomDatabase() {

    abstract fun guestDAO(): GuestDAO

    companion object {
        private lateinit var INSTANCE: GuestDataBase
        fun getDataBase(context: Context): GuestDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(GuestDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, GuestDataBase::class.java, "Guestdb")
                        .addMigrations(migration_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }


        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DELETE FROM Guest")
            }
        }
    }
}



