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
 * The version which is parsed by a [GenericScheme].
 *
 * ### Version syntax
 * Simple version syntax is:
 * ```
 * <major>.<minor>.<patch>-<qualifier>
 * ```
 * The major, minor and patch segments are numeric, and the qualifier segment is a string.
 * The major is required, the minor, patch and qualifier are optional.
 * You can use any character as a delimiter between bump segments and qualifier segment (see [SchemeConfiguration]).
 *
 * There are some examples of valid versions (with default configuration)
 *  - `1.2.3`
 *  - `1.2.3-SNAPSHOT`
 *  - `1.2.3-RC1`
 *  - `1.2.3-R0.1-SNAPSHOT`
 *  - `1.2.3-RC1+build.1`
 *  - `0.0.1`
 *  - `1.0`
 *  - `1.0-SNAPSHOT`
 *
 * ### Version comparison
 * The version comparison is based on the integer version which is an integer number composed of the major, minor and
 * patch segments. The integer version is calculated using the following code: `major.shl(16) + minor.shl(8) + patch`
 *
 * The qualifier segment is only used when the numeric segments are equal.
 * The qualifier segment is compared lexicographically.
 */
@Suppress("UNCHECKED_CAST")
public class GenericVersion internal constructor(
    major: Int,
    minor: Int,
    patch: Int,
    qualifier: String,
    scheme: GenericScheme,
) : AbstractVersion(major, minor, patch, qualifier, scheme as VersionScheme<Version>)

/**
 * Parses the given [value] and returns an instance of [GenericVersion].
 *
 * @see GenericScheme.parse
 */
public fun GenericVersion(value: String): GenericVersion = GenericScheme.parse(value)

/**
 * Creates a new instance of [GenericVersion].
 *
 * The scheme of this version is [GenericScheme.Default].
 */
public fun GenericVersion(major: Int, minor: Int, patch: Int = 0, qualifier: String = ""): GenericVersion =
    GenericVersion(major, minor, patch, qualifier, GenericScheme.Default)
