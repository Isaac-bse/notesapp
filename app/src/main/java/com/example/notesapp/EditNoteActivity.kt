package com.example.notesapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.database.DatabaseHelper
import com.example.notesapp.databinding.ActivityEditNoteBinding
import com.example.notesapp.models.note

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var currentNote: note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        // Receive the selected note
        currentNote = intent.getSerializableExtra("note") as note

        // Display note details
        binding.etTitle.setText(currentNote.title)
        binding.etSubtitle.setText(currentNote.subtitle)

        // Update button
        binding.btnUpdate.setOnClickListener {

            val title = binding.etTitle.text.toString().trim()
            val subtitle = binding.etSubtitle.text.toString().trim()

            if (title.isEmpty()) {
                binding.etTitle.error = "Title required"
                return@setOnClickListener
            }

            if (subtitle.isEmpty()) {
                binding.etSubtitle.error = "Note required"
                return@setOnClickListener
            }

            val success = databaseHelper.updateNote(
                currentNote.id,
                title,
                subtitle
            )

            if (success) {

                Toast.makeText(
                    this,
                    "Note updated",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Update failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Delete button
        binding.btnDelete.setOnClickListener {

            val success = databaseHelper.deleteNote(
                currentNote.id
            )

            if (success) {

                Toast.makeText(
                    this,
                    "Note deleted",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Delete failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}