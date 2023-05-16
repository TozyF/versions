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
 * An abstract implementation of [VersionScheme].
 */
public abstract class AbstractVersionScheme<V : AbstractVersion>(override val configuration: SchemeConfiguration) :
    VersionScheme<V> {
    /**
     * Creates a new instance of [V] with the given [major], [minor], [patch] and [qualifier].
     */
    protected abstract fun newVersionInstance(major: Int, minor: Int, patch: Int, qualifier: String): V

    override fun parse(value: String): V {
        if (value.isBlank()) {
            throw VersionParseException("Version cannot be blank")
        }

        val scanner = Scanner(value)
        val major = scanner.nextInt()
        if (!scanner.hasDelimiter(configuration.numericSegmentDelimiter)) {
            if (!scanner.hasDelimiter(configuration.qualifierSegmentDelimiter)) {
                throw VersionParseException("Invalid version: $value")
            }

            scanner.skip()
            return newVersionInstance(major, 0, 0, scanner.remaining())
        }

        scanner.skip()
        val minor = scanner.nextInt()
        if (!scanner.hasDelimiter(configuration.numericSegmentDelimiter)) {
            if (!scanner.hasDelimiter(configuration.qualifierSegmentDelimiter)) {
                throw VersionParseException("Invalid version: $value")
            }

            scanner.skip()
            return newVersionInstance(major, minor, 0, scanner.remaining())
        }

        scanner.skip()
        val patch = scanner.nextInt()
        if (scanner.hasDelimiter(configuration.numericSegmentDelimiter)) {
            throw VersionParseException("Segment fourth must be qualifier")
        }

        if (!scanner.hasDelimiter(configuration.qualifierSegmentDelimiter)) {
            return newVersionInstance(major, minor, patch, "")
        }

        scanner.skip()
        return newVersionInstance(major, minor, patch, scanner.remaining())
    }

    override fun format(version: V): String = buildString {
        append(version.major)
        append(configuration.numericSegmentDelimiter)
        append(version.minor)
        appendPatch(version)
        appendQualifier(version)
    }

    private fun StringBuilder.appendPatch(version: Version) {
        if (configuration.trimZeroPatchSegment && version.patch == 0) {
            return
        }
        append(configuration.numericSegmentDelimiter)
        append(version.patch)
    }

    private fun StringBuilder.appendQualifier(version: Version) {
        if (version.qualifier.isEmpty()) return

        append(configuration.qualifierSegmentDelimiter)
        append(version.qualifier)
    }

    private class Scanner(private val value: String) {
        var pos: Int = 0

        fun hasNext(): Boolean = pos < value.length

        fun hasInt(): Boolean = hasNext() && peek().isDigit()

        fun next(): Char = value[pos++]

        fun peek(): Char = value[pos]

        fun skip() {
            pos++
        }

        fun nextInt(): Int {
            if (hasInt()) {
                throw VersionParseException("Unexpected digit \"${peek()}\" at position $pos")
            }
            var num = next().digitToInt()
            while (hasInt()) {
                num = num * 10 + next().digitToInt()
            }
            return num
        }

        fun hasDelimiter(delimiter: Char): Boolean = hasNext() && peek() == delimiter

        fun remaining(): String = value.substring(pos)
    }
}
