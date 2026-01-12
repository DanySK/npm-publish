/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.util

import javax.inject.Inject
import kotlin.reflect.KClass
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory

/** Utility abstract class to provide injected gradle object factories */
public abstract class WithGradleFactories {
  @get:Inject protected abstract val objects: ObjectFactory

  @get:Inject protected abstract val providers: ProviderFactory

  protected fun <T : Any> instance(klass: KClass<T>, vararg args: Any?): T =
    objects.newInstance(klass.java, *args.filterNotNull().toTypedArray())

  protected fun <T : Any> instanceProvider(klass: KClass<T>, vararg args: Any?): Provider<T> =
    providers.provider { instance(klass, *args) }

  protected fun <T : Any> Property<T>.configure(
    klass: KClass<T>,
    config: Action<T>,
    vararg args: Any?,
  ) {
    set(instanceProvider(klass, *args).map { it.also(config::execute) })
  }

  protected inline fun <reified T : Any> Property<T>.configure(
    config: Action<T>,
    vararg args: Any?,
  ) {
    configure(T::class, config, *args)
  }
}
