/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.util

@Suppress("UNCHECKED_CAST", "ComplexMethod")
internal fun MutableMap<String, Any>.overrideFrom(
  other: Map<String, Any>
): MutableMap<String, Any> = apply {
  other.entries.forEach { (key, new) ->
    val old = this[key]
    this[key] =
      when {
        old == null -> new
        old is Map<*, *> && new is Map<*, *> ->
          (old as Map<String, Any>).toMutableMap().overrideFrom(new as Map<String, Any>)

        old is Array<*> && new is Array<*> -> old.toList() + new.toList()
        old is Array<*> && new is Collection<*> -> old.toList() + new
        (old is Collection<*> && new is Array<*>) ||
          (old is Collection<*> && new is Collection<*>) -> old + new

        else -> new
      }
  }
}
