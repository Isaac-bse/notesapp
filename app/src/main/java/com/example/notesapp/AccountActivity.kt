package com.example.notesapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.DatabaseHelper
import com.example.notesapp.databinding.ActivityAccountBinding
import com.example.notesapp.utils.SessionManager

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        databaseHelper = DatabaseHelper(this)

        val userId = sessionManager.getUserId()

        val user = databaseHelper.getUserById(userId)

        if (user != null) {

            binding.txtUsername.text = "Username: ${user.username}"
            binding.txtEmail.text = "Email: ${user.email}"

        }

        binding.btnSettings.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )

        }

        binding.btnLogout.setOnClickListener {

            sessionManager.logout()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finishAffinity()
        }

    }
}