package com.snow.aboutme.util

import java.util.*

fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (_: Exception) {
    null
}