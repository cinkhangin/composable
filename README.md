# Composable

**An open-source Compose Multiplatform app showcasing UI components and interactions for learning and inspiration.**

### Description

Composable is a multiplatform showcase built with Compose. The shared catalog runs on Android, iOS, desktop, and web from one Kotlin codebase.

---

![previews](https://github.com/user-attachments/assets/8f4f522e-3242-4f11-afc7-ea9d4e225ae3)

---

### Getting Started

1. **Clone the repo**:

   ```bash
   git clone https://github.com/cinkhangin/composable.git
   cd composable
   ```

2. **Open in Android Studio**:
   Simply import the project, sync Gradle, and run the app on your device or emulator.

3. **Explore & Learn**:
   Browse `composeApp/src/commonMain/kotlin` to inspect the shared components.

### Multiplatform App

`:composeApp` is the application module for Android, iOS, desktop, and web. Components and resources live in `commonMain`; platform source sets contain only platform-specific entry points and services.

Useful Gradle tasks:

```bash
./gradlew :composeApp:compileDebugKotlinAndroid
./gradlew :composeApp:compileKotlinIosSimulatorArm64
./gradlew :composeApp:run
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

---

### Why Use It?

* **Inspiration**: Discover engaging UI ideas and transitions
* **Learning Resource**: Study clean Compose implementations in Kotlin
* **Copy & Customize**: Easily reuse components in your own projects

---

### Contribute

Contributions are warmly welcome! Whether it’s a new component, improved UI, or better documentation-feel free to submit a pull request or open an issue.

---

### License

This project is licensed under the **MIT License**—see the [LICENSE](LICENSE) file for details.
