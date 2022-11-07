package com.example.ft_hangouts.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ft_hangouts.User

class MyDbHelper(context: Context): SQLiteOpenHelper(context, "HangDB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(ID INTEGER PRIMARY KEY, NAME TEXT, PHONENUMBER TEXT, EMAIL TEXT, PROPIC TEXT, BIRTHDAY TEXT)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun getAll(): MutableMap<Int, User> {
        val users = mutableMapOf<Int, User>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM USERS", null)
        if (cursor != null && cursor.moveToNext()){
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex("ID")).toInt()
                users[id] = User(
                    id,
                    cursor.getString(cursor.getColumnIndex("NAME")),
                    cursor.getString(cursor.getColumnIndex("PHONENUMBER")),
                    cursor.getString(cursor.getColumnIndex("EMAIL")),
                    cursor.getString(cursor.getColumnIndex("EMAIL")),
                    cursor.getString(cursor.getColumnIndex("BIRTHDAY"))
                )
            }while (cursor.moveToNext())
        }
        return users
    }
}
