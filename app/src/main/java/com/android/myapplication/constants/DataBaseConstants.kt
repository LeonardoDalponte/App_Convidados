package com.android.myapplication.constants

class DataBaseConstants {
    companion object GUEST {

        const val TABLE_NAME = "guestdb"
        const val ID = "guestid"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}