package com.example.notesapp.activities

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.adapters.NotesAdapter
import com.example.notesapp.database.DatabaseHelper
import com.example.notesapp.models.note
import com.example.notesapp.utils.SessionManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private lateinit var notesAdapter: NotesAdapter

    private var notes = mutableListOf<note>()


    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)


            binding = ActivityMainBinding.inflate(layoutInflater)

            setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        binding.recyclerNotes.layoutManager = LinearLayoutManager(this)

        notesAdapter = NotesAdapter(notes) { selectedNote ->

            val intent = Intent(
                this,
                EditNoteActivity::class.java
            )

            intent.putExtra("note", selectedNote)

            startActivity(intent)

        }

        binding.searchView.setOnQueryTextListener(

            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    val filteredList = notes.filter {

                        it.title.contains(newText ?: "", ignoreCase = true)
                                ||
                                it.subtitle.contains(newText ?: "", ignoreCase = true)

                    }

                    notesAdapter.filterList(filteredList)

                    return true
                }

            }

        )

        binding.recyclerNotes.adapter = notesAdapter

        loadNotes()


            setupBottomNavigation()


            binding.fabAddNote.setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        AddNoteActivity::class.java
                    )
                )

            }

    }
    override fun onResume() {
        super.onResume()

        loadNotes()
    }

    private fun loadNotes() {

        val userId = sessionManager.getUserId()

        notes = databaseHelper.getNotes(userId)

        notesAdapter.updateNotes(notes)
    }


    private fun setupBottomNavigation() {

        binding.bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_notes -> {

                    true

                }

                R.id.nav_account -> {

                    startActivity(
                        Intent(
                            this,
                            AccountActivity::class.java
                        )
                    )

                    true

                }

                else -> false

            }

        }

    }

}