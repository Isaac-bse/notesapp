package com.example.notesapp.models

import java.io.Serializable

data class note(

    val id: Int,

    val title: String,

    val subtitle: String

) : Serializable