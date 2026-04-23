package com.loongse.kmpapp

import androidx.compose.runtime.Immutable

@Immutable
data class Todo(
    val id: String = generateId(),
    val title: String,
    val completed: Boolean = false
) {
    companion object {
        private var counter = 0
        fun generateId(): String = "todo_${++counter}_${kotlin.random.Random.nextInt(10000)}"
    }
}