package com.example.notesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.notesapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("settings", MODE_PRIVATE)

        // Load saved theme
        when (preferences.getString("theme", "system")) {
            "light" -> binding.rbLight.isChecked = true
            "dark" -> binding.rbDark.isChecked = true
            else -> binding.rbSystem.isChecked = true
        }

        binding.themeGroup.setOnCheckedChangeListener { _, checkedId ->

            val editor = preferences.edit()

            when (checkedId) {

                binding.rbLight.id -> {

                    editor.putString("theme", "light")
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )

                }

                binding.rbDark.id -> {

                    editor.putString("theme", "dark")
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )

                }

                binding.rbSystem.id -> {

                    editor.putString("theme", "system")
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    )

                }

            }

            editor.apply()

        }

    }
}