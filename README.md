## 📱 TechNews-KMP (Compose Multiplatform)

A Kotlin Multiplatform app that delivers the latest tech news with a seamless UI across Android and iOS using Jetpack Compose.

### 🚀 Features
- Cross-platform UI with **Jetpack Compose Multiplatform**
- Smooth image loading using **Coil**
- Fetches news via **Ktor HTTP client**
- Secure API handling with **BuildKonfig**
- Persistent animation storage with platform-specific implementations

### 🔧 Tech Stack
- **Jetpack Compose** – Unified UI toolkit for Android & iOS  
- **[Ktor](https://github.com/ktorio/ktor)** – Asynchronous API calls  
- **[Coil](https://github.com/coil-kt/coil)** – Efficient image loading  
- **[BuildKonfig](https://github.com/yshrsmz/BuildKonfig)** – Secure API key management  
- **expect/actual** – Platform-specific logic for persistent storage:
  - Android → `SharedPreferences`
  - iOS → `UserDefaults`

### 📂 Storage Handling
Animations are:
1. Downloaded and cached in memory  
2. Stored permanently using platform-specific methods for reuse



## This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
