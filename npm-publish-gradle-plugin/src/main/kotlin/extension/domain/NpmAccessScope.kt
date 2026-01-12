/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.extension.domain

/** Provides implicit accessors to [NpmAccess] values */
@Suppress("PropertyName", "unused", "VariableNaming")
public interface NpmAccessScope {
  /** @see [NpmAccess.PUBLIC] */
  public val PUBLIC: NpmAccess
    get() = NpmAccess.PUBLIC

  /** @see [NpmAccess.RESTRICTED] */
  public val RESTRICTED: NpmAccess
    get() = NpmAccess.RESTRICTED
}
