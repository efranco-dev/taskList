package br.com.axweb.tasklist

import java.util.UUID

data class TaskItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    var isCompleted: Boolean = false
)
