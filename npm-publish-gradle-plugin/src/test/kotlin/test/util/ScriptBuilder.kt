/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.test.util

open class ScriptBuilder(val baseIndent: Int = 0) {
  private val indentChunk = List(baseIndent) { " " }.joinToString("")
  private val builder = StringBuilder()

  /**
   * Appends a lambda block
   *
   * ```kotlin
   * [name] {
   *   <content>
   * }
   * ```
   */
  operator fun String.invoke(indent: Int = 2, block: ScriptBuilder.() -> Unit) {
    val sb = ScriptBuilder(baseIndent + indent).apply(block)
    +"$this {"
    builder.append("$sb")
    +"}"
  }

  operator fun String.unaryPlus() {
    split("\n").forEach { builder.appendLine("$indentChunk$it") }
  }

  override fun toString(): String {
    return builder.toString()
  }
}

operator fun <T : ScriptBuilder> T.invoke(block: T.() -> Unit) = apply(block)
