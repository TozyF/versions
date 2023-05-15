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
 * Represents a scheme for parsing and formatting versions.
 */
public interface VersionScheme {
    /**
     * The configuration of the scheme.
     */
    public val configuration: SchemeConfiguration

    /**
     * Parses the given [value] and returns an instance of [Version].
     */
    public fun parse(value: String): Version

    /**
     * Formats the given [version] and returns its string representation.
     */
    public fun format(version: Version): String
}
