# Serene

**An expressive Compose Multiplatform UI library for Android, iOS, desktop,
and web.**

Serene is a growing collection of polished, reusable components built for real
interfaces. Every component uses shared Compose APIs and is presented in a
responsive documentation experience with live previews, source examples,
installation guidance, and API details.

The visual language pairs practical component design with expressive motion,
shape, color, and interaction—so multiplatform products can feel consistent
without feeling generic.

![Serene component library preview](https://github.com/user-attachments/assets/8f4f522e-3242-4f11-afc7-ea9d4e225ae3)

## Highlights

* Shared Compose components from `commonMain`
* Live component previews and source browsing
* Animated, interactive, and structural component collections
* Responsive showcase for desktop and mobile screens
* Android, iOS, desktop JVM, and WebAssembly targets
* MIT licensed and designed for extension

## Install

After the first Maven Central release, add the library to `commonMain`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.ckgin:serene-ui:0.1.0")
        }
    }
}
```

Components use regular Compose APIs and can be called directly from shared
code:

```kotlin
import com.ckgin.serene.component.acc.TypingText

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
`ui/src/commonMain/kotlin/com/ckgin/serene/component`. The showcase
consumes `:ui` as a normal project dependency, which keeps the local demo on the
same API that library users receive.

## Run the Documentation Site

```bash
./gradlew :composeApp:compileDebugKotlinAndroid
./gradlew :composeApp:compileKotlinIosSimulatorArm64
./gradlew :composeApp:run
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

The web development server is available at `http://localhost:8080`.

## Deploy the Web Showcase

Pushes to `main` deploy the production Wasm build to Cloudflare Pages through
GitHub Actions. Create a Pages project named `serene`, then add these
repository secrets:

* `CLOUDFLARE_ACCOUNT_ID`
* `CLOUDFLARE_API_TOKEN`

The API token needs Cloudflare Pages edit permission. Build locally with:

```bash
./gradlew :composeApp:wasmJsBrowserDistribution
```

The deployable files are generated in
`composeApp/build/dist/wasmJs/productionExecutable`. To deploy them manually:

```bash
npx wrangler pages deploy composeApp/build/dist/wasmJs/productionExecutable --project-name=serene
```

If the Pages project uses another name, update it in `wrangler.toml` and
`.github/workflows/deploy-pages.yml`.

## Publish to Maven Central

The `:ui` module is configured with the Vanniktech Maven Publish plugin and the
coordinates `com.ckgin:serene-ui:0.1.0`.

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

Serene is available under the [MIT License](LICENSE).
