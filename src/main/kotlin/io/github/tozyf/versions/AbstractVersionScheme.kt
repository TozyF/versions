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

        val scanner = Scanner(value, configuration)
        var major = 0
        var minor = 0
        var patch = 0
        var qualifier = ""
        var numericSegmentIndex = 1
        try {
            while (scanner.hasNext()) {
                when (scanner.nextToken()) {
                    Token.NUMERIC_SEGMENT -> {
                        when (numericSegmentIndex++) {
                            1 -> major = scanner.readInt()
                            2 -> minor = scanner.readInt()
                            3 -> patch = scanner.readInt()
                            else -> throw VersionParseException("Must not have more than 3 numeric segments")
                        }
                    }

                    Token.QUALIFIER_SEGMENT -> qualifier = scanner.readString()
                    else -> {}
                }
            }
        } catch (throwable: Throwable) {
            throw VersionParseException("Invalid version: $value", throwable)
        }

        return newVersionInstance(major, minor, patch, qualifier)
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

    private enum class Token { NUMERIC_SEGMENT, QUALIFIER_SEGMENT, NUMERIC_DELIMITER, QUALIFIER_DELIMITER }

    private class Scanner(private val value: String, private val configuration: SchemeConfiguration) {
        var startReadPos: Int = -1
        private var currentPos: Int = 0
        private var previousToken: Token? = null

        private fun hasInt(): Boolean = hasNext() && peek() in DIGIT_CHAR_RANGES

        private fun peek(): Char = value[currentPos]

        private fun next() = ++currentPos

        private fun end() {
            currentPos = value.length
        }

        fun hasNext(): Boolean = currentPos < value.length

        fun readString(): String {
            check(startReadPos != -1) { "No string to read" }
            return value.substring(startReadPos, currentPos)
        }

        fun readInt(): Int {
            check(startReadPos != -1) { "No int to read" }
            var num = 0
            for (i in startReadPos until currentPos) {
                num = num * 10 + value[i].digitToInt()
            }
            return num
        }

        private fun expectedPreviousToken(token: Token) =
            check(previousToken == token) { "Expected $token, but got $previousToken" }

        fun nextToken(): Token {
            check(hasNext()) { "No more tokens" }
            startReadPos = currentPos
            return when (peek()) {
                configuration.numericSegmentDelimiter -> {
                    expectedPreviousToken(Token.NUMERIC_SEGMENT)
                    next()
                    Token.NUMERIC_DELIMITER
                }

                configuration.qualifierSegmentDelimiter -> {
                    expectedPreviousToken(Token.NUMERIC_SEGMENT)
                    next()
                    Token.QUALIFIER_DELIMITER
                }

                in DIGIT_CHAR_RANGES -> {
                    while (hasInt()) {
                        next()
                    }
                    Token.NUMERIC_SEGMENT
                }

                else -> {
                    if (previousToken == Token.NUMERIC_SEGMENT) {
                        end()
                        return Token.QUALIFIER_SEGMENT
                    }

                    expectedPreviousToken(Token.QUALIFIER_DELIMITER)
                    end()
                    Token.QUALIFIER_SEGMENT
                }
            }.also { previousToken = it }
        }

        companion object {
            val DIGIT_CHAR_RANGES = '0'..'9'
        }
    }
}
