package com.loongse.kmpapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

class TodoState {
    private val _todos = mutableStateOf(
        listOf(
            Todo(title = "学习 Kotlin Multiplatform"),
            Todo(title = "构建 Todo App", completed = true),
            Todo(title = "Push 到 GitHub", completed = true),
        )
    )
    val todos: List<Todo> get() = _todos.value
    val completedCount: Int get() = _todos.value.count { it.completed }
    val totalCount: Int get() = _todos.value.size

    fun add(title: String) {
        if (title.isNotBlank()) {
            _todos.value = _todos.value + Todo(title = title.trim())
        }
    }

    fun toggle(id: String) {
        _todos.value = _todos.value.map {
            if (it.id == id) it.copy(completed = !it.completed) else it
        }
    }

    fun delete(id: String) {
        _todos.value = _todos.value.filter { it.id != id }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen() {
    val state = remember { TodoState() }
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        state.add(inputText)
                        inputText = ""
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("New task") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (inputText.isNotEmpty()) {
                        IconButton(onClick = { inputText = "" }) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${state.completedCount} / ${state.totalCount} completed",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.todos, key = { it.id }) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggle = { state.toggle(it) },
                        onDelete = { state.delete(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TodoItem(
    todo: Todo,
    onToggle: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (todo.completed) MaterialTheme.colorScheme.surfaceVariant
        else MaterialTheme.colorScheme.surface
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.completed,
                onCheckedChange = { onToggle(todo.id) }
            )

            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (todo.completed) TextDecoration.LineThrough else null,
                color = if (todo.completed) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { onDelete(todo.id) }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}