/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.test.util

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags

@Tags(Tag("unit"))
@Suppress("UnnecessaryAbstractClass")
abstract class UTest {
  protected val packageNames =
    listOf(
      "testPackage",
      "TestPackage",
      "test package",
      "test Package",
      "Test Package",
      "test-package",
    )
  protected val registryNames =
    listOf(
      "testRegistry",
      "TestRegistry",
      "test registry",
      "test Registry",
      "Test Registry",
      "test-registry",
    )
}
