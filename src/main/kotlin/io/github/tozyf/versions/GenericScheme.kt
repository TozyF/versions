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
 * The generic version scheme for parsing and formatting [GenericVersion]s.
 *
 * Details about the version scheme can be found in the [GenericVersion] docs.
 */
public sealed class GenericScheme(configuration: SchemeConfiguration) :
    AbstractVersionScheme<GenericVersion>(configuration) {
    override fun newVersionInstance(major: Int, minor: Int, patch: Int, qualifier: String): GenericVersion =
        GenericVersion(major, minor, patch, qualifier, this)

    override fun toString(): String = "GenericScheme(configuration=$configuration)"

    /**
     * The default instance of [GenericScheme] with default configuration.
     */
    public companion object Default : GenericScheme(SchemeConfiguration())
}

/**
 * Creates an instance of [GenericScheme] configured from the optionally given [VersionScheme instance][from] and
 * adjust with [builderAction].
 */
public fun GenericScheme(
    from: GenericScheme = GenericScheme.Default,
    builderAction: GenericSchemeBuilder.() -> Unit,
): GenericScheme = GenericSchemeImpl(GenericSchemeBuilder(from).apply(builderAction).build())

/**
 * Builder of the [GenericScheme] instance provided by `GenericScheme { ... }` factory function.
 */
public class GenericSchemeBuilder internal constructor(scheme: GenericScheme) {
    /**
     * The delimiter used to separate numeric segments in the version string.
     *
     * '.' by default.
     */
    public var numericSegmentDelimiter: Char = scheme.configuration.numericSegmentDelimiter

    /**
     * The delimiter used to separate the qualifier segment in the version string.
     *
     * '-' by default.
     */
    public var qualifierSegmentDelimiter: Char = scheme.configuration.qualifierSegmentDelimiter

    /**
     * Whether the [Version.patch] segment is trimmed if it is zero.
     *
     * `true` by default.
     */
    public var zeroPatchSegmentTrimmed: Boolean = scheme.configuration.trimZeroPatchSegment

    internal fun build(): SchemeConfiguration =
        SchemeConfiguration(numericSegmentDelimiter, qualifierSegmentDelimiter, zeroPatchSegmentTrimmed)
}

private class GenericSchemeImpl(configuration: SchemeConfiguration) : GenericScheme(configuration)
