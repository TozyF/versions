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

class CustomGenericSchemeTest {
    @Test
    fun `custom delimiter version should be parsed`() {
        val scheme = GenericScheme {
            numericSegmentDelimiter = '+'
            qualifierSegmentDelimiter = ' '
        }
        with(scheme) {
            parse("1+2 SNAPSHOT").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(0, patch)
                assertEquals("SNAPSHOT", qualifier)
            }
            parse("1+2+3 SNAPSHOT").run {
                assertEquals(1, major)
                assertEquals(2, minor)
                assertEquals(3, patch)
                assertEquals("SNAPSHOT", qualifier)
            }
        }
    }

    @Test
    fun `zero patch should not be trimmed if configuration set to false`() {
        val scheme = GenericScheme { trimZeroPatchSegment = false }
        with(scheme) {
            assertEquals("1.0.0", format(GenericVersion(1, 0)))
            assertEquals("1.0.0-SNAPSHOT", format(GenericVersion(1, 0, qualifier = "SNAPSHOT")))
            assertEquals("1.2.0", format(GenericVersion(1, 2)))
        }
    }

    @Test
    fun `version object should be formatted with custom delimiter`() {
        val scheme = GenericScheme {
            numericSegmentDelimiter = '_'
            qualifierSegmentDelimiter = '+'
        }
        with(scheme) {
            assertEquals("1_2_3+SNAPSHOT", format(GenericVersion(1, 2, 3, "SNAPSHOT")))
            assertEquals("1_2+SNAPSHOT", format(GenericVersion(1, 2, qualifier = "SNAPSHOT")))
        }
    }
}
