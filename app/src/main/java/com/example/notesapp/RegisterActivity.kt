package com.example.notesapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityRegisterBinding
import com.example.notesapp.database.DatabaseHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect XML with Kotlin
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)


        // Register button click
        binding.btnRegister.setOnClickListener {



            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()



            // Validation

            if (username.isEmpty()) {

                binding.etUsername.error = "Username required"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }


            if (email.isEmpty()) {

                binding.etEmail.error = "Email required"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }


            if (password.isEmpty()) {

                binding.etPassword.error = "Password required"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }


            if (confirmPassword.isEmpty()) {

                binding.etConfirmPassword.error =
                    "Confirm your password"

                binding.etConfirmPassword.requestFocus()
                return@setOnClickListener
            }



            if (password != confirmPassword) {

                binding.etConfirmPassword.error =
                    "Passwords do not match"

                binding.etConfirmPassword.requestFocus()
                return@setOnClickListener
            }



            if (password.length < 6) {

                binding.etPassword.error =
                    "Password must be at least 6 characters"

                binding.etPassword.requestFocus()
                return@setOnClickListener
            }



            // SQLite registration will be added later

            val inserted = databaseHelper.addUser(
                username,
                email,
                password
            )


            if (inserted) {

                Toast.makeText(
                    this,
                    "Registration successful",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Username already exists",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }



        // Back to login
        binding.txtLogin.setOnClickListener {

            finish()

        }

    }
}