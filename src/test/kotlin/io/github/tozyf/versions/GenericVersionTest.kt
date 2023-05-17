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
import kotlin.test.assertIs
import kotlin.test.assertTrue

@Suppress("PrivatePropertyName")
class GenericVersionTest {
    private val v0 by lazy { GenericVersion("0.0.0") }
    private val v0_0_1 by lazy { GenericVersion("0.0.1") }
    private val v0_0_2 by lazy { GenericVersion("0.0.2") }
    private val v0_1_0 by lazy { GenericVersion("0.1.0") }
    private val v0_1_1 by lazy { GenericVersion("0.1.1") }

    private val v1 by lazy { GenericVersion("1.0.0") }
    private val v1_SNAPSHOT by lazy { GenericVersion("1.0.0-SNAPSHOT") }
    private val v1_ALPHA by lazy { GenericVersion("1.0.0-ALPHA") }
    private val v1_BETA by lazy { GenericVersion("1.0.0-BETA") }

    @Test
    fun `scheme should be GenericScheme`() {
        assertIs<GenericScheme>(GenericVersion(1, 0).scheme)
    }

    @Test
    fun `GenericVersion(String) factory fun should return a parsed version`() {
        assertEquals(GenericVersion(1, 0, 0), v1)
        assertEquals(GenericVersion(1, 0), GenericVersion("1.0"))
        assertEquals(GenericVersion(1, 0, qualifier = "M1"), GenericVersion("1.0-M1"))
    }

    @Test
    fun `toString should be a formatted string version`() {
        assertEquals("1.2.3-SNAPSHOT", GenericVersion(1, 2, 3, "SNAPSHOT").toString())
        assertEquals("1.2-SNAPSHOT", GenericVersion(1, 2, qualifier = "SNAPSHOT").toString())
    }

    @Test
    fun `compareTo should return the int version diff`() {
        assertEquals(1, v0_0_1.compareTo(v0))
        assertEquals(-1, v0.compareTo(v0_0_1))
        assertEquals(-2, v0.compareTo(v0_0_2))
        assertEquals(-1 shl 8 /* = -256 */, v0.compareTo(v0_1_0))
        assertEquals(-(1.shl(8) + 1) /* = -257 */, v0.compareTo(v0_1_1))
        assertEquals(-1 shl 16 /* = -65536 */, v0.compareTo(v1))

        assertEquals(1, v1.compareTo(v1_SNAPSHOT))
        assertEquals(-1, v1_ALPHA.compareTo(v1))
        assertEquals(-1, v1_ALPHA.compareTo(v1_BETA))
        assertEquals(17, v1_SNAPSHOT.compareTo(v1_BETA))
    }

    @Test
    fun `version comparison should be correct order`() {
        assertTrue(v0 < v1)
        assertTrue(v0 < v0_0_1)
        assertTrue(v0_0_1 < v0_1_0)
        assertTrue(v0_1_0 < v0_1_1)
        assertTrue(v1_SNAPSHOT < v1)
        assertTrue(v1_ALPHA < v1_BETA)
    }

    @Test
    fun `the same version should be the same hashCode`() {
        assertEquals(GenericVersion(0, 0, 0).hashCode(), v0.hashCode())
        assertEquals(GenericVersion(0, 1, 0).hashCode(), v0_1_0.hashCode())
        assertEquals(GenericVersion(1, 0, qualifier = "ALPHA").hashCode(), v1_ALPHA.hashCode())
    }
}
