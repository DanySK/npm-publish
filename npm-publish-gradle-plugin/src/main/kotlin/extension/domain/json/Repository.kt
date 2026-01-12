/*
 * Copyright 2025-2026 JetBrains s.r.o. and respective authors and developers.
 * Based on original work by Martynas Petuska and contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package dev.petuska.npm.publish.extension.domain.json

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

/** [repository](https://docs.npmjs.com/files/package.json#repository) */
public abstract class Repository : GenericJsonObject() {
  /** [repository](https://docs.npmjs.com/files/package.json#repository) */
  @get:Input @get:Optional public abstract val type: Property<String>

  /** [repository](https://docs.npmjs.com/files/package.json#repository) */
  @get:Input @get:Optional public abstract val url: Property<String>

  /** [repository](https://docs.npmjs.com/files/package.json#repository) */
  @get:Input @get:Optional public abstract val directory: Property<String>

  override fun finalise(): MutableMap<String, Any> =
    super.finalise().apply {
      type.finalOrNull?.let { put("type", it) }
      url.finalOrNull?.let { put("url", it) }
      directory.finalOrNull?.let { put("directory", it) }
    }
}
