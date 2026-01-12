/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.extension.domain.json

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

/** [bugs](https://docs.npmjs.com/files/package.json#bugs) */
public abstract class Bugs : GenericJsonObject() {
  /** [bugs](https://docs.npmjs.com/files/package.json#bugs) */
  @get:Input @get:Optional public abstract val url: Property<String>

  /** [bugs](https://docs.npmjs.com/files/package.json#bugs) */
  @get:Input @get:Optional public abstract val email: Property<String>

  override fun finalise(): MutableMap<String, Any> =
    super.finalise().apply {
      url.finalOrNull?.let { put("url", it) }
      email.finalOrNull?.let { put("email", it) }
    }
}
