/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.extension.domain

import dev.petuska.npm.publish.config.assembleTaskName
import dev.petuska.npm.publish.config.packTaskName
import dev.petuska.npm.publish.test.util.UTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class NpmPackageUTest : UTest() {
  @TestFactory
  fun assembleTaskNameTests(): Collection<DynamicTest> {
    val expected = "assembleTestPackagePackage"
    return packageNames.map {
      DynamicTest.dynamicTest("::assembleTaskName can handle '$it' package name") {
        assembleTaskName(it) shouldBe expected
      }
    }
  }

  @TestFactory
  fun packTaskNameTests(): Collection<DynamicTest> {
    val expected = "packTestPackagePackage"
    return packageNames.map {
      DynamicTest.dynamicTest("::packTaskName can handle '$it' package name") {
        packTaskName(it) shouldBe expected
      }
    }
  }
}
