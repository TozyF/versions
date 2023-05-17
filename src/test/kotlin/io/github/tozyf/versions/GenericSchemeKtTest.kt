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
import kotlin.test.assertFalse
import kotlin.test.assertNotSame

class GenericSchemeKtTest {
    @Test
    fun `GenericScheme factory fun should create new instance of GenericScheme`() {
        val scheme = GenericScheme {
            numericSegmentDelimiter = '_'
            qualifierSegmentDelimiter = ' '
            trimZeroPatchSegment = false
        }
        assertNotSame(GenericScheme.Default, scheme)
        with(scheme.configuration) {
            assertEquals('_', numericSegmentDelimiter)
            assertEquals(' ', qualifierSegmentDelimiter)
            assertFalse(trimZeroPatchSegment)
        }
    }

    @Test
    fun `GenericScheme creation without configuration should have default GenericScheme configuration`() {
        val scheme = GenericScheme {}
        with(GenericScheme.configuration) {
            assertEquals(numericSegmentDelimiter, scheme.configuration.numericSegmentDelimiter)
            assertEquals(qualifierSegmentDelimiter, scheme.configuration.qualifierSegmentDelimiter)
            assertEquals(trimZeroPatchSegment, scheme.configuration.trimZeroPatchSegment)
        }
    }

    @Test
    fun `GenericScheme creation with from param should have inherited configuration if not set`() {
        val from = GenericScheme {
            numericSegmentDelimiter = ' '
            qualifierSegmentDelimiter = ' '
            trimZeroPatchSegment = true
        }
        val scheme = GenericScheme(from) {
            qualifierSegmentDelimiter = '-'
        }
        with(from.configuration) {
            assertEquals(numericSegmentDelimiter, scheme.configuration.numericSegmentDelimiter)
            assertEquals('-', scheme.configuration.qualifierSegmentDelimiter)
            assertEquals(trimZeroPatchSegment, scheme.configuration.trimZeroPatchSegment)
        }
    }
}
