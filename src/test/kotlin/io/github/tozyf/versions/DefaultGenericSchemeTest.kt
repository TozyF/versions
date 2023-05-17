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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DefaultGenericSchemeTest {
    @Test
    fun `version string should be parsed`() {
        with(GenericScheme) {
            parse("1.0.0-SNAPSHOT").run {
                assertEquals(1, major)
                assertEquals(0, minor)
                assertEquals(0, patch)
                assertEquals("SNAPSHOT", qualifier)
            }
            parse("1.2.3-SNAPSHOT").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(3, patch)
                assertEquals("SNAPSHOT", qualifier)
            }
            parse("0.1.0-SNAPSHOT").run {
                assertEquals(0, major)
                assertEquals(1, minor)
                assertEquals(0, patch)
                assertEquals("SNAPSHOT", qualifier)
            }
            parse("1.0.0-R0.1-SNAPSHOT").run {
                assertEquals(1, major)
                assertEquals(0, minor)
                assertEquals(0, patch)
                assertEquals("R0.1-SNAPSHOT", qualifier)
            }
            parse("1.2.110").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(110, patch)
                assertEquals("", qualifier)
            }
        }
    }

    @Test
    fun `trimmed version string should be parsed`() {
        with(GenericScheme) {
            parse("1-snapshot").run {
                assertEquals(1, major)
                assertEquals(0, minor)
                assertEquals(0, patch)
                assertEquals("snapshot", qualifier)
            }
            parse("1snapshot").run {
                assertEquals(1, major)
                assertEquals(0, minor)
                assertEquals(0, patch)
                assertEquals("snapshot", qualifier)
            }
            parse("1.2-snapshot").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(0, patch)
                assertEquals("snapshot", qualifier)
            }
            parse("1.2snapshot").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(0, patch)
                assertEquals("snapshot", qualifier)
            }
            parse("1.2.3snapshot").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(3, patch)
                assertEquals("snapshot", qualifier)
            }
            parse("1000").run {
                assertEquals(1000, major)
                assertEquals(0, minor)
                assertEquals(0, patch)
                assertEquals("", qualifier)
            }
        }
    }

    @Test
    fun `invalid version should not be parsed`() {
        with(GenericScheme) {
            assertFailsWith<VersionParseException>("Version cannot be blank") {
                parse("")
            }
            assertFailsWith<VersionParseException>("Version cannot be blank") {
                parse("   ")
            }
            assertFailsWith<VersionParseException>("Must not have more than 3 numeric segments") {
                parse("1.2.3.4")
            }
            assertParseInvalidVersion("-SNAPSHOT", "Expected NUMERIC_SEGMENT, but got null")
            assertParseInvalidVersion("SNAPSHOT", "Expected QUALIFIER_DELIMITER, but got null")
            assertParseInvalidVersion("1.snapshot", "Expected QUALIFIER_DELIMITER, but got NUMERIC_DELIMITER")
            assertParseInvalidVersion("1.snapshot", "Expected QUALIFIER_DELIMITER, but got NUMERIC_DELIMITER")
            assertParseInvalidVersion("1.2..", "Expected NUMERIC_SEGMENT, but got NUMERIC_DELIMITER")
            assertParseInvalidVersion("1.-..", "Expected NUMERIC_SEGMENT, but got NUMERIC_DELIMITER")
        }
    }

    private fun GenericScheme.assertParseInvalidVersion(version: String, causeMessage: String? = null) {
        val throwable = assertFailsWith<VersionParseException>("Invalid version: $version") {
            parse(version)
        }
        assertEquals(causeMessage, throwable.cause?.message)
    }

    @Test
    fun `version object should be formatted`() {
        with(GenericScheme) {
            assertEquals("1.2.3", format(GenericVersion(1, 2, 3)))
            assertEquals("1.0-SNAPSHOT", format(GenericVersion(1, 0, qualifier = "SNAPSHOT")))
            assertEquals("1.2", format(GenericVersion(1, 2)))
            assertEquals("1.2.3-SNAPSHOT", format(GenericVersion(1, 2, 3, "SNAPSHOT")))
        }
    }

    @Test
    fun `toString should return class name and it configuration`() {
        with(GenericScheme) {
            assertEquals("GenericScheme(configuration=$configuration)", toString())
        }
    }
}
