package com.robothijau.todolistapp

import com.google.gson.annotations.SerializedName

data class TodoListModel(
    val id: String,
    val nama_tugas: String,
    val deskripsi: String,
    val tanggal: String,
    val status: String,
) : java.io.Serializable
