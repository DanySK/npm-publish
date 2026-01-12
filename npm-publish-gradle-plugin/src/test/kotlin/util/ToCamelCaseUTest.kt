/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package util

import dev.petuska.npm.publish.test.util.UTest
import dev.petuska.npm.publish.util.toCamelCase
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class ToCamelCaseUTest : UTest() {
  @TestFactory
  fun tests(): Collection<DynamicTest> {
    val expectedLower = "publishJsPackageToGitHubRegistry"
    val expectedUpper = "PublishJsPackageToGitHubRegistry"
    return listOf(
        "PublishJsPackageToGitHubRegistry",
        "publishJsPackageToGitHubRegistry",
        "PublishJsPackage-ToGitHub Registry",
        "publishJsPackage-ToGitHub Registry",
      )
      .flatMap {
        listOf(
          DynamicTest.dynamicTest(
            "String::toCamelCase can convert '$it' to '$expectedLower' lower camel case"
          ) {
            it.toCamelCase(lower = true) shouldBe expectedLower
          },
          DynamicTest.dynamicTest(
            "String::toCamelCase can convert '$it' to '$expectedUpper' upper camel case"
          ) {
            it.toCamelCase(lower = false) shouldBe expectedUpper
          },
        )
      }
  }
}
