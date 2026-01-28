import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension
import java.net.URI

fun Project.secret(key: String): Provider<String> =
  providers.environmentVariable(key)
    .orElse(providers.systemProperty(key))
    .orElse(providers.gradleProperty(key))

fun PublishingExtension.projectInfo(configuration: MavenPom.() -> Unit) {
  publications.withType<MavenPublication>().configureEach {
    pom(configuration)
  }
}

fun Project.signWith(
  keyId: Provider<String>,
  signingKey: Provider<String>,
  signingPassphrase: Provider<String>,
) {
  val signing = extensions.getByType(SigningExtension::class.java)
  signing.useInMemoryPgpKeys(
    keyId.orNull,
     signingKey.orNull,
    signingPassphrase.orNull
  )

  val publishing = extensions.getByType(PublishingExtension::class.java)
  signing.sign(publishing.publications)
}

fun RepositoryHandler.maven(
  url: Provider<URI>,
  username: Provider<String>,
  password: Provider<String>,
) {
  val repositoryUrl = url.orNull ?: return
  maven {
    this.url = repositoryUrl
    credentials {
      this.username = username.orNull
      this.password = password.orNull
    }
  }
}