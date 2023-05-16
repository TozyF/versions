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
 * Represents the configuration of a [VersionScheme].
 */
public interface SchemeConfiguration {
    /**
     * The delimiter used to separate numeric segments in the version string.
     */
    public val numericSegmentDelimiter: Char

    /**
     * The delimiter used to separate the qualifier segment in the version string.
     */
    public val qualifierSegmentDelimiter: Char

    /**
     * Whether the [Version.patch] segment is trimmed if it is zero.
     */
    public val trimZeroPatchSegment: Boolean
}

/**
 * Creates an instance of [SchemeConfiguration].
 *
 * @param numericSegmentDelimiter The delimiter used to separate numeric segments in the version string.
 * Default is '`.`'.
 * @param qualifierSegmentDelimiter The delimiter used to separate the qualifier segment in the version string.
 * Default is '`-`'.
 * @param trimZeroPatchSegment Whether the [Version.patch] segment is trimmed if it is zero. Default is `true`.
 */
public fun SchemeConfiguration(
    numericSegmentDelimiter: Char = '.',
    qualifierSegmentDelimiter: Char = '-',
    trimZeroPatchSegment: Boolean = true,
): SchemeConfiguration = SchemeConfigurationImpl(
    numericSegmentDelimiter,
    qualifierSegmentDelimiter,
    trimZeroPatchSegment,
)

private class SchemeConfigurationImpl(
    override val numericSegmentDelimiter: Char,
    override val qualifierSegmentDelimiter: Char,
    override val trimZeroPatchSegment: Boolean,
) : SchemeConfiguration {
    override fun toString(): String = "SchemeConfigurationImpl(numericSegmentDelimiter=$numericSegmentDelimiter, " +
            "qualifierSegmentDelimiter=$qualifierSegmentDelimiter, trimZeroPatchSegment=$trimZeroPatchSegment)"

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is SchemeConfigurationImpl -> false
        numericSegmentDelimiter != other.numericSegmentDelimiter -> false
        qualifierSegmentDelimiter != other.qualifierSegmentDelimiter -> false
        else -> trimZeroPatchSegment == other.trimZeroPatchSegment
    }

    override fun hashCode(): Int {
        var result = numericSegmentDelimiter.hashCode()
        result = 31 * result + qualifierSegmentDelimiter.hashCode()
        result = 31 * result + trimZeroPatchSegment.hashCode()
        return result
    }
}
