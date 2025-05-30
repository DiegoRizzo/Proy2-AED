/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [https://neo4j.com]
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
package org.neo4j.driver.util;

/**
 * Immutable pair of a key and a value
 *
 * @param <K> the Java type of the key
 * @param <V> the Java type of the contained value
 * @since 1.0
 */
@Immutable
public interface Pair<K, V> {
    /**
     * Returns the property key.
     *
     * @return the property key
     */
    K key();

    /**
     * Returns the property value.
     *
     * @return the property value
     */
    V value();
}
