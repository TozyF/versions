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

package io.github.tozyf.version

import kotlin.test.*

class VersionTest {
    @Test
    fun `the same string representation should be equal`() {
        assertEquals(Version("0.0.0"), Versions.`0_0_0`)
        assertEquals(Version("1.0.0"), Versions.`1_0_0`)

        assertEquals("1.0.0-snapshot", Versions.`1_0_0-snapshot`.toString())
        assertEquals("unspecified", Versions.unspecified.toString())

        assertNotEquals("1. 0.0", Versions.`1_0_0`.toString())
        assertNotEquals(" abc", Versions.abc.toString())
    }

    @Test
    fun `difference string representation case should not equal`() {
        assertNotEquals(Version("1.0.0-SNAPSHOT"), Versions.`1_0_0-snapshot`)
        assertNotEquals(Version("unspecified"), Versions.UNSPECIFIED)

        assertEquals(Version("1.0.0-SNAPSHOT"), Versions.`1_0_0-SNAPSHOT`)
        assertEquals(Version("bce"), Versions.bce)
    }

    @Test
    fun `simple comparison should be correct`() {
        assertEquals(0, Versions.`0_0_0`.compareTo(Versions.`0_0_0`))
        assertEquals(1, Versions.`1_0_0`.compareTo(Versions.`0_0_0`))
        assertEquals(-1, Versions.`0_0_0`.compareTo(Versions.`1_0_0`))

        assertTrue(Versions.`0_0_0` < Versions.`1_0_0`)

        assertEquals(0, Versions.abc.compareTo(Versions.abc))
        assertEquals(1, Versions.bce.compareTo(Versions.abc))
        assertEquals(-1, Versions.abc.compareTo(Versions.bce))
        assertEquals(3, Versions.def.compareTo(Versions.abc))
        assertEquals(-3, Versions.abc.compareTo(Versions.def))

        assertTrue(Versions.abc < Versions.bce)
        assertTrue(Versions.bce < Versions.def)
        assertTrue(Versions.abc < Versions.def)

        assertFalse(Versions.`1_0_0` < Versions.`0_0_0`)

        // Complex case must use a version scheme to compare
        assertFalse(Versions.`1_0_0-SNAPSHOT` < Versions.`1_0_0`)
    }

    @Test
    fun `hashCode should same hash code if they have the same string representation`() {
        assertEquals(Version("0.0.0").hashCode(), Versions.`0_0_0`.hashCode())
        assertEquals(Version("1.0.0").hashCode(), Versions.`1_0_0`.hashCode())
        assertEquals(Version("1.0.0-snapshot").hashCode(), Versions.`1_0_0-snapshot`.hashCode())
        assertEquals(Version("unspecified").hashCode(), Versions.unspecified.hashCode())

        assertEquals("0.0.0".hashCode(), Versions.`0_0_0`.hashCode())
        assertEquals("abc".hashCode(), Versions.abc.hashCode())
        assertEquals("bce".hashCode(), Versions.bce.hashCode())
    }
}
