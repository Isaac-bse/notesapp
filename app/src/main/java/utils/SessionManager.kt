package com.example.notesapp.utils

import android.content.Context

class SessionManager(context: Context) {

    private val preferences =
        context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveUserId(userId: Int) {

        preferences.edit()
            .putInt("user_id", userId)
            .apply()
    }

    fun getUserId(): Int {

        return preferences.getInt("user_id", -1)
    }

    fun logout() {

        preferences.edit().clear().apply()
    }
}