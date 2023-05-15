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
