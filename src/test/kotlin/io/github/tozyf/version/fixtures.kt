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

@Suppress("ObjectPropertyName")
object Versions {
    val `0_0_0` = Version("0.0.0")
    val `1_0_0` = Version("1.0.0")

    val `1_0_0-snapshot` = Version("1.0.0-snapshot")
    val `1_0_0-SNAPSHOT` = Version("1.0.0-SNAPSHOT")

    val unspecified = Version("unspecified")
    val UNSPECIFIED = Version("UNSPECIFIED")

    val abc = Version("abc")
    val bce = Version("bce")
    val def = Version("def")
}
