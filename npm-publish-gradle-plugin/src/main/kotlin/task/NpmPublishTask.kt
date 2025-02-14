package dev.petuska.npm.publish.task

import dev.petuska.npm.publish.extension.NpmPublishExtension
import dev.petuska.npm.publish.extension.domain.NpmRegistry
import dev.petuska.npm.publish.util.configure
import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.provider.Property
import org.gradle.api.publish.plugins.PublishingPlugin.PUBLISH_TASK_GROUP
import org.gradle.api.tasks.*
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import java.util.*
import javax.inject.Inject

/**
 * A publishing task that publishes a given package to a given registry.
 */
@Suppress("LeakingThis")
@UntrackedTask(because = "Must always run")
public abstract class NpmPublishTask : NpmExecTask() {
  @get:Inject
  internal abstract val fs: FileSystemOperations

  /**
   * A registry to publish to
   * @see [NpmRegistry]
   */
  @get:Nested
  public abstract val registry: Property<NpmRegistry>

  /**
   * The directory where the assembled and ready-to-publish package is
   * @see [NpmAssembleTask]
   */
  @get:InputDirectory
  public abstract val packageDir: DirectoryProperty

  /**
   * Controls dry-tun mode for the execution.
   * @see [NpmPublishExtension.dry]
   */
  @get:Input
  @get:Option(option = "dry", description = "Execute in dry-run mode")
  public abstract val dry: Property<Boolean>

  /**
   * Optional tag to label the published package version
   * @see [NpmPublishExtension.dry]
   */
  @get:Input
  @get:Optional
  @get:Option(option = "tag", description = "Optional tag to label the published package version")
  public abstract val tag: Property<String>

  /**
   * Configuration DSL allowing to modify a registry config
   * @param action to apply
   */
  @Suppress("unused")
  public fun registry(action: Action<NpmRegistry>) {
    registry.configure(action)
  }

  init {
    group = PUBLISH_TASK_GROUP
    description = "Publishes NPM package to NPM registry"
    dry.convention(registry.flatMap(NpmRegistry::dry))
    registry.convention(
      project.provider {
        project.objects.newInstance(NpmRegistry::class.java, name)
      }
    )
  }

  @Suppress("unused")
  @TaskAction
  private fun doAction() {
    val reg = registry.get()
    val uri = reg.uri.get()
    val repo = "${uri.authority.trim()}${uri.path.trim()}/"
    val pDir = packageDir.asFile.get()
    val workingDir = project.layout.buildDirectory.dir("registries/${reg.name}/${pDir.name}").get().asFile

    val d = dry.get()
    info {
      "Publishing package at $workingDir to ${reg.name} registry ${if (d) "with" else "without"} --dry-run flag"
    }
    val args: List<String> = buildList {
      add("publish")
      add("$workingDir")
      add("--access=${reg.access.get()}")
      add("--registry=${uri.scheme.trim()}://$repo")
      if (reg.otp.isPresent) add("--otp=${reg.otp.get()}")
      if (reg.authToken.isPresent) add("--//$repo:_authToken=${reg.authToken.get()}")
      if (reg.auth.isPresent) add("--//$repo:_auth=${reg.auth.get()}")
      if (reg.username.isPresent) add("--//$repo:username=${reg.username.get()}")
      if (reg.password.isPresent) {
        val password = reg.password.get()
        val encoded = Base64.getEncoder().encodeToString(password.toByteArray(Charsets.UTF_8))
        add("--//$repo:_password=$encoded")
      }
      if (d) add("--dry-run")
      if (tag.isPresent) add("--tag=${tag.get()}")
    }
    fs.sync {
      it.from(packageDir)
      if (reg.npmrc.isPresent) it.from(reg.npmrc)
      it.into(workingDir)
    }
    npmExec(args) { it.workingDir(workingDir) }.rethrowFailure()
    if (!d) info { "Published package at $workingDir to ${reg.name} registry" }
  }
}
