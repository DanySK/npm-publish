/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.extension.domain

import java.util.*

/** Enum representation of NPM repository access */
public enum class NpmAccess {
  PUBLIC,
  RESTRICTED;

  override fun toString(): String {
    return name.lowercase(Locale.getDefault())
  }

  public companion object {
    /**
     * Converts a given string to an instance of [NpmAccess] by name ignoring case
     *
     * @param name of the [NpmAccess] instance
     * @return an instance of [NpmAccess]
     */
    public fun fromString(name: String): NpmAccess = values().first { it.name.equals(name, true) }
  }
}
