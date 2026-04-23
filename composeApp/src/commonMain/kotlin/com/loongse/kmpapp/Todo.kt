package com\loongse\kmpapp

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Todo(
    val id: Uuid = Uuid.random(),
    val title: String,
    val completed: Boolean = false
)