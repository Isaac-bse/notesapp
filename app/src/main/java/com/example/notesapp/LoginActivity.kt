package com.example.notesapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityLoginBinding
import com.example.notesapp.database.DatabaseHelper
import com.example.notesapp.utils.SessionManager
import androidx.appcompat.app.AppCompatDelegate

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = getSharedPreferences("settings", MODE_PRIVATE)

        when (preferences.getString("theme", "system")) {

            "light" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

            "dark" -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )

            else -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )
        }
        super.onCreate(savedInstanceState)

        // Connect XML layout with Kotlin
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Display the layout
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        // Login button click
        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()


            // Validate username
            if (username.isEmpty()) {

                binding.etUsername.error = "Username required"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }


            // Validate password
            if (password.isEmpty()) {

                binding.etPassword.error = "Password required"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }


            val user = databaseHelper.getUser(
                username,
                password
            )

            if (user != null) {

                // Save logged-in user's ID
                sessionManager.saveUserId(user.id)

                Toast.makeText(
                    this,
                    "Welcome ${user.username}",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


        // Open signup page
        binding.txtSignup.setOnClickListener {

            val intent = Intent(
                this,
                RegisterActivity::class.java
            )

            startActivity(intent)
        }

    }
}