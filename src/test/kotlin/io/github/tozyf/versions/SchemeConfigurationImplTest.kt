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
import kotlin.test.assertNotSame


class SchemeConfigurationTest {
    @Test
    fun `them same configuration should be equal`() {
        assertEquals(SchemeConfiguration(), SchemeConfiguration())
        assertNotSame(SchemeConfiguration(), SchemeConfiguration())
        assertEquals(SchemeConfiguration().hashCode(), SchemeConfiguration().hashCode())
        assertEquals(SchemeConfiguration(' ', '+', false), SchemeConfiguration(' ', '+', false))
    }

    @Test
    fun `toString should return string of all properties`() {
        assertEquals(
            "SchemeConfigurationImpl(numericSegmentDelimiter=., qualifierSegmentDelimiter=-, trimZeroPatchSegment=true)",
            SchemeConfiguration().toString()
        )
        assertEquals(
            "SchemeConfigurationImpl(numericSegmentDelimiter=+, qualifierSegmentDelimiter= , trimZeroPatchSegment=false)",
            SchemeConfiguration('+', ' ', false).toString()
        )
    }
}
