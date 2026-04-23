package com.loongse.kmpapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform