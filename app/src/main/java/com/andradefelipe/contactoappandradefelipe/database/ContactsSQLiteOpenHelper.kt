package com.andradefelipe.contactoappandradefelipe.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.andradefelipe.contactoappandradefelipe.ContactoModelClass
import java.sql.Array


class ContactsSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Companion.SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(Companion.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ContactosAndradeFelipe.db"
        const val TABLE_NAME = "Contacto"
        const val COLUMN_NAME_USERID = "UserID"
        const val COLUMN_NAME_FIRSTNAME = "FirstName"
        const val COLUMN_NAME_LASTNAME = "LastName"
        const val COLUMN_NAME_PHONENUMBER = "PhoneNumber"
        const val COLUMN_NAME_EMAIL = "Email"
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        const val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLE_NAME} (" +
                "${COLUMN_NAME_USERID} INTEGER PRIMARY KEY," +
                "${COLUMN_NAME_FIRSTNAME} TEXT," +
                "${COLUMN_NAME_LASTNAME} TEXT," +
                "${COLUMN_NAME_PHONENUMBER} TEXT," +
                "${COLUMN_NAME_EMAIL} TEXT)"
    }

    fun addContactos( contacto: ContactoModelClass) : Int {
        // Gets the data repository in write mode
        val db = this.writableDatabase

// Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(COLUMN_NAME_USERID, contacto.userId)
            put(COLUMN_NAME_FIRSTNAME, contacto.firstName)
            put(COLUMN_NAME_LASTNAME, contacto.lastName)
            put(COLUMN_NAME_PHONENUMBER, contacto.phoneNumber)
            put(COLUMN_NAME_EMAIL, contacto.emailAddress)
        }

// Insert the new row, returning the primary key value of the new row
        val status = db?.insert(TABLE_NAME, null, values)
        return status!!.toInt()
    }

    fun readContacto() : ArrayList<ContactoModelClass>{

        val db = this.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(COLUMN_NAME_USERID, COLUMN_NAME_FIRSTNAME, COLUMN_NAME_LASTNAME, COLUMN_NAME_PHONENUMBER, COLUMN_NAME_EMAIL)


        val selection = "${COLUMN_NAME_USERID} = ?"
        val selectionArgs = arrayOf(COLUMN_NAME_USERID, COLUMN_NAME_FIRSTNAME, COLUMN_NAME_LASTNAME, COLUMN_NAME_PHONENUMBER, COLUMN_NAME_EMAIL)

        // ORDENADO ASCENDENTEMENTE POR USERID....
        val sortOrder = "$COLUMN_NAME_USERID ASC"

        val cursor = db.query(
            TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        val contactos = arrayListOf<ContactoModelClass>()
        with(cursor) {
            while (moveToNext()) {
                val userId = getInt(getColumnIndexOrThrow(COLUMN_NAME_USERID))
                val firstname = getString(getColumnIndexOrThrow(COLUMN_NAME_FIRSTNAME))
                val lastname = getString(getColumnIndexOrThrow(COLUMN_NAME_LASTNAME))
                val phonenumber = getString(getColumnIndexOrThrow(COLUMN_NAME_PHONENUMBER))
                val email = getString(getColumnIndexOrThrow(COLUMN_NAME_EMAIL))
                contactos.add(ContactoModelClass(userId, firstname, lastname, phonenumber, email))
            }
        }
        return contactos
    }
}
