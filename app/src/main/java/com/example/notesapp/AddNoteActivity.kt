package com.example.notesapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.DatabaseHelper
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.utils.SessionManager
import android.view.animation.AnimationUtils
import com.example.notesapp.R

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.startAnimation(

            AnimationUtils.loadAnimation(
                this,
                R.anim.fade_in
            )

        )

        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        binding.btnSave.setOnClickListener {

            val title = binding.etTitle.text.toString().trim()
            val subtitle = binding.etSubtitle.text.toString().trim()

            if (title.isEmpty()) {
                binding.etTitle.error = "Title required"
                binding.etTitle.requestFocus()
                return@setOnClickListener
            }

            if (subtitle.isEmpty()) {
                binding.etSubtitle.error = "Note required"
                binding.etSubtitle.requestFocus()
                return@setOnClickListener
            }

            val userId = sessionManager.getUserId()

            val success = databaseHelper.addNote(
                userId,
                title,
                subtitle
            )

            if (success) {

                Toast.makeText(
                    this,
                    "Note saved successfully",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Failed to save note",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}