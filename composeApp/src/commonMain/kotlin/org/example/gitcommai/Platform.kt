package org.example.gitcommai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform