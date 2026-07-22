# Composable

**A reusable Compose Multiplatform UI library and showcase for Android, iOS,
desktop, and web.**

Composable collects polished UI components that solve practical interface
problems. The components live in the publishable `:ui` library, while
`:composeApp` presents them as a responsive object book. Components are grouped
into full-screen chapters that scroll vertically, with horizontal browsing
inside each chapter and continuous previous/next navigation in detail views.

![previews](https://github.com/user-attachments/assets/8f4f522e-3242-4f11-afc7-ea9d4e225ae3)

## Use the Library

After the first Maven Central release, add the library to `commonMain`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.ckgin:composable-ui:0.1.0")
        }
    }
}
```

Components use regular Compose APIs and can be called directly:

```kotlin
import com.ckgin.composable.component.acc.TypingText

TypingText(text = "Compose once, use everywhere")
```

## Supported Targets

* Android
* iOS device and simulator
* Desktop JVM
* Web (Wasm)

## Modules

| Module | Purpose |
| --- | --- |
| `:ui` | Publishable KMP/CMP component library and shared resources |
| `:composeApp` | Multiplatform component catalog and platform demos |
| `:core` | Existing Android support module |

Reusable source is under
`ui/src/commonMain/kotlin/com/ckgin/composable/component`. The showcase
consumes `:ui` as a normal project dependency, which keeps the local demo on the
same API that library users receive.

## Run the Showcase

```bash
./gradlew :composeApp:compileDebugKotlinAndroid
./gradlew :composeApp:compileKotlinIosSimulatorArm64
./gradlew :composeApp:run
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

## Publish to Maven Central

The `:ui` module is configured with the Vanniktech Maven Publish plugin and the
coordinates `com.ckgin:composable-ui:0.1.0`.

Before publishing, create a Central Portal account, verify the `com.ckgin`
namespace, and provide these Gradle properties outside the repository:

```properties
mavenCentralUsername=your-token-username
mavenCentralPassword=your-token-password
signingInMemoryKey=your-armored-gpg-private-key
signingInMemoryKeyPassword=your-gpg-password
```

They can also be supplied as `ORG_GRADLE_PROJECT_...` environment variables.
Never commit credentials or signing keys.

Publish with:

```bash
./gradlew :ui:publishToMavenCentral
```

See the [Central Portal registration guide](https://central.sonatype.org/register/central-portal/)
and [publishing plugin guide](https://vanniktech.github.io/gradle-maven-publish-plugin/central/)
for account, namespace, and GPG setup.

## Contributing

Components must be visually polished, useful in real applications, reusable,
accessible, responsive, and compatible with every supported target. Read
[CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request.

## License

Composable is available under the [MIT License](LICENSE).
