**NOTE**: This is a fork of https://github.com/mpetuska/npm-publish.
The original author can no longer maintain the project, and I adopted it.

# NPM-PUBLISH GRADLE PLUGIN

Gradle plugin enabling NPM publishing (essentially `maven-publish` for NPM packages). Integrates seamlessly with
Kotlin/JS/MPP plugin if applied, providing auto configurations.

> Check the latest [release](https://github.com/mpetuska/npm-publish/releases/latest) for verified JVM, Kotlin and
> Gradle tooling versions

## Setup

Here's a bare minimum setup when using together with one of the kotlin plugins. This setup would produce the following
tasks:

* `assembleJsPackage`
* `packJsPackage`
* `publishJsPackageToNpmjsRegistry`

```kotlin title="build.gradle.kts"
plugins {
    id("org.danilopianini.npm.publish") version "<VERSION>"
    kotlin("multiplatform") version "<VERSION>>" // Optional, also supports "js"
}

kotlin {
    js(IR) {
        binaries.library()
        browser() // or nodejs()
    }
}

npmPublish {
    registries {
        // For registries expecting an authentiation token, use authToken
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set("obfuscated")
        }
        
        // For registries expecting a username and password, use auth or username + password
        register("nexus") {
            uri.set("https://nexus.example.com/repository/npm-internal")
            username.set("obfuscated")
            password.set("obfuscated")
            // Or:
            // auth.set("base64-encoded-string")
        }
    }
}
```

Full documentation can be found
on [npm-publish.petuska.dev](https://npm-publish.petuska.dev/latest/user-guide/quick-start/)

## Contributing

See [CONTRIBUTING](.github/CONTRIBUTING.md)

Thanks to all the people who contributed to npm-publish!

<a href="https://github.com/mpetuska/npm-publish/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=mpetuska/npm-publish" />
</a>
