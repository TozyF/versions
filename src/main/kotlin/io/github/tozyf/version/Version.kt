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

package io.github.tozyf.version

/**
 * Represents a parsed version.
 *
 * The implementation of this interface must be immutable.
 */
public interface Version : Comparable<Version> {
    /**
     * Returns the string representation of this version.
     */
    public override fun toString(): String
}

/**
 * Creates an instance of [Version] from the given [versionString]. The created instance is immutable and has no
 * special meaning (has no special format).
 *
 * The comparison of two versions is based on the lexicographic order of their string representations.
 */
public fun Version(versionString: String): Version = ImmutableVersion(versionString)

private class ImmutableVersion(private val versionString: String) : Version {
    override fun toString(): String = versionString

    override fun compareTo(other: Version): Int = versionString.compareTo(other.toString())

    override fun equals(other: Any?): Boolean = other is Version && versionString == other.toString()

    override fun hashCode(): Int = versionString.hashCode()
}
