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
 * Represents a parsed version. The implementation of this interface must be immutable.
 */
public interface Version : Comparable<Version> {
    /**
     * The first number in the version, known as the [major] version number.
     */
    public val major: Int

    /**
     * The second number in the version, known as the [minor] version number.
     */
    public val minor: Int

    /**
     * The third number in the version, known as the [patch] version number.
     * Sometimes referred to as the `micro` segment.
     */
    public val patch: Int

    /**
     * The remaining part of the version, known as the [qualifier]. It is optional and can be empty.
     */
    public val qualifier: String

    /**
     * The scheme used to parse and format this version.
     */
    public val scheme: VersionScheme<Version>

    /**
     * Returns the formatted string representation of this version.
     *
     * @see VersionScheme.format
     */
    public override fun toString(): String
}

/**
 * Parses the given [value] and returns an instance of [Version] with the [GenericScheme].
 *
 * @see GenericVersion
 * @see GenericScheme.parse
 */
public fun Version(value: String): Version = GenericVersion(value)
