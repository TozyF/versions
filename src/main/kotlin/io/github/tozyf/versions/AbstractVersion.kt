/*
 * Copyright 2023 TozyF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.tozyf.versions

/**
 * An abstract implementation of [Version].
 */
public abstract class AbstractVersion(
    final override val major: Int,
    final override val minor: Int,
    final override val patch: Int,
    final override val qualifier: String,
    final override val scheme: VersionScheme,
) : Version {
    private val version: Int = versionOf(major, minor, patch)

    /**
     * Converts the version to int for more convenient comparison.
     */
    private fun versionOf(major: Int, minor: Int, patch: Int): Int =
        major.shl(16) + minor.shl(8) + patch

    override fun compareTo(other: Version): Int = when {
        this === other -> 0
        other !is AbstractVersion -> version - versionOf(other.major, other.minor, other.patch)
        version != other.version -> version - other.version
        else -> qualifier.compareTo(other.qualifier)
    }

    override fun toString(): String = scheme.format(this)

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Version -> false
        major != other.major -> false
        minor != other.minor -> false
        patch != other.patch -> false
        else -> qualifier == other.qualifier
    }

    override fun hashCode(): Int = version - qualifier.hashCode()
}
