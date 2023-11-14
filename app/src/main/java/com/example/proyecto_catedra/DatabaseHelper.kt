package com.example.proyecto_catedra

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, UNIQUE_DATABASE_NAME, null, UNIQUE_DATABASE_VERSION) {
    companion object {
        private const val UNIQUE_DATABASE_NAME = "unique_app_data.db"
        private const val UNIQUE_DATABASE_VERSION = 3
        private const val UNIQUE_TABLE_NAME = "unique_users"
        private const val UNIQUE_COLUMN_ID = "_unique_id"
        private const val UNIQUE_COLUMN_USERNAME = "unique_username"
        private const val UNIQUE_COLUMN_PASSWORD = "unique_password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $UNIQUE_TABLE_NAME (" +
                "$UNIQUE_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$UNIQUE_COLUMN_USERNAME TEXT NOT NULL, " +
                "$UNIQUE_COLUMN_PASSWORD TEXT NOT NULL);")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $UNIQUE_TABLE_NAME")
        onCreate(db)
    }

    fun insertUniqueUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(UNIQUE_COLUMN_USERNAME, username)
        values.put(UNIQUE_COLUMN_PASSWORD, password)
        return db.insert(UNIQUE_TABLE_NAME, null, values)
    }

    fun loginUniqueUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$UNIQUE_COLUMN_USERNAME = ? AND $UNIQUE_COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(UNIQUE_TABLE_NAME, null, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        return count > 0
    }
}
