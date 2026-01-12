/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.util

import org.gradle.api.Named
import org.gradle.api.tasks.Input

/** An override of [Named] interface marking [Named.getName] bean as Gradle's @[Input] */
public interface NamedInput : Named {
  @Input override fun getName(): String
}
