# Contributing to Composable

Thanks for contributing to Composable. This project is a multiplatform catalog
of polished, reusable Compose components for Android, iOS, desktop, and web.

## Component Standard

A component should be both:

1. **Cool and beautiful**: it has a polished visual design, thoughtful motion,
   and a complete interaction experience where appropriate.
2. **Useful to others**: it solves a recognizable UI problem and can be reused
   or adapted in a real application.

Visual novelty alone is not enough. A contribution should have a clear purpose,
such as navigation, feedback, data display, input, progress, media, layout, or
another practical interface need.

### Reusability requirements

New components should:

* Accept a `Modifier` so callers can position and size them.
* Expose meaningful content, style, and behavior as parameters instead of
  hard-coding demo values.
* Hoist state and provide callbacks when the caller needs to observe or control
  an interaction.
* Keep sample data and full-screen presentation code in a demo wrapper when the
  reusable component itself can be smaller.
* Work at different reasonable sizes without clipping or overlapping.
* Support the project theme and remain readable in light and dark modes.
* Include accessibility semantics, labels, and adequate touch targets where
  relevant.
* Avoid unnecessary dependencies and duplicated utilities.

## Multiplatform Requirements

Components belong in `ui/src/commonMain` and must use Compose
Multiplatform APIs. They should compile and provide a useful experience on all
supported targets:

* Android
* iOS
* Desktop
* Web (Wasm)

Do not use Android-only APIs in `commonMain`. When a feature truly needs a
platform service, define a small `expect` API in `commonMain` and provide
`actual` implementations in the platform source sets. A platform that cannot
offer the full behavior should still receive a stable, meaningful fallback.

## Project Structure

Place shared component code under:

```text
ui/src/commonMain/kotlin/com/naulian/composable/component/
```

Use the existing category that best fits the component:

* `acc`: animated components
* `icc`: interactive components
* `scc`: static components

Add the component and its demo metadata to `ComponentCatalog.kt`. Keep the
reusable component separate from catalog-only framing or sample content.

Shared images, fonts, and other resources belong in:

```text
ui/src/commonMain/composeResources/
```

## Development Workflow

1. Fork and clone the repository.
2. Create a focused feature branch.
3. Implement the shared component and catalog demo.
4. Test its behavior, responsiveness, theme support, and accessibility.
5. Open a pull request against `develop`.

Describe the component's practical use case in the pull request. Include
screenshots or a short recording, preferably showing more than one target and
more than one size.

## Verification

Before submitting, run the relevant checks:

```bash
./gradlew :ui:assemble
./gradlew :composeApp:compileDebugKotlinAndroid
./gradlew :composeApp:compileKotlinDesktop
./gradlew :composeApp:compileKotlinIosSimulatorArm64
./gradlew :composeApp:compileKotlinWasmJs
```

Also verify the component interactively on the targets affected by your change.
A contribution must not break existing catalog demos.

## Review Criteria

Maintainers will consider:

* Practical usefulness and reusability
* Visual and interaction quality
* Multiplatform behavior
* Accessibility and responsive layout
* API clarity and code maintainability
* Tests or reasonable manual verification
* Fit with the catalog without unnecessary duplication

Meeting every checkbox does not guarantee acceptance, but review decisions
should be based on these criteria and explained constructively.

## Existing Components and Removal

The same standard applies to existing and new components. Maintainers may
refactor, migrate, deprecate, or remove a component when it no longer meets the
project standard, does not work across supported targets, duplicates another
component, or cannot be maintained safely.

When practical, maintainers should explain the reason in an issue, pull request,
or release note and give contributors an opportunity to update the component.
Removal from the current catalog does not erase the contributor's credit or the
project's Git history.

## License

By contributing, you agree that your contribution is provided under the
repository's MIT License and that it may be reviewed, modified, redistributed,
or removed as the project evolves.

## Code of Conduct

Be respectful and constructive in issues, reviews, and pull requests. Critique
the work against the documented project standard, not the person who created
it.
