package com.example.itp_wearos.models

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    val due_time: String,
    val create_time: String,
    val is_done: Int,
)