package com\loongse\kmpapp

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
fun getPlatform(): Platform = when {
    Platform.osFamily == OsFamily.ANDROID -> Platform.Android
    Platform.osFamily == OsFamily.IOS -> Platform.IOS
    Platform.isJS -> Platform.Web
    else -> Platform.Desktop
}

sealed interface Platform {
    data object Android : Platform
    data object Desktop : Platform
    data object IOS : Platform
    data object Web : Platform
    val name: String
        get() = when (this) {
            Android -> "Android"
            Desktop -> "Desktop"
            IOS -> "iOS"
            Web -> "Web"
        }
}