package com.example.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notesapp.models.note
import com.example.notesapp.models.User


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {

        private const val DATABASE_NAME = "NotesApp.db"

        private const val DATABASE_VERSION = 1


        // User table

        private const val TABLE_USERS = "users"

        private const val COLUMN_ID = "id"

        private const val COLUMN_USERNAME = "username"

        private const val COLUMN_EMAIL = "email"

        private const val COLUMN_PASSWORD = "password"

        // Notes table

        private const val TABLE_NOTES = "notes"

        private const val COLUMN_NOTE_ID = "id"

        private const val COLUMN_USER_ID = "userId"

        private const val COLUMN_TITLE = "title"

        private const val COLUMN_SUBTITLE = "subtitle"

    }



    override fun onCreate(db: SQLiteDatabase?) {


        val createUsersTable = """
            
            CREATE TABLE $TABLE_USERS (
            
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            
            $COLUMN_USERNAME TEXT UNIQUE,
            
            $COLUMN_EMAIL TEXT,
            
            $COLUMN_PASSWORD TEXT
            
            )
            
        """.trimIndent()


        db?.execSQL(createUsersTable)


        val createNotesTable = """
            
            CREATE TABLE $TABLE_NOTES (
            
            $COLUMN_NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            
            $COLUMN_USER_ID INTEGER,
            
            $COLUMN_TITLE TEXT,
            
            $COLUMN_SUBTITLE TEXT
            
            )
            
        """.trimIndent()


        db?.execSQL(createNotesTable)

    }



    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

        db?.execSQL(
            "DROP TABLE IF EXISTS $TABLE_USERS"
        )

        onCreate(db)

    }




    // Insert user

    fun addUser(
        username: String,
        email: String,
        password: String
    ): Boolean {


        val db = writableDatabase


        val values = ContentValues()

        values.put(COLUMN_USERNAME, username)

        values.put(COLUMN_EMAIL, email)

        values.put(COLUMN_PASSWORD, password)



        val result =
            db.insert(TABLE_USERS, null, values)



        db.close()


        return result != -1L

    }




    // Check login

    fun getUser(
        username: String,
        password: String
    ): User? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT * FROM $TABLE_USERS
        WHERE $COLUMN_USERNAME = ?
        AND $COLUMN_PASSWORD = ?
        """.trimIndent(),
            arrayOf(username, password)
        )

        var user: User? = null

        if (cursor.moveToFirst()) {

            user = User(

                id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_ID)
                ),

                username = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_USERNAME)
                ),

                email = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_EMAIL)
                )

            )
        }

        cursor.close()
        db.close()

        return user
    }

    fun addNote(
        userId: Int,
        title: String,
        subtitle: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_USER_ID, userId)
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_SUBTITLE, subtitle)

        val result = db.insert(TABLE_NOTES, null, values)

        db.close()

        return result != -1L
    }

    fun getNotes(userId: Int): MutableList<note> {

        val notes = mutableListOf<note>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT * FROM $TABLE_NOTES
        WHERE $COLUMN_USER_ID = ?
        ORDER BY $COLUMN_NOTE_ID DESC
        """.trimIndent(),
            arrayOf(userId.toString())
        )

        if (cursor.moveToFirst()) {

            do {

                val note = note(

                    id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COLUMN_NOTE_ID)
                    ),

                    title = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_TITLE)
                    ),

                    subtitle = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_SUBTITLE)
                    )

                )

                notes.add(note)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return notes
    }

    fun getUserById(userId: Int): User? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT * FROM $TABLE_USERS
        WHERE $COLUMN_ID = ?
        """.trimIndent(),
            arrayOf(userId.toString())
        )

        var user: User? = null

        if (cursor.moveToFirst()) {

            user = User(

                id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_ID)
                ),

                username = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_USERNAME)
                ),

                email = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_EMAIL)
                )

            )
        }

        cursor.close()
        db.close()

        return user
    }

    fun updateNote(
        noteId: Int,
        title: String,
        subtitle: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_SUBTITLE, subtitle)

        val result = db.update(
            TABLE_NOTES,
            values,
            "$COLUMN_NOTE_ID=?",
            arrayOf(noteId.toString())
        )

        db.close()

        return result > 0
    }

    fun deleteNote(noteId: Int): Boolean {

        val db = writableDatabase

        val result = db.delete(
            TABLE_NOTES,
            "$COLUMN_NOTE_ID=?",
            arrayOf(noteId.toString())
        )

        db.close()

        return result > 0
    }

}