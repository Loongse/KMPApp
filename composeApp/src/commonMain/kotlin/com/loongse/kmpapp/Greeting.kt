package com\loongse\kmpapp

data class Greeting(val text: String)

fun greet(): Greeting {
    val platform = getPlatform()
    return Greeting("Hello, ${platform.name}!")
}